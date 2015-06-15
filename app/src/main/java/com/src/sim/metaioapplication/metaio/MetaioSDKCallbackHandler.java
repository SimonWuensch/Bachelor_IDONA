package com.src.sim.metaioapplication.metaio;

import android.util.Log;

import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;

/**
 * Created by Simon on 15.06.2015.
 */
public class MetaioSDKCallbackHandler extends IMetaioSDKCallback {


    @Override
    public void onTrackingEvent(TrackingValuesVector trackingValues) {
        for (int i = 0; i < trackingValues.size(); i++) {
            final TrackingValues value = trackingValues.get(i);
            Log.i("xmetaio", "CoordinateSystemID: " + value.getCoordinateSystemID());

        }
    }
}