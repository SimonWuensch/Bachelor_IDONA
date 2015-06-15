package com.src.sim.metaioapplication.model;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.Rotation;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.sdk.jni.Vector4d;
import com.metaio.tools.io.AssetsManager;
import com.src.sim.metaioapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Simon on 09.04.2015.
 */
public class ARConfiguration extends ARViewActivity{

    private String mTrackingFile;
    //0, 0, 1, 1.6 oben
    //rechts 1, 0, 0, 0


    private List<IGeometry> geometries;
    float x = 0f;
    float y = 0f;
    float z = 0f;

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
        geometries = new ArrayList<>();
        mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "Assets2/TrackingData_MarkerlessFast.xml");

        boolean result = metaioSDK.setTrackingConfiguration(mTrackingFile);

        MetaioDebug.log("Tracking data loaded: " + result);
        String modelPath = AssetsManager.getAssetPath(getBaseContext(), "Assets2/arrow.md2");

        geometries.add(loadGeometry(modelPath, 1));
        geometries.add(loadGeometry(modelPath, 2));
        geometries.add(loadGeometry(modelPath, 3));
        geometries.add(loadGeometry(modelPath, 4));
    }

    private IGeometry loadGeometry(String modelPath, int systemID){
        if(modelPath != null){
            IGeometry geometry = metaioSDK.createGeometry(modelPath);
            if(geometry != null){
                geometry.setScale(new Vector3d(0.1f, 0.1f, 0.1f));
                geometry.setVisible(true);
                geometry.setCoordinateSystemID(systemID);
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

    }

    public void rotateX(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                x += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation X " + geometry.getRotation().getAxisAngle());
            }
        }
    }

    public void rotateY(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                y += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation Y " + geometry.getRotation().getAxisAngle());
            }
        }
    }

    public void rotateZ(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                z += 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation Z "  + geometry.getRotation().getAxisAngle());
            }
        }
    }

    public void minRotateY(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                y -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation Z "  + geometry.getRotation().getAxisAngle());
            }
        }
    }

    public void minRotateX(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                x -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation Z "  + geometry.getRotation().getAxisAngle());
            }
        }
    }

    public void minRotateZ(View view){
        for(IGeometry geometry : geometries){
            if(geometry.isVisible()){
                z -= 0.01f;
                geometry.setRotation(new Rotation(x, y, z));
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation Z " + geometry.getCoordinateSystemID() + " - "  + geometry.getRotation().getAxisAngle());
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
                Log.d(ARConfiguration.class.getSimpleName(), "Rotation Z "  + geometry.getRotation().getAxisAngle());
            }
        }
    }
}
