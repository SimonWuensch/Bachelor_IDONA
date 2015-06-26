package com.src.sim.metaioapplication.ui.activitiy.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.asynctask.AssetsExtrater;
import com.src.sim.metaioapplication.asynctask.NetworkCommunikation;
import com.src.sim.metaioapplication.data.MyDataBaseSQLite;
import com.src.sim.metaioapplication.dialog.DialogManager;
import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;
import com.src.sim.metaioapplication.metaio.ARConfiguration;
import com.src.sim.metaioapplication.ui.fragment.location.ListLocationFragment;

public class MainActivity extends Activity implements CustomListener{

    public static String LOCATIONFRAGMENT = "fragmentlocationlist";
    public static String HISTORYEXTRA = "historyExtra";
    public static String LOCATIONONLYEXTRA = "locationOnlyExtra";
    public static String LOCATIONONLYIDEXTRA = "locationOnlyID";

    private MyDataBaseSQLite dataBase;
    private DialogManager dialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new AssetsExtrater().extractAllAssets(this);
        dataBase = new MyDataBaseSQLite(this);
        dialogManager = new DialogManager(MainActivity.this);
        //NetworkCommunikation.getLocation(this);
        showFragment(new ListLocationFragment(), LOCATIONFRAGMENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showFragment(Fragment fragment, String backstack){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_flFragmentContainer, fragment, backstack)
                .addToBackStack(backstack)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        dialogManager.toggleLoading(false);
    }

    @Override
    public MyDataBaseSQLite getDatabase(){
        return dataBase;
    }

    @Override
    public View.OnClickListener handleCardLocationClick(final LocationOnly location){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                dialogManager.toggleLoading(true);

                Intent arIntent = new Intent(MainActivity.this, ARConfiguration.class);
                arIntent.putExtra(HISTORYEXTRA, dataBase.getHistory(location).toJson());
                arIntent.putExtra(LOCATIONONLYEXTRA, location.toJson());
                arIntent.putExtra(LOCATIONONLYIDEXTRA, Long.toString(location.getId()));
                startActivity(arIntent);
            }
        };
    }
}
