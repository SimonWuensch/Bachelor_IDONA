package com.src.sim.metaioapplication.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Marker;
import com.src.sim.metaioapplication.metaio.CallBackHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WayAlgorithm extends AsyncTask<Integer, Integer, Map<Marker, Direction>> {

    private Marker marker;
    private LocationObject locationObject;
    private com.src.sim.metaioapplication.logic.WayAlgorithm wayAlgorithm;
    private CallBackHandler callbackHandler;

    public WayAlgorithm(CallBackHandler callbackHandler, com.src.sim.metaioapplication.logic.WayAlgorithm wayAlgorithm, Marker marker, LocationObject locationObject) {
        this.locationObject = locationObject;
        this.wayAlgorithm = wayAlgorithm;
        this.marker = marker;
        this.callbackHandler = callbackHandler;
    }

    public static void calculateWay(CallBackHandler callbackHandler, com.src.sim.metaioapplication.logic.WayAlgorithm markerAlgo, Marker marker, LocationObject locationObject){
        WayAlgorithm wayAlgorithm = new WayAlgorithm(callbackHandler, markerAlgo, marker, locationObject);
        wayAlgorithm.execute();
    }

    @Override
    protected Map<Marker, Direction> doInBackground(Integer... integers) {
        Map<Aim, List<List<Marker>>> ways = wayAlgorithm.findWays(marker, locationObject);
        return initializeMarkers(ways);
    }

    private Map<Marker, Direction> initializeMarkers(Map<Aim, List<List<Marker>>> ways){
        Map<Marker, Direction> markerDirectionMap = new HashMap<Marker, Direction>();

        Log.d(WayAlgorithm.class.getSimpleName(), "Markers initialization..." );
        Set<Aim> aims = ways.keySet();
        if(aims.size() <= 0){
            return new HashMap<Marker, Direction>();
        }

        Aim aim = (Aim)ways.keySet().toArray()[0];
        List<Marker> markers = ways.get(aim).get(0);
        com.src.sim.metaioapplication.logic.WayAlgorithm.printWay(markers, aim);
        Direction direction = null;

        callbackHandler.resetIDList();
        callbackHandler.addIDToIDList(markers.get(0).getId());
        for(int i = 1; i < markers.size(); i++){
            callbackHandler.addIDToIDList(markers.get(i).getId());
            Marker oldMarker = markers.get(i-1);
            Marker newMarker = markers.get(i);

            direction = oldMarker.getDirectionToMarker(newMarker);
            markerDirectionMap.put(oldMarker, direction);

            if(i == markers.size() - 1){
                direction = newMarker.getDirectionToLocationObject(aim.getlObject());
                markerDirectionMap.put(newMarker, direction);
            }
        }
        if(markers.size() == 1){
            Marker marker = markers.get(0);
            markerDirectionMap.put(marker, marker.getDirectionToLocationObject(locationObject));
        }

        return markerDirectionMap;
    }

    @Override
    protected void onPostExecute(Map<Marker, Direction> markerDirectionMap) {
        super.onPostExecute(markerDirectionMap);

        for(Marker marker : markerDirectionMap.keySet()){
            Direction direction = markerDirectionMap.get(marker);
            Log.d(WayAlgorithm.class.getSimpleName(), "Update Rotation from IGeometry with id " + marker.getId());
            callbackHandler.updateGeometryRotation(marker.getId(), direction);
        }
    }
}