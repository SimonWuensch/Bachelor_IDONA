package com.src.sim.metaioapplication.ui.fragment.object;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.Location;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

public class ListObjectFragment extends Fragment {
    private static final String LOCATIONJSON = "locationJson";
    private static final String LOCATIONID = "locationid";

    private CustomListener customListener;
    private LocationOnly location;

    public static ListObjectFragment newInstance(LocationOnly location) {
        ListObjectFragment fragment = new ListObjectFragment();
        Bundle args = new Bundle();
        args.putString(LOCATIONJSON, location.toJson());
        args.putLong(LOCATIONID, location.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
    }

    private void loadArguments() {
        if (getArguments() != null) {
            location  = LocationOnly.JsonToLocationOnly(getArguments().getString(LOCATIONJSON));
            location.setId(getArguments().getLong(LOCATIONID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_object, container, false);
        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpandable);
        expListView.setAdapter(new AdapterObject(getActivity(), location));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        customListener = (CustomListener)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
