package com.src.sim.metaioapplication.metaio;


import android.util.Log;

import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.src.sim.metaioapplication.logic.TrackerAlgo;
import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;
import com.src.sim.metaioapplication.metaio.ARConfiguration.GeometryRotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetaioSDKCallbackHandler extends IMetaioSDKCallback {

    private List<Integer> idList;

    private ARConfiguration arConfiguration;
    private Map<Integer, Tracker> trackerMap;
    private LocationObject locationObject;

    public MetaioSDKCallbackHandler(ARConfiguration arConfiguration, Map<Integer, Tracker> trackerMap, LocationObject locationObject){
        this.arConfiguration = arConfiguration;
        this.trackerMap = trackerMap;
        this.locationObject = locationObject;
        this.idList = new ArrayList<>();
    }

    @Override
    public void onTrackingEvent(TrackingValuesVector trackingValues) {
         if (trackingValues.size() >= 1) {
             TrackerAlgo trackerAlgo = new TrackerAlgo(trackerMap);
             TrackingValues value = trackingValues.get(0);

             int systemId = value.getCoordinateSystemID();
             Tracker startTracker = trackerMap.get(systemId);
             Log.d(MetaioSDKCallbackHandler.class.getSimpleName(), "Tracker with id " + systemId + " detected.");

             if (startTracker != null && !idList.contains(systemId)) {
                 idList = new ArrayList<>();
                 Map<Aim, List<List<Tracker>>> ways = trackerAlgo.findWays(startTracker, locationObject);
                 initializeTrackers(ways);
             }
         }
    }

    private void initializeTrackers(Map<Aim, List<List<Tracker>>> ways){
        Aim aim = (Aim)ways.keySet().toArray()[0];
        List<Tracker> trackers = ways.get(aim).get(0);
        TrackerAlgo.printWay(trackers, aim);

        idList.add(trackers.get(0).getId());
        for(int i = 1; i < trackers.size(); i++){
            idList.add(trackers.get(i).getId());
            Tracker oldTracker = trackers.get(i-1);
            Tracker newTracker = trackers.get(i);

            String directionIcon = oldTracker.getDirectionIconToTracker(newTracker);
            arConfiguration.updateGeometryRotation(oldTracker.getId(), getGeometryRotation(directionIcon));

            if(i == trackers.size() - 1){
                GeometryRotation geometryRotation = getGeometryRotation(newTracker.getDirectionToLocationObject(aim.getlObject()).getDirection());
                arConfiguration.updateGeometryRotation(newTracker.getId(), geometryRotation);
            }
        }
    }

    private ARConfiguration.GeometryRotation getGeometryRotation(String directionIcon){
        ARConfiguration.GeometryRotation geometryRotation;
        if(directionIcon.equals(Direction.FORWARDS)){
            geometryRotation = GeometryRotation.UP;
        }else if(directionIcon.equals(Direction.RIGHT)){
            geometryRotation = GeometryRotation.RIGHT;
        }else if(directionIcon.equals(Direction.BACKWARDS)){
            geometryRotation = GeometryRotation.DOWN;
        }else if(directionIcon.equals(Direction.LEFT)){
            geometryRotation = GeometryRotation.LEFT;
        }else{
            throw new NullPointerException("DirectionIcon [" + directionIcon + "] does not match any of the " + GeometryRotation.class.getSimpleName());
        }
        return geometryRotation;
    }

}