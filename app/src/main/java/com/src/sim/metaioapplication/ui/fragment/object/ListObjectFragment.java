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
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

public class ListObjectFragment extends Fragment {
    private static final String HISTORYJSON = "historyJson";
    private static final String LOCATIONJSON = "locationJson";
    private static final String LOCATIONID = "locationid";

    private TextView tvLocationName, tvLocationStreetAndNumber, tvLocationZip, tvObjects;
    private ExpandableListView expListView;

    private LocationOnly location;
    private History history;

    public static ListObjectFragment newInstance(LocationOnly location, History history) {
        ListObjectFragment fragment = new ListObjectFragment();
        Bundle args = new Bundle();
        args.putString(LOCATIONJSON, location.toJson());
        args.putLong(LOCATIONID, location.getId());
        args.putString(HISTORYJSON, history.toJson());
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
            location = LocationOnly.JsonToLocationOnly(getArguments().getString(LOCATIONJSON));
            location.setId(getArguments().getLong(LOCATIONID));
            history = History.JsonToHistory(getArguments().getString(HISTORYJSON));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_object, container, false);

        tvLocationName = (TextView) rootView.findViewById(R.id.fList_Object_tvName);
        tvLocationStreetAndNumber = (TextView) rootView.findViewById(R.id.fList_Object_tvStreetAndNumber);
        tvLocationZip = (TextView) rootView.findViewById(R.id.fList_Object_tvZip);
        tvObjects = (TextView) rootView.findViewById(R.id.fList_Object_tvObjects);

        tvLocationName.setText(location.getName());
        tvLocationStreetAndNumber.setText(location.getStreet() + " " + location.getNumber());
        tvLocationZip.setText(location.getZip() + " " + location.getPlace());

        expListView = (ExpandableListView) rootView.findViewById(R.id.fList_Object_ExpandableListView);
        expListView.setAdapter(new AdapterObject(getActivity(), this, location, history));

        tvObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    toggleListVisible();
            }
        });

        return rootView;
    }

    private void toggleListVisible(){
        if(expListView.getVisibility() == View.GONE) {
            expListView.setVisibility(View.VISIBLE);
        }else{
            expListView.setVisibility(View.GONE);
        }
    }

    protected void updateTextViewObjects(LocationObject locationObject){
        toggleListVisible();
        tvObjects.setText(locationObject.toString());
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
