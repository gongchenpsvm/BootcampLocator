package itp341.chen.gong.bootcamplocatorudemy.Fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import itp341.chen.gong.bootcamplocatorudemy.R;
import itp341.chen.gong.bootcamplocatorudemy.adapters.LocationsAdapter;
import itp341.chen.gong.bootcamplocatorudemy.model.LocationMarker;
import itp341.chen.gong.bootcamplocatorudemy.service.DataService;

/**
 * Use the {@link LocationsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsListFragment extends android.support.v4.app.Fragment {
    public LocationsListFragment() {
        // Required empty public constructor
    }

    public static LocationsListFragment newInstance() {
        LocationsListFragment fragment = new LocationsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_locations);
        //R.id.recycler_locations included by R.layout.fragment_locations_list
        recyclerView.setHasFixedSize(true);

        //Cast from List to ArrayList for adapter
        //TODO Correct to cast?
        ArrayList<LocationMarker> lmArrayList;
        lmArrayList =
                castFromListToArrayList
                        (DataService.getInstance()
                                .getMoocerLocationsWithin10MilesOfZip("90007"));
        LocationsAdapter adapter = new LocationsAdapter(lmArrayList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private ArrayList<LocationMarker> castFromListToArrayList (List<LocationMarker> lmList){
        ArrayList<LocationMarker> lmArrayList = new ArrayList<LocationMarker>();
        for (LocationMarker iter : lmList){
            lmArrayList.add(iter);
        }
        return lmArrayList;
    }

}


