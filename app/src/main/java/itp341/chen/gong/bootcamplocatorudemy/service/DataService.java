package itp341.chen.gong.bootcamplocatorudemy.service;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import itp341.chen.gong.bootcamplocatorudemy.model.LocationMarker;

/**
 * Created by gongchen on 12/1/16.
 */
public class DataService {
    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {
    }
    //Imitate
    //TODO Check whether the lm already in database. If yes, do NOT save.
    public List<LocationMarker> getMoocerLocationsWithin10MilesOfZip(String zipcode){
        LocationMarker lm0 = new LocationMarker(34.025f, -118.282f,
                "Troy Hall",
                "3025 Royal St, Los Angeles, CA ",
                "90089",
                "troyhall");
        lm0.save();
        LocationMarker lm1 = new LocationMarker(34.016f,  -118.282f,
                "Tuscany Apartments",
                "3770 S Figueroa St, Los Angeles, CA ",
                "90089",
                "tuscanyapartment");
        lm1.save();
        LocationMarker lm2 = new LocationMarker(34.016f, -118.288f,
                "Natural History Museum of Los Angeles County",
                "900 Exposition Blvd, Los Angeles, CA ",
                "90089",
                "nhmla");
        lm2.save();
        LocationMarker lm3 = new LocationMarker(34.023f, -118.280f,
                "University Gateway Apartments",
                "3335 S Figueroa St, Los Angeles, CA ",
                "90089",
                "gatewayusc");
        lm3.save();
        LocationMarker lm4 = new LocationMarker(34.036f,  -118.424f,
                "Westwood/Rancho Park Station",
                "10816 Exposition Blvd, Los Angeles, CA ",
                "90064",
                "wrps");
        lm4.save();
        List<LocationMarker> list =
                Select.from(LocationMarker.class)
                        .where(Condition.prop("location_Zip").eq(zipcode))
                        //TODO  Seems not to select zip and offer specified markers to adapter
                        //Sugar ORM cannot identify string?
                        .list();
        return list;

    }
}


//Simulate we are downloading data from server. Discarded due to sugar ORM
        /*
        ArrayList<LocationMarker> list = new ArrayList<>();
        list.add(new LocationMarker(34.025f, -118.282f,
                "Troy Hall", "3025 Royal St, Los Angeles, CA ", "90007", "slo"));
        list.add(new LocationMarker(34.016f,  -118.282f,
                "Tuscany Apartments", "3770 S Figueroa St, Los Angeles, CA ", "90007", "slo"));
        list.add(new LocationMarker(34.016f, -118.288f,
                "Natural History Museum of Los Angeles County", "900 Exposition Blvd, Los Angeles, CA ", "90007", "slo"));
        list.add(new LocationMarker(34.023f, -118.280f,
                "University Gateway Apartments", "3335 S Figueroa St, Los Angeles, CA ", "90007","slo"));
        return list;*/