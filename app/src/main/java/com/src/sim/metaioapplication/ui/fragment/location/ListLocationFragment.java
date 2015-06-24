package com.src.sim.metaioapplication.ui.fragment.location;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.src.sim.metaioapplication.R;

public class ListLocationFragment extends Fragment {

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list_location, container, false);

            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fList_Location_rcRecylcerView);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

            RecyclerView.Adapter mAdapter = new AdapterLocation(R.layout.cardview_list_location, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }
}
