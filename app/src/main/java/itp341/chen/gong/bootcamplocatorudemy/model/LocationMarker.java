package itp341.chen.gong.bootcamplocatorudemy.model;

import com.orm.SugarRecord;

/**
 * Created by gongchen on 12/1/16.
 */
public class LocationMarker extends SugarRecord {
    private float longitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;
    private String locationZip;
    private String locationImageUrl;

    public LocationMarker(){

    }
    public LocationMarker(float latitude, float longitude, String locationTitle, String locationAddress, String locationZipInput, String locationImageUrl) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationZip = locationZipInput;
        this.locationImageUrl = locationImageUrl;
    }

    public String getLocationImageUrl() {
        return  locationImageUrl;
    }

    public float getLongtitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationZip() {
        return locationZip;
    }
}
