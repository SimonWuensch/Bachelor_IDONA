package com.src.sim.metaioapplication.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.src.sim.metaioapplication.logic.TrackerAlgo;
import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;
import com.src.sim.metaioapplication.metaio.CallBackHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WayAlgorithm extends AsyncTask<Integer, Integer, Map<Tracker, Direction>> {

    private Tracker tracker;
    private LocationObject locationObject;
    private TrackerAlgo trackerAlgo;
    private CallBackHandler callbackHandler;

    public WayAlgorithm(CallBackHandler callbackHandler, TrackerAlgo trackerAlgo, Tracker tracker, LocationObject locationObject) {
        this.locationObject = locationObject;
        this.trackerAlgo = trackerAlgo;
        this.tracker = tracker;
        this.callbackHandler = callbackHandler;
    }

    public static void calculateWay(CallBackHandler callbackHandler, TrackerAlgo trackerAlgo, Tracker tracker, LocationObject locationObject){
        WayAlgorithm wayAlgorithm = new WayAlgorithm(callbackHandler, trackerAlgo, tracker, locationObject);
        wayAlgorithm.execute();
    }

    @Override
    protected Map<Tracker, Direction> doInBackground(Integer... integers) {
        Map<Aim, List<List<Tracker>>> ways = trackerAlgo.findWays(tracker, locationObject);
        return initializeTrackers(ways);
    }

    private Map<Tracker, Direction> initializeTrackers(Map<Aim, List<List<Tracker>>> ways){
        Map<Tracker, Direction> trackerDirectionMap = new HashMap<Tracker, Direction>();

        Log.d(WayAlgorithm.class.getSimpleName(), "Trackers initialization..." );
        Set<Aim> aims = ways.keySet();
        if(aims.size() <= 0){
            return new HashMap<Tracker, Direction>();
        }

        Aim aim = (Aim)ways.keySet().toArray()[0];
        List<Tracker> trackers = ways.get(aim).get(0);
        TrackerAlgo.printWay(trackers, aim);
        Direction direction = null;

        callbackHandler.resetIDList();
        callbackHandler.addIDToIDList(trackers.get(0).getId());
        for(int i = 1; i < trackers.size(); i++){
            callbackHandler.addIDToIDList(trackers.get(i).getId());
            Tracker oldTracker = trackers.get(i-1);
            Tracker newTracker = trackers.get(i);

            direction = oldTracker.getDirectionToTracker(newTracker);
            trackerDirectionMap.put(oldTracker, direction);

            if(i == trackers.size() - 1){
                direction = newTracker.getDirectionToLocationObject(aim.getlObject());
                trackerDirectionMap.put(newTracker, direction);
            }
        }
        if(trackers.size() == 1){
            Tracker tracker = trackers.get(0);
            trackerDirectionMap.put(tracker, tracker.getDirectionToLocationObject(locationObject));
        }

        return trackerDirectionMap;
    }

    @Override
    protected void onPostExecute(Map<Tracker, Direction> trackerDirectionMap) {
        super.onPostExecute(trackerDirectionMap);

        for(Tracker tracker : trackerDirectionMap.keySet()){
            Direction direction = trackerDirectionMap.get(tracker);
            Log.d(WayAlgorithm.class.getSimpleName(), "Update Rotation from IGeometry with id " + tracker.getId());
            callbackHandler.updateGeometryRotation(direction.getArrow(), tracker.getId(), direction);
        }
    }
}