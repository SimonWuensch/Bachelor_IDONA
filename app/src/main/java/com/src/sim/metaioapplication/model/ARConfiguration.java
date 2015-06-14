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

import java.util.Random;

/**
 * Created by Simon on 09.04.2015.
 */
public class ARConfiguration extends ARViewActivity{

    private String mTrackingFile;
    private IGeometry arrowOne;
    private IGeometry arrowTwo;
    private IGeometry arrowThree;
    private IGeometry arrowFour;
    private IGeometry arrowFive;
    private IGeometry arrowSix;
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
        mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "Assets2/TrackingData_MarkerlessFast.xml");

        boolean result = metaioSDK.setTrackingConfiguration(mTrackingFile);
        MetaioDebug.log("Tracking data loaded: " + result);
        String modelPath = AssetsManager.getAssetPath(getBaseContext(), "Assets2/arrow.md2");

        loadGeometry(modelPath, arrowOne, 1);
        loadGeometry(modelPath, arrowTwo, 2);
        loadGeometry(modelPath, arrowThree, 3);
        loadGeometry(modelPath, arrowFour, 4);
        loadGeometry(modelPath, arrowFive, 5);
        loadGeometry(modelPath, arrowSix, 6);
    }

    private void loadGeometry(String modelPath, IGeometry geometry, int systemID){
        if(modelPath != null){
            geometry = metaioSDK.createGeometry(modelPath);
            if(geometry != null){
                geometry.setScale(new Vector3d(0.25f, 0.25f, 0.25f));
                geometry.setVisible(true);
                geometry.setCoordinateSystemID(systemID);
                MetaioDebug.log("Loaded geometry " + modelPath);
                Log.d("Info", "Loaded geometry " + modelPath);
            }else{
                MetaioDebug.log(Log.ERROR, "Error loading geometry " + modelPath);
            }
        }
    }

    @Override
    protected void onGeometryTouched(IGeometry geometry) {

    }
}
