package com.src.sim.metaioapplication.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.metaio.sdk.MetaioDebug;
import com.metaio.tools.io.AssetsManager;

import java.io.IOException;

public class AssetsExtrater extends AsyncTask<Integer, Integer, Boolean> {

    private Activity activity;

    public void extractAllAssets(Activity activity){
        this.activity = activity;
        execute(0);
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        try{
            AssetsManager.extractAllAssets(activity.getApplicationContext(), true);
        }catch(IOException e){
            MetaioDebug.printStackTrace(Log.ERROR, e);
            return false;
        }

        return true;
    }
}