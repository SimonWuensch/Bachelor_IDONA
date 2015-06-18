package com.src.sim.metaioapplication.ui.activitiy.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.data.MyDataBaseSQLite;
import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;
import com.src.sim.metaioapplication.metaio.ARConfiguration;
import com.src.sim.metaioapplication.ui.fragment.location.ListLocationFragment;
import com.src.sim.metaioapplication.ui.fragment.object.ListObjectFragment;

public class MainActivity extends Activity implements CustomListener{

    public static String LOCATIONFRAGMENT = "fragmentlocationlist";
    public static String OBJECTFRAGMENT = "fragmentobject";

    public static String HISTORYEXTRA = "historyextra";
    public static String LOCATIONOBJECTEXTRA = "locationobjectextra";

    private Intent arItent;
    private MyDataBaseSQLite dataBase;

    private Aim aim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new AssetsExtrater().extractAllAssets(this);
        dataBase = new MyDataBaseSQLite(this);
        showFragment(new ListLocationFragment(), LOCATIONFRAGMENT);
    }

    public void showFragment(Fragment fragment, String backstack){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_flFragmentContainer, fragment)
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                showFragment(ListObjectFragment.newInstance(location), OBJECTFRAGMENT);
            }
        };
    }

    @Override
    public View.OnClickListener handleLocationObjectClick(final History history, final LocationObject locationObject){
        final Activity activity = this;
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, locationObject.getDescription() + " ", Toast.LENGTH_LONG).show();
                aim = new Aim();
                aim.setlObject(locationObject);

                Intent intent = new Intent(activity, ARConfiguration.class);
                intent.putExtra(HISTORYEXTRA, history.toJson());
                intent.putExtra(LOCATIONOBJECTEXTRA, locationObject.toJson());
                startActivity(intent);
            }
        };
    }

    @Override
    public void startNavigation(int trackerId){
        if(!aim.hasTracker()){
        }
    }
}
