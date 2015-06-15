package com.src.sim.metaioapplication.metaio;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 09.04.2015.
 */
public class ARConfiguration extends ARViewActivity{

    private String mTrackingFile;

   /* private static Rotation LEFT = new Rotation(0.0f, 0.0f, 3.1599975f);
    private static Rotation UP = new Rotation(0.0f, 0.0f, 1.5999988f);
    private static Rotation RIGHT_UP = new Rotation(0.0f, 0.0f ,0.51999986f);
    private static Rotation LEFT_UP = new Rotation(0.0f, 0.0f ,2.639998f);
    private static Rotation RIGHT = new Rotation(0.0f, 0.0f ,0.0f);
    private static Rotation DOWN = new Rotation(0.0f, 0.0f ,-1.5999988f);
    private static Rotation RIGHT_DOWN = new Rotation(0.0f, 0.0f ,-0.43999988f);
    private static Rotation LEFT_DOWN = new Rotation(0.0f, 0.0f ,-2.719998f);*/

    private List<IGeometry> geometries;
    private MetaioSDKCallbackHandler mCallbackHandler;

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
        mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/TrackingData_MarkerlessFast.xml");
        mCallbackHandler = new MetaioSDKCallbackHandler();
        metaioSDK.registerCallback(mCallbackHandler);
        boolean result = metaioSDK.setTrackingConfiguration(mTrackingFile);

        MetaioDebug.log("Tracking data loaded: " + result);
    }

    private IGeometry loadGeometry(int systemID){
        String modelPath = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow.md2");
        if(modelPath != null){
            IGeometry geometry = metaioSDK.createGeometry(modelPath);
            if(geometry != null){
                geometry.setScale(new Vector3d(0.1f, 0.1f, 0.1f));
                geometry.setVisible(true);
                geometry.setCoordinateSystemID(systemID);
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
    }
}
