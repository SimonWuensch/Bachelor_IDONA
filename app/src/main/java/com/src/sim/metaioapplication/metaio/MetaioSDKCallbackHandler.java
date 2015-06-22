package com.src.sim.metaioapplication.metaio;


import android.util.Log;

import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.src.sim.metaioapplication.asynctask.WayAlgorithm;
import com.src.sim.metaioapplication.logic.TrackerAlgo;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetaioSDKCallbackHandler extends IMetaioSDKCallback {

    private ARConfiguration arConfiguration;
    private Map<Integer, Tracker> trackerMap;
    private LocationObject locationObject;
    private TrackerAlgo trackerAlgo;
    private boolean isFirstStart = true;

    public MetaioSDKCallbackHandler(ARConfiguration arConfiguration, Map<Integer, Tracker> trackerMap, LocationObject locationObject){
        this.arConfiguration = arConfiguration;
        this.trackerMap = trackerMap;
        this.locationObject = locationObject;
        this.trackerAlgo = new TrackerAlgo(trackerMap);
    }

    @Override
    public void onTrackingEvent(TrackingValuesVector trackingValues) {
         if (trackingValues.size() >= 1 && !isFirstStart) {
             TrackingValues value = trackingValues.get(0);
             int systemId = value.getCoordinateSystemID();
             Log.d(MetaioSDKCallbackHandler.class.getSimpleName(), "Tracker with id " + systemId + " detected.");
             arConfiguration.setCurrentTrackerID(systemId);
             Tracker startTracker = trackerMap.get(systemId);
             WayAlgorithm.calculateWay(arConfiguration, startTracker, locationObject, trackerAlgo);
         }
         isFirstStart = false;
     }
}