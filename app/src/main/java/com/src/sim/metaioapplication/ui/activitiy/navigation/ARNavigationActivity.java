package com.src.sim.metaioapplication.ui.activitiy.navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.tools.io.AssetsManager;
import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.Arrow;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.Location;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Marker;
import com.src.sim.metaioapplication.metaio.CallBackHandler;
import com.src.sim.metaioapplication.ui.activitiy.start.StartMenuActivity;
import com.src.sim.metaioapplication.ui.fragment.object.ListObjectFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ARNavigationActivity extends ARViewActivity{

    private Map<Integer, List<IGeometry>> geometryMap;
    private CallBackHandler mCallbackHandler;

    private Location location;
    private History history;
    private Map<Integer, Marker> markerMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar_view);
        getActionBar().hide();

        location = Location.JsonToLocation(getIntent().getStringExtra(StartMenuActivity.LOCATIONONLYEXTRA));
        location.setId(Long.parseLong(getIntent().getStringExtra(StartMenuActivity.LOCATIONONLYIDEXTRA)));
        history = History.JsonToHistory(getIntent().getStringExtra(StartMenuActivity.HISTORYEXTRA));

        String mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/TrackingData.xml");
        markerMap = history.getMarkerMap();
        metaioSDK.setTrackingConfiguration(mTrackingFile);
    }

    public void showFragment(final Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_ar_flFragmentContainer, fragment)
                .commit();
    }

    public void handleLocationObjectClick(final LocationObject locationObject){
        mCallbackHandler.updateLocationObject(locationObject);
    }

    @Override
    protected int getGUILayout() {
        return R.layout.ar_view;
    }

    @Override
    protected IMetaioSDKCallback getMetaioSDKCallbackHandler() {
        return mCallbackHandler;
    }

    @Override
    protected void loadContents() {
        geometryMap = new HashMap<>();
        showFragment(ListObjectFragment.newInstance(location, history));
        loadGeometries();

        mCallbackHandler = new CallBackHandler(ARNavigationActivity.this, markerMap, geometryMap);
        metaioSDK.registerCallback(mCallbackHandler);
    }

    protected void loadGeometries(){
        for(Marker marker : markerMap.values()){
           loadGeometry(marker.getId(), Arrow.DEFAULT);
        }
    }

    protected IGeometry loadGeometry(int systemID, Arrow arrowRotation){
        String arrow = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow/arrow.md2");
        String arrowCurve = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow/arrowcurve.md2");
        String arrowTurn = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/arrow/arrowturn.md2");
        if(arrow != null){
            IGeometry geometryArrow = metaioSDK.createGeometry(arrow);
            IGeometry geometryArrowCurve = metaioSDK.createGeometry(arrowCurve);
            IGeometry geometryArrowTurn = metaioSDK.createGeometry(arrowTurn);
            if(geometryArrow != null && geometryArrowCurve != null){

                geometryArrow.setScale(new Vector3d(0.05f, 0.05f, 0.05f));
                geometryArrow.setVisible(false);
                geometryArrow.setCoordinateSystemID(systemID);
                geometryArrow.setRotation(arrowRotation.getGeometryRotation());

                geometryArrowCurve.setScale(new Vector3d(0.05f, 0.05f, 0.05f));
                geometryArrowCurve.setVisible(false);
                geometryArrowCurve.setCoordinateSystemID(systemID);
                geometryArrowCurve.setRotation(arrowRotation.getGeometryRotation());

                geometryArrowTurn.setScale(new Vector3d(0.05f, 0.05f, 0.05f));
                geometryArrowTurn.setVisible(false);
                geometryArrowTurn.setCoordinateSystemID(systemID);
                geometryArrowTurn.setRotation(arrowRotation.getGeometryRotation());

                List<IGeometry> geometries = new ArrayList<>();
                geometries.add(geometryArrow);
                geometries.add(geometryArrowCurve);
                geometries.add(geometryArrowTurn);

                geometryMap.put(systemID, geometries);
                MetaioDebug.log("Loaded geometry " + arrow);
                Log.d(ARNavigationActivity.class.getSimpleName(), "Loaded geometry [" + systemID + "] - " + arrow);
            }else{
                Log.i(ARNavigationActivity.class.getSimpleName(), "Error loading geometry [" + systemID + "] - " + arrow);
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
}
