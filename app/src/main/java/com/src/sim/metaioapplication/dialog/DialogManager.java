package com.src.sim.metaioapplication.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.metaio.ARConfiguration;

import java.util.Timer;
import java.util.TimerTask;

public class DialogManager {

    private Activity activity;
    private ProgressDialog mDialog;
    private AlertDialog aimFoundDialog;

    public DialogManager(Activity activity){
        this.activity = activity;
    }

    public void toggleLoading(final boolean shouldRunning){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mDialog == null)
                    mDialog = new ProgressDialog(activity);

                if(!mDialog.isShowing() && shouldRunning) {
                    mDialog.setMessage("Loading...");
                    mDialog.setCancelable(false);
                    mDialog.show();
                }else{
                    mDialog.dismiss();
                }
            }
        });
    }

    public void aimFoundDialog(Direction direction, LocationObject locationObject){
        final String wayDescriptionToLocationObject = direction.getWayDescriptionToLocationObject(locationObject);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(aimFoundDialog == null) {
                    aimFoundDialog = new AlertDialog.Builder(activity)
                            .setMessage(wayDescriptionToLocationObject)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            }).create();
                }
                if(!aimFoundDialog.isShowing()){
                    aimFoundDialog.show();
                }
            }
        });
    }
}
