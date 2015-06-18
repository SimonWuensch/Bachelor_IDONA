package com.src.sim.metaioapplication.metaio;

import android.util.Log;

import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.src.sim.metaioapplication.logic.TrackerAlgo;
import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;

import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 15.06.2015.
 */
public class MetaioSDKCallbackHandler extends IMetaioSDKCallback {

    private ARConfiguration arConfiguration;
    private History history;
    private LocationObject locationObject;

    private boolean started = false;

    public MetaioSDKCallbackHandler(ARConfiguration arConfiguration, History history, LocationObject locationObject){
        this.arConfiguration = arConfiguration;
        this.history = history;
        this.locationObject = locationObject;
    }

    @Override
    public void onTrackingEvent(TrackingValuesVector trackingValues) {

        if( trackingValues.size() > 1){
            TrackingValues value = trackingValues.get(0);
            Map<Integer, Tracker> trackerMap = history.getTrackerMap();
            TrackerAlgo trackerAlgo = new TrackerAlgo(trackerMap);

            Map<Aim, List<List<Tracker>>> ways = trackerAlgo.findWays(trackerMap.get(value.getCoordinateSystemID()), locationObject);
            started = true;
            for(Aim aim : ways.keySet()){
                for(List<Tracker> trackers : ways.get(aim))
                TrackerAlgo.printWay(trackers, aim);
            }

        }

        /*for (int i = 0; i < trackingValues.size(); i++) {
            final TrackingValues value = trackingValues.get(i);
            Log.i("xmetaio", "CoordinateSystemID: " + value.getCoordinateSystemID());

        }_*/
    }
}