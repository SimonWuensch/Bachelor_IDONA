package com.src.sim.metaioapplication.ui.fragment.object;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 18.06.2015.
 */
public class AdapterObject extends BaseExpandableListAdapter {

    private Activity activity;
    private LocationOnly location;
    private History history;
    private CustomListener customListener;
    private List<Kind> parentList;

    private Map<LocationObject.Kind, List<LocationObject>> childrenMap;

    public AdapterObject(Activity activity, LocationOnly location) {
        this.activity = activity;
        this.location = location;
        this.customListener = (CustomListener)activity;
        this.history = customListener.getDatabase().getHistory(location);

        this.childrenMap = history.getlObjectMap();
        this.parentList = new ArrayList<Kind>(childrenMap.keySet());
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childrenMap.get(this.parentList.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final LocationObject child = (LocationObject)getChild(groupPosition, childPosition);
        View view = ViewHolderChildren.assignData(activity, child, convertView);
        view.setOnClickListener(customListener.handleLocationObjectClick(history, child));
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childrenMap.get(this.parentList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Kind headerTitle = (Kind)getGroup(groupPosition);
        return ViewHolderParent.assignData(activity, headerTitle, convertView);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}