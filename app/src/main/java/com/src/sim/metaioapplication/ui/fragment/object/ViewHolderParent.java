package com.src.sim.metaioapplication.ui.fragment.object;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;

public class ViewHolderParent {

    public static View assignData(Activity activity, Kind header, View convertView){

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.viewgroup_list_object, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(header.name());
        return convertView;
    }
}
