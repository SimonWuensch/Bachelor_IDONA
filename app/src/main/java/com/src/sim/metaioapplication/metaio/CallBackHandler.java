package com.src.sim.metaioapplication.metaio;


import android.util.Log;

import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.src.sim.metaioapplication.asynctask.WayAlgorithm;
import com.src.sim.metaioapplication.logic.TrackerAlgo;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.Location;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallBackHandler extends IMetaioSDKCallback {
    private List<Integer> idList;
    private Map<Integer, List<IGeometry>> geometryMap;
    private Map<Integer, Tracker> trackerMap;

    private int currentTrackerID;
    private LocationObject locationObject;
    private TrackerAlgo trackerAlgo;
    private boolean isFirstStart = true;

    public CallBackHandler(Map<Integer, Tracker> trackerMap, Map<Integer, List<IGeometry>> geometryMap){
        this.geometryMap = geometryMap;
        this.trackerMap = trackerMap;
        this.trackerAlgo = new TrackerAlgo(trackerMap);
        this.idList = new ArrayList<Integer>();
    }

    public void updateLocationObject(LocationObject locationObject){
        Log.d(CallBackHandler.class.getSimpleName(), "LocationObject changened to " + locationObject.toString());
        this.locationObject = locationObject;
        resetIDList();
    }

    @Override
    public void onTrackingEvent(TrackingValuesVector trackingValues) {
         if (trackingValues.size() >= 1 && !isFirstStart && locationObject != null) {
             TrackingValues value = trackingValues.get(0);
             int systemId = value.getCoordinateSystemID();
             Log.d(CallBackHandler.class.getSimpleName(), "Got Tracker with id " + systemId);

             currentTrackerID = systemId;
             Tracker startTracker = trackerMap.get(systemId);
             if(startTracker != null && !idList.contains(systemId)) {
                 Log.d(CallBackHandler.class.getSimpleName(), "ID is not in IDList " + systemId);
                 WayAlgorithm.calculateWay(this, trackerAlgo, startTracker, locationObject);
             }
         }
         isFirstStart = false;
    }

    public void updateGeometryRotation(String arrow, int systemId, Direction direction){
        IGeometry geometry = null;
        if(arrow.equals(Direction.ARROWNORMAL)){
            geometry = geometryMap.get(systemId).get(0);
        }else if(arrow.equals(Direction.ARROWCURVE)){
            geometry = geometryMap.get(systemId).get(1);
        }else{
            throw new NullPointerException("Arrow does not exist! - " + arrow + ".");
        }

        geometry.setVisible(true);
        geometry.setRotation(direction.getRotation().getGeometryRotation());
        Log.d(ARConfiguration.class.getSimpleName(), "Geometry [" + systemId + "] set to Rotation " + direction.getRotation().name());
    }

    public IGeometry getCurrentIGeometry(){
        List<IGeometry> geometries =  geometryMap.get(currentTrackerID);
        if(geometries != null)
            for(IGeometry geometry : geometries){
                if(geometry.isVisible()) {
                    return geometry;
                }
            }
        throw new NullPointerException("No visible IGeometry found.");
    }

    public void resetIDList(){
        idList = new ArrayList<Integer>();
    }

    public void addIDToIDList(int id){
        idList.add(id);
    }
}