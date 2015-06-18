package com.src.sim.metaioapplication.ui.fragment.object;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.LocationObject;

public class ViewHolderChildren {

    public static View assignData(Activity activity, LocationObject child, View convertView){
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.viewitem_list_object, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(child.getDescription());
        return convertView;
    }
}
