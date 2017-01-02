package itp341.chen.gong.bootcamplocatorudemy.holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import itp341.chen.gong.bootcamplocatorudemy.R;
import itp341.chen.gong.bootcamplocatorudemy.model.LocationMarker;

/**
 * Created by gongchen on 12/6/16.
 */
public class LocationsViewHolder extends RecyclerView.ViewHolder {


    private ImageView locationImage;
    private TextView locationTitle;
    private TextView locationAddress;

    public LocationsViewHolder(View itemView) {
        super(itemView);

        locationImage = (ImageView)itemView.findViewById(R.id.location_img);
        locationTitle = (TextView)itemView.findViewById(R.id.location_title);
        locationAddress = (TextView)itemView.findViewById(R.id.location_address);
    }

    public void updateUI(LocationMarker location){
        /*Discarded since failed to set images to imageView
        int resource = locationImage.getResources().getIdentifier(uri, null, locationImage.getContext().getPackageName());
        locationImage.setImageResource(resource);
        */
        //String uri = location.getLocationImageUrl();
        //TODO Unable to fetch specified image resources with the url String. Hardcoded at present
        int imgResource = locationImage.getResources()
                .getIdentifier("nhmla","drawable", locationImage.getContext().getPackageName());
        locationImage.setImageResource(imgResource);
                    Log.v("Test imgResource",Integer.toString(imgResource));

        locationTitle.setText(location.getLocationTitle());
        String add = location.getLocationAddress() + location.getLocationZip();
        locationAddress.setText(add);

    }
}
