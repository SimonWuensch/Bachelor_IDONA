package com.src.sim.metaioapplication.ui.activitiy.navigation;

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

        location = LocationOnly.JsonToLocationOnly(getIntent().getStringExtra(StartMenuActivity.LOCATIONONLYEXTRA));
        location.setId(Long.parseLong(getIntent().getStringExtra(StartMenuActivity.LOCATIONONLYIDEXTRA)));
        history = History.JsonToHistory(getIntent().getStringExtra(StartMenuActivity.HISTORYEXTRA));

        String mTrackingFile = AssetsManager.getAssetPath(getBaseContext(), "AssetsOne/TrackingData.xml");
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
        return mCallbackHandler;
    }

    @Override
    protected void loadContents() {
        geometryMap = new HashMap<>();
        showFragment(ListObjectFragment.newInstance(location, history));
        loadGeometries();

        mCallbackHandler = new CallBackHandler(ARNavigationActivity.this, trackerMap, geometryMap);
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

    public void print(View view){
        Log.d(ARNavigationActivity.class.getSimpleName(), x + "f, " + y + "f, " + z + "f");
    }

    public void rotateX(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }
        x += 0.01f;
        if(geometry != null)
            geometry.setRotation(new Rotation(x, y, z));
    }

    public void rotateY(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }
        y += 0.01f;
        if(geometry != null)
            geometry.setRotation(new Rotation(x, y, z));
    }

    public void rotateZ(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }
        z += 0.01f;
        if(geometry != null)
            geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateY(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }

        y -= 0.01f;
        if(geometry != null)
            geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateX(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }
        x -= 0.01f;
        if(geometry != null)
            geometry.setRotation(new Rotation(x, y, z));
    }

    public void minRotateZ(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }
        z -= 0.01f;
        if(geometry != null)
            geometry.setRotation(new Rotation(x, y, z));
    }

    int count = 1;
    public void zero(View view){
        IGeometry geometry = null;
        try{
            geometry = mCallbackHandler.getCurrentIGeometry();
        }catch(Throwable t){
            t.printStackTrace();
        }

        Direction.ArrowRotation arrowRotation = null;
        if(count == 1){
            arrowRotation= Direction.ArrowRotation.RIGHT_BACKWARDS;
        }else if(count == 2){
            arrowRotation= Direction.ArrowRotation.LEFT_BACKWARDS;
        }else if(count == 3){
            arrowRotation= Direction.ArrowRotation.RIGHT_FORWARDS;
        }else if(count == 4){
            arrowRotation= Direction.ArrowRotation.LEFT_FORWARDS;
        }else if(count == 5){
            arrowRotation= Direction.ArrowRotation.FORWARDS_LEFT;
        }else if(count == 6){
            arrowRotation= Direction.ArrowRotation.FORWARDS_RIGHT;
        }else if(count == 7){
            arrowRotation= Direction.ArrowRotation.BACKWARDS_LEFT;
        }else if(count == 8){
            arrowRotation= Direction.ArrowRotation.BACKWARDS_RIGHT;
        }else if(count == 9){
            arrowRotation= Direction.ArrowRotation.LEFT;
        }else if(count == 10){
            arrowRotation= Direction.ArrowRotation.RIGHT;
        }else if(count == 11){
            arrowRotation= Direction.ArrowRotation.FORWARDS;
        }else if(count == 12){
            arrowRotation= Direction.ArrowRotation.BACKWARDS;
        }else if(count == 13){
            arrowRotation= Direction.ArrowRotation.LEFT_UP;
        }else if(count == 14){
            arrowRotation= Direction.ArrowRotation.LEFT_DOWN;
        }else if(count == 15){
            arrowRotation= Direction.ArrowRotation.RIGHT_UP;
        }else if(count == 16){
            arrowRotation= Direction.ArrowRotation.RIGHT_DOWN;
        }else if(count == 17){
            arrowRotation= Direction.ArrowRotation.UP;
        }else if(count == 18){
            arrowRotation= Direction.ArrowRotation.DOWN;
        }

        if(geometry != null){count++;
            if(count == 19){
                count = 1;
            }
            final String arrow = arrowRotation.name();
            ARNavigationActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ARNavigationActivity.this, "ArrorRotation: " + arrow, Toast.LENGTH_LONG).show();
                }
            });
            x = arrowRotation.getRotation()[0];
            y = arrowRotation.getRotation()[1];
            z = arrowRotation.getRotation()[2];
            geometry.setRotation(arrowRotation.getGeometryRotation());
        }

    }
}
