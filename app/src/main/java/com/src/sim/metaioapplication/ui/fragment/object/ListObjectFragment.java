package com.src.sim.metaioapplication.ui.fragment.object;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.src.sim.metaioapplication.R;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

public class ListObjectFragment extends Fragment {
    private static final String LOCATIONJSON = "locationJson";
    private static final String LOCATIONID = "locationid";

    private TextView tvLocationName, tvLocationStreetAndNumber, tvLocationZip, tvLocationPlace;

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

        loadFragmentElemente(rootView);

        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.fList_Object_ExpandableListView);
        expListView.setAdapter(new AdapterObject(getActivity(), location));
        return rootView;
    }

    private void loadFragmentElemente(View view){
        tvLocationName = (TextView) view.findViewById(R.id.fList_Object_tvName);
        tvLocationStreetAndNumber = (TextView) view.findViewById(R.id.fList_Object_tvStreetAndNumber);
        tvLocationZip = (TextView) view.findViewById(R.id.fList_Object_tvZip);
        tvLocationPlace = (TextView) view.findViewById(R.id.fList_Object_tvPlace);

        tvLocationName.setText(location.getName());
        tvLocationStreetAndNumber.setText(location.getStreet() + " " + location.getNumber());
        tvLocationZip.setText(location.getZip());
        tvLocationPlace.setText(location.getPlace());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
