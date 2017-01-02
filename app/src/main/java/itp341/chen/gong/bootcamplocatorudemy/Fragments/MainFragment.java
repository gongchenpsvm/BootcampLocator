package itp341.chen.gong.bootcamplocatorudemy.Fragments;


import android.content.Context;
import android.hardware.input.InputManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import itp341.chen.gong.bootcamplocatorudemy.R;
import itp341.chen.gong.bootcamplocatorudemy.model.LocationMarker;
import itp341.chen.gong.bootcamplocatorudemy.service.DataService;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MarkerOptions userMarker;

    private LocationsListFragment mListFragment;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Create Recycle View
        mListFragment = (LocationsListFragment)getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.container_locations_list);
        if (mListFragment == null){
            mListFragment = LocationsListFragment.newInstance();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_locations_list, mListFragment)
                    .commit();
        }

        final EditText zipText = (EditText)view.findViewById(R.id.zip_text);
        zipText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER){
                    String zipString = zipText.getText().toString();


                    //Hide soft keyboard after zip code entered
                    InputMethodManager imm
                            = (InputMethodManager)getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(zipText.getWindowToken(), 0);
                    //Respond to zip code entered
                        //Convert zip to Latlng and then move camera to this Latlng
                    final Geocoder geocoder = new Geocoder(getActivity());//get what? Activity or context or this?
                    try{
                        Address addressFromZip;
                        //Get 1 address related to the string for the camera to move to
                        List<Address> addresses = geocoder.getFromLocationName(zipString,1);
                        if (addresses != null && !addresses.isEmpty()){
                            addressFromZip = addresses.get(0);
                            LatLng latlngFromZip
                                    = new LatLng
                                    (addressFromZip.getLatitude(),addressFromZip.getLongitude());
                            //Move camera to zip code entered
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFromZip,15));
                        }
                    } catch (IOException e){
                    }
                    showList();
                    updateMapForZip(zipString);
                    return true;
                }
                return false;
            }
        });
        hideList();
        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void setUserMarker (LatLng latLng){
        //Set current user marker if not set yet and update markers around current location
        if (userMarker == null){
            //Log.v("Locator App", "Lat: " + latLng.latitude + "Long: " + latLng.longitude);
            userMarker = new MarkerOptions().position(latLng).title("Current Location");
            mMap.addMarker(userMarker);

        }
        else{
            Log.v("Locator App", "userMarker not null");
        }
        //Move camera to current location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        //Update markers on map with zip code
        try {
            //Convert last Latlng to zip code used to select markers near last location
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
            String lastLocationZip = addresses.get(0).getPostalCode();
            Log.v("lastLocationZip", lastLocationZip);
            updateMapForZip(lastLocationZip);
        }catch (IOException exception){
        }
    }

    private void updateMapForZip(String zipcode){//In face zip code not used to update markers on map
        //Select markers of one zip code
        List<LocationMarker> locations = DataService.getInstance().getMoocerLocationsWithin10MilesOfZip(zipcode);
        //Iterate the list and update markers on map
        for (LocationMarker lm: locations){
            MarkerOptions marker = new MarkerOptions().position(new LatLng(lm.getLatitude(), lm.getLongtitude()));
            marker.title(lm.getLocationTitle());
            marker.snippet(lm.getLocationAddress() + lm.getLocationZip() );
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mMap.addMarker(marker);
        }
    }

    private void hideList(){
        getActivity().getSupportFragmentManager().beginTransaction().hide(mListFragment).commit();
    }

    private void showList(){
        getActivity().getSupportFragmentManager().beginTransaction().show(mListFragment).commit();
    }

}
