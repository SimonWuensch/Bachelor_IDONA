package com.src.sim.metaioapplication.metaio;

import android.content.Intent;
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
import com.src.sim.metaioapplication.ui.activitiy.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

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
    private List<IGeometry> geometries;
    private MetaioSDKCallbackHandler mCallbackHandler;

    private History history;
    private LocationObject locationObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        history = History.JsonToHistory(getIntent().getStringExtra(MainActivity.HISTORYEXTRA));
        locationObject = LocationObject.JsonToLocationObject(getIntent().getStringExtra(MainActivity.LOCATIONOBJECTEXTRA));

        geometries = new ArrayList<>();
        mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/TrackingData_MarkerlessFast.xml");
        mCallbackHandler = new MetaioSDKCallbackHandler(this, history, locationObject);
        metaioSDK.registerCallback(mCallbackHandler);
        metaioSDK.setTrackingConfiguration(mTrackingFile);
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
        loadGeometry(1, GeometryRotation.UP);
        loadGeometry(2, GeometryRotation.RIGHT);
        loadGeometry(3, GeometryRotation.DOWN);
        loadGeometry(4, GeometryRotation.LEFT);
    }

    private IGeometry loadGeometry(int systemID, GeometryRotation geometryRotation){
        String modelPath = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow.md2");
        if(modelPath != null){
            IGeometry geometry = metaioSDK.createGeometry(modelPath);
            if(geometry != null){
                geometry.setScale(new Vector3d(0.1f, 0.1f, 0.1f));
                geometry.setVisible(true);
                geometry.setCoordinateSystemID(systemID);
                geometry.setRotation(geometryRotation.getRotation());
                geometries.add(geometry);
                MetaioDebug.log("Loaded geometry " + modelPath);
                Log.d("Info", "Loaded geometry " + modelPath);
            }else{
                MetaioDebug.log(Log.ERROR, "Error loading geometry " + modelPath);
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

    /*
    public void print(View view){
        Log.d(ARConfiguration.class.getSimpleName(), x + " - " + y + " - " + z);
        Log.d("Sendsortyp" , "");
    }

    public void rotateX(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                x += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void rotateY(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                y += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void rotateZ(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                z += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void minRotateY(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                y -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void minRotateX(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                x -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void minRotateZ(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                z -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    }

    public void zero(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                x = 0;
                y = 0;
                z = 0;
                geometry.setRotation(new Rotation(x, y, z));
            }
        }
    } */
}
