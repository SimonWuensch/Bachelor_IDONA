package com.src.sim.metaioapplication.ui.activitiy.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.src.sim.metaioapplication.asynctask.AssetsExtrater;
import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.data.MyDataBaseSQLite;
import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;
import com.src.sim.metaioapplication.metaio.ARConfiguration;
import com.src.sim.metaioapplication.ui.fragment.location.ListLocationFragment;

import java.util.Map;

public class MainActivity extends Activity implements CustomListener{

    public static String LISTLOCATIONFRAGMENT = "fragmentlocationlist";

    private Intent arItent;
    private MyDataBaseSQLite dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new AssetsExtrater().extractAllAssets(this);
        dataBase = new MyDataBaseSQLite(this);
        //new NetworkCommunikation(this).execute();

        getFragmentManager().beginTransaction().add(R.id.activity_main_flFragmentContainer,  new ListLocationFragment(), LISTLOCATIONFRAGMENT).addToBackStack(LISTLOCATIONFRAGMENT).commit();

       // showFragment(new ListLocationFragment(), LISTLOCATIONFRAGMENT);

        //arItent = new Intent(getApplicationContext(), ARConfiguration.class);
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
       final Activity activity = this;
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, location.getName() + " ", Toast.LENGTH_LONG).show();
            }
        };
    }
}
