package com.src.sim.metaioapplication.listener;

import android.view.View;

import com.src.sim.metaioapplication.data.MyDataBaseSQLite;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.Location;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

public interface CustomListener  {

    MyDataBaseSQLite getDatabase();
    View.OnClickListener handleCardLocationClick(final LocationOnly location);
}
