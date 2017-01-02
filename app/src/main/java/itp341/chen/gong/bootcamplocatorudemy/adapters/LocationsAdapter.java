package itp341.chen.gong.bootcamplocatorudemy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import itp341.chen.gong.bootcamplocatorudemy.R;
import itp341.chen.gong.bootcamplocatorudemy.holders.LocationsViewHolder;
import itp341.chen.gong.bootcamplocatorudemy.model.LocationMarker;

/**
 * Created by gongchen on 12/6/16.
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsViewHolder> {
    private ArrayList<LocationMarker> mLocationMarkerArrayList;

    public LocationsAdapter(ArrayList<LocationMarker> locationMarkerArrayList) {
        this.mLocationMarkerArrayList = locationMarkerArrayList;
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        final LocationMarker location = mLocationMarkerArrayList.get(position);
        holder.updateUI(location);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implement onClick to show details
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLocationMarkerArrayList.size();
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.card_location,parent,false);
        return new LocationsViewHolder(card);
    }
}
