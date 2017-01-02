package itp341.chen.gong.bootcamplocatorudemy;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import itp341.chen.gong.bootcamplocatorudemy.Fragments.MainFragment;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener,
GoogleApiClient.ConnectionCallbacks, LocationListener{

    private GoogleApiClient mGoogleApiClient;

    private MainFragment mainFragment;

    private final int PERMISSION_LOCATION = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.container_main);
        if (mainFragment == null){
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container_main,mainFragment)
                    .commit();
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("Locator App", "onLocationChanged Called" );
        mainFragment.setUserMarker(new LatLng(location.getLatitude(),location.getLongitude()));
        Log.v("Locator App", "Long: " + location.getLongitude() + " - Lat: " + location.getLatitude());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION);
            Log.v("Locator App", "Request Location");
        }
        else {
            startLocationServices();
        }


    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){//What's this? From server?
                    startLocationServices();
                    Log.v("Locator App", "Permission granted - starting service");
                }
                else {
                    Log.v("Locator App", "Permission denied - no service");
                    Toast.makeText(getApplicationContext(), "Permission denied.", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    }
    public void startLocationServices(){
        Log.v("Locator App", "startLocationServices called");
        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, req, this);
            Log.v("Locator App", "Request Location update");
            /*Grab the last location and call onLocationChanged manually.
            Otherwise, onLocationChanged does not get called promptly. */
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);//The closest location
            if (mLastLocation != null) {
                Log.v("Locator App", "mLastLocation != null ");
                onLocationChanged(mLastLocation);
            }
        } catch (SecurityException exception){
            Toast.makeText(getApplicationContext(), "Please permit to continue", Toast.LENGTH_LONG)
                    .show();
        }
    }
}