package com.src.sim.metaioapplication.ui.fragment.object;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;
import com.src.sim.metaioapplication.metaio.ARConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterObject extends BaseExpandableListAdapter {

    private ARConfiguration activity;
    private LocationOnly location;
    private List<Kind> parentList;
    private ListObjectFragment listObjectFragment;

    private Map<LocationObject.Kind, List<LocationObject>> childrenMap;

    public AdapterObject(Activity activity, ListObjectFragment listObjectFragment, LocationOnly location, History history) {
        this.activity = (ARConfiguration)activity;
        this.location = location;
        this.childrenMap = history.getlObjectMap();
        this.listObjectFragment = listObjectFragment;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.handleLocationObjectClick(child);
                listObjectFragment.updateTextViewObjects(child);
            }
        });
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