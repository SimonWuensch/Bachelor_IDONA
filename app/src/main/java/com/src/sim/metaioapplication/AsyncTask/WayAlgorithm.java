package com.src.sim.metaioapplication.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.metaio.sdk.jni.IGeometry;
import com.src.sim.metaioapplication.logic.TrackerAlgo;
import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;
import com.src.sim.metaioapplication.metaio.ARConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 17.06.2015.
 */
public class WayAlgorithm extends AsyncTask<Integer, Integer, Boolean> {

    private ARConfiguration activity;
    private Tracker tracker;
    private LocationObject locationObject;
    private TrackerAlgo trackerAlgo;

    public WayAlgorithm(ARConfiguration activity, Tracker tracker, LocationObject locationObject, TrackerAlgo trackerAlgo) {
        this.activity = activity;
        this.locationObject = locationObject;
        this.trackerAlgo = trackerAlgo;
        this.tracker = tracker;
    }

    public static void calculateWay(ARConfiguration activity, Tracker tracker, LocationObject locationObject, TrackerAlgo trackerAlgo){
        WayAlgorithm wayAlgorithm = new WayAlgorithm(activity, tracker, locationObject, trackerAlgo);
        //IGeometry currentGeometry = activity.getCurrentIGeometry();
        //if (tracker != null && currentGeometry.getRotation().equals(Direction.ArrowRotation.DEFAULT.getGeometryRotation()))
        if(tracker != null)
            wayAlgorithm.execute();
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Log.d(WayAlgorithm.class.getSimpleName(), "do in Background");
        Map<Aim, List<List<Tracker>>> ways = trackerAlgo.findWays(tracker, locationObject);
        initializeTrackers(ways);
        return true;
    }

    private void initializeTrackers(Map<Aim, List<List<Tracker>>> ways){
        Log.d(WayAlgorithm.class.getSimpleName(), "initializeTrackers");
        Aim aim = (Aim)ways.keySet().toArray()[0];
        List<Tracker> trackers = ways.get(aim).get(0);
        TrackerAlgo.printWay(trackers, aim);
        Direction direction = null;

        for(int i = 1; i < trackers.size(); i++){
            Tracker oldTracker = trackers.get(i-1);
            Tracker newTracker = trackers.get(i);

            direction = oldTracker.getDirectionToTracker(newTracker);
            activity.updateGeometryRotation(direction.getArrow(), oldTracker.getId(), direction);

            if(i == trackers.size() - 1){
                direction = newTracker.getDirectionToLocationObject(aim.getlObject());
                activity.updateGeometryRotation(direction.getArrow(), newTracker.getId(), direction);
            }
        }
    }
}