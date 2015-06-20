package com.src.sim.metaioapplication.metaio;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.Rotation;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.tools.io.AssetsManager;
import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;
import com.src.sim.metaioapplication.ui.activitiy.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Simon on 09.04.2015.
 */
public class ARConfiguration extends ARViewActivity{

    public enum GeometryRotation{
        LEFT(0.0f, 0.0f, 3.1599975f),
        UP(0.0f, 0.0f, 1.5999988f),
        RIGHT_UP(0.0f, 0.0f ,0.51999986f),
        LEFT_UP(0.0f, 0.0f ,2.639998f),
        RIGHT(0.0f, 0.0f ,0.0f),
        DOWN(0.0f, 0.0f ,-1.5999988f),
        RIGHT_DOWN(0.0f, 0.0f ,-0.43999988f),
        LEFT_DOWN(0.0f, 0.0f ,-2.719998f);

        private Rotation rotation;

        private GeometryRotation(float x, float y, float z){
            rotation = new Rotation(x, y, z);
        }

        public Rotation getRotation(){
            return rotation;
        }
    }

    private String mTrackingFile;
    private Map<Integer, IGeometry> geometryMap;
    private MetaioSDKCallbackHandler mCallbackHandler;

    private History history;
    private LocationObject locationObject;
    private Map<Integer, Tracker> trackerMap;


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
        Log.d(ARConfiguration.class.getSimpleName(), "loadContents");
        loadGeometries();
    }

    protected void loadGeometries(){
       for(Tracker tracker : trackerMap.values()){
           loadGeometry(tracker.getId(), GeometryRotation.RIGHT);
        }
    }

    protected IGeometry loadGeometry(int systemID, GeometryRotation geometryRotation){
        String modelPath = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow/arrow.md2");
        if(modelPath != null){
            IGeometry geometry = metaioSDK.createGeometry(modelPath);
            if(geometry != null){
                geometry.setScale(new Vector3d(0.08f, 0.08f, 0.08f));
                geometry.setVisible(true);
                geometry.setCoordinateSystemID(systemID);
                geometry.setRotation(geometryRotation.getRotation());
                geometryMap.put(systemID, geometry);
                MetaioDebug.log("Loaded geometry " + modelPath);
                Log.i(ARConfiguration.class.getSimpleName(), "Loaded geometry [" + systemID + "] - " + modelPath);
            }else{
                MetaioDebug.log(Log.ERROR, "Error loading geometry [" + systemID + "] - " + modelPath);
            }
            return geometry;
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

    protected void updateGeometryRotation(int systemId, GeometryRotation geometryRotation){
        IGeometry geometry = geometryMap.get(systemId);
        geometry.setRotation(geometryRotation.getRotation());
        Log.i(ARConfiguration.class.getSimpleName(), "Geometry [" + systemId + "] set to Rotation " + geometryRotation.name());
    }

    /*
    public void print(View view){
        Log.d(ARConfiguration.class.getSimpleName(), x + " - " + y + " - " + z);
        Log.d("Sendsortyp" , "");
    }

    public void rotateX(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                x += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void rotateY(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                y += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void rotateZ(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                z += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void minRotateY(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                y -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void minRotateX(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                x -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void minRotateZ(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                z -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void zero(View view){
        for(IGeometry geometry : geometryMap.values()){
            if(geometry.isVisible()){
                x = 0;
                y = 0;
                z = 0;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    } */
}
