package com.src.sim.metaioapplication.listener;

import android.view.View;

import com.src.sim.metaioapplication.data.MyDataBaseSQLite;
import com.src.sim.metaioapplication.logic.resource.Location;

public interface CustomListener  {

    MyDataBaseSQLite getDatabase();
    View.OnClickListener handleCardLocationClick(final Location location);
}
