package com.src.sim.metaioapplication.metaio;

import android.app.Fragment;
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
import com.src.sim.metaioapplication.logic.resource.LocationOnly;
import com.src.sim.metaioapplication.logic.resource.Tracker;
import com.src.sim.metaioapplication.ui.activitiy.main.MainActivity;
import com.src.sim.metaioapplication.ui.fragment.object.ListObjectFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ARConfiguration extends ARViewActivity{

    private Map<Integer, List<IGeometry>> geometryMap;
    private CallBackHandler mCallbackHandler;

    private LocationOnly location;
    private History history;
    private Map<Integer, Tracker> trackerMap;

    private float x = 0;
    private float y = 0;
    private float z = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar_view);
        getActionBar().hide();

        location = LocationOnly.JsonToLocationOnly(getIntent().getStringExtra(MainActivity.LOCATIONONLYEXTRA));
        location.setId(Long.parseLong(getIntent().getStringExtra(MainActivity.LOCATIONONLYIDEXTRA)));
        history = History.JsonToHistory(getIntent().getStringExtra(MainActivity.HISTORYEXTRA));

        String mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/TrackingData_MarkerlessFast.xml");
        trackerMap = history.getTrackerMap();
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
        return null;
    }

    @Override
    protected void loadContents() {
        geometryMap = new HashMap<>();
        showFragment(ListObjectFragment.newInstance(location, history));
        loadGeometries();

        mCallbackHandler = new CallBackHandler(ARConfiguration.this, trackerMap, geometryMap);
        metaioSDK.registerCallback(mCallbackHandler);
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
            if(geometryArrow != null && geometryArrowCurve != null){
                geometryArrow.setScale(new Vector3d(0.05f, 0.05f, 0.05f));
                geometryArrow.setVisible(false);
                geometryArrow.setCoordinateSystemID(systemID);
                geometryArrow.setRotation(arrowRotation.getGeometryRotation());

                geometryArrowCurve.setScale(new Vector3d(0.05f, 0.05f, 0.05f));
                geometryArrowCurve.setVisible(false);
                geometryArrowCurve.setCoordinateSystemID(systemID);
                geometryArrowCurve.setRotation(arrowRotation.getGeometryRotation());

                List<IGeometry> geometries = new ArrayList<>();
                geometries.add(geometryArrow);
                geometries.add(geometryArrowCurve);

                geometryMap.put(systemID, geometries);
                MetaioDebug.log("Loaded geometry " + arrow);
                Log.d(ARConfiguration.class.getSimpleName(), "Loaded geometry [" + systemID + "] - " + arrow);
            }else{
                Log.i(ARConfiguration.class.getSimpleName(), "Error loading geometry [" + systemID + "] - " + arrow);
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

    public void print(View view){
        Log.d(ARConfiguration.class.getSimpleName(), x + " - " + y + " - " + z);
        Toast.makeText(this, x + " - " + y + " - " + z, Toast.LENGTH_LONG).show();
    }

    public void rotateX(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        x += 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void rotateY(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        y += 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void rotateZ(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        z += 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateY(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        y -= 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateX(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        x -= 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateZ(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        z -= 0.01f;
        geometry.setRotation(new Rotation(x, y, z));
    }

    public void zero(View view){
        IGeometry geometry = mCallbackHandler.getCurrentIGeometry();
        x = 0;
        y = 0;
        z = 0;
        geometry.setRotation(new Rotation(x, y, z));
    }
}
