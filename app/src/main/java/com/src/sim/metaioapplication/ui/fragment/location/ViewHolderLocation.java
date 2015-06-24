package com.src.sim.metaioapplication.ui.fragment.location;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

public class ViewHolderLocation extends RecyclerView.ViewHolder {

    private TextView tvName, tvStreetAndNumber, tvZip, tvPlace;

    public ViewHolderLocation(View itemView) {
         super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.cardview_list_location_tvName);
        tvStreetAndNumber = (TextView) itemView.findViewById(R.id.cardview_list_location_tvStreetAndNumber);
        tvZip = (TextView) itemView.findViewById(R.id.cardview_list_location_tvZip);
        tvPlace = (TextView) itemView.findViewById(R.id.cardview_list_location_tvPlace);
    }

    public void assignData(final LocationOnly location){
        tvName.setText(location.getName());
        tvStreetAndNumber.setText(location.getStreet() + " " + location.getNumber());
        tvZip.setText(location.getZip());
        tvPlace.setText(location.getPlace());
    }
}
