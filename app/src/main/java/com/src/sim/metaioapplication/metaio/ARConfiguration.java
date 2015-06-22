package com.src.sim.metaioapplication.metaio;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.Rotation;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.tools.io.AssetsManager;
import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;
import com.src.sim.metaioapplication.ui.activitiy.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 09.04.2015.
 */
public class ARConfiguration extends ARViewActivity{

    private String mTrackingFile;
    private Map<Integer, List<IGeometry>> geometryMap;
    private MetaioSDKCallbackHandler mCallbackHandler;

    private History history;
    private LocationObject locationObject;
    private Map<Integer, Tracker> trackerMap;

    private int currentTrackerID = -1;

    private float x = 0;
    private float y = 0;
    private float z = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(ARConfiguration.class.getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        history = History.JsonToHistory(getIntent().getStringExtra(MainActivity.HISTORYEXTRA));
        locationObject = LocationObject.JsonToLocationObject(getIntent().getStringExtra(MainActivity.LOCATIONOBJECTEXTRA));

        mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/TrackingData_MarkerlessFast.xml");
        trackerMap = history.getTrackerMap();

        mCallbackHandler = new MetaioSDKCallbackHandler(this, trackerMap, locationObject);
        metaioSDK.setTrackingConfiguration(mTrackingFile);
        metaioSDK.registerCallback(mCallbackHandler);
    }

    @Override
    protected int getGUILayout() {
        return R.layout.ar_view;
    }

    @Override
    protected IMetaioSDKCallback getMetaioSDKCallbackHandler() {
        return null;
    }

    @Override
    protected void loadContents() {
        geometryMap = new HashMap<>();
        loadGeometries();
    }

    protected void loadGeometries(){
       for(Tracker tracker : trackerMap.values()){
           loadGeometry(tracker.getId(), Direction.ArrowRotation.DEFAULT);
        }
    }

    protected IGeometry loadGeometry(int systemID, Direction.ArrowRotation arrowRotation){
        String arrow = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow/arrow.md2");
        String arrowCurve = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow/arrowcurve.md2");
        if(arrow != null){
            IGeometry geometryArrow = metaioSDK.createGeometry(arrow);
            IGeometry geometryArrowCurve = metaioSDK.createGeometry(arrowCurve);
            if(geometryArrow != null){
                geometryArrow.setScale(new Vector3d(0.08f, 0.08f, 0.08f));
                geometryArrow.setVisible(false);
                geometryArrow.setCoordinateSystemID(systemID);
                geometryArrow.setRotation(arrowRotation.getGeometryRotation());

                geometryArrowCurve.setScale(new Vector3d(0.08f, 0.08f, 0.08f));
                geometryArrowCurve.setVisible(false);
                geometryArrowCurve.setCoordinateSystemID(systemID);
                geometryArrowCurve.setRotation(arrowRotation.getGeometryRotation());

                List<IGeometry> geometries = new ArrayList<>();
                geometries.add(geometryArrow);
                geometries.add(geometryArrowCurve);

                geometryMap.put(systemID, geometries);
                MetaioDebug.log("Loaded geometry " + arrow);
                Log.i(ARConfiguration.class.getSimpleName(), "Loaded geometry [" + systemID + "] - " + arrow);
            }else{
                MetaioDebug.log(Log.ERROR, "Error loading geometry [" + systemID + "] - " + arrow);
            }
            return geometryArrow;
        }
        return null;
    }

    @Override
    protected void onGeometryTouched(IGeometry geometry) {
        Log.d("Touch", geometry.getCoordinateSystemID() + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallbackHandler.delete();
        mCallbackHandler = null;
    }

    public void setCurrentTrackerID(int systemID){
        currentTrackerID = systemID;
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
        Log.i(ARConfiguration.class.getSimpleName(), "Geometry [" + systemId + "] set to Rotation " + direction.getRotation().name());
    }

    public void print(View view){
        Log.d(ARConfiguration.class.getSimpleName(), x + " - " + y + " - " + z);
        Toast.makeText(this, x + " - " + y + " - " + z, Toast.LENGTH_LONG).show();
    }

    public void rotateX(View view){
        IGeometry geometry = getCurrentIGeometry();
        x += 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void rotateY(View view){
        IGeometry geometry = getCurrentIGeometry();
        y += 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void rotateZ(View view){
        IGeometry geometry = getCurrentIGeometry();
        z += 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateY(View view){
        IGeometry geometry = getCurrentIGeometry();
        y -= 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateX(View view){
        IGeometry geometry = getCurrentIGeometry();
        x -= 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateZ(View view){
        IGeometry geometry = getCurrentIGeometry();
        z -= 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void zero(View view){
        IGeometry geometry = getCurrentIGeometry();
        x = 0;
        y = 0;
        z = 0;
        geometry.setRotation(new Rotation(x, y, z));
    }
}
