package mettinochmette.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mattias on 03/12/15.
 */
public class Place {

    private String type;
    private int totalFeatures;
    @SerializedName("features")
    private ArrayList<ParkingPlace> parkingPlaces;

    public String getType() {
        return type;
    }

    public int getTotalFeatures() {
        return totalFeatures;
    }

    public ArrayList<ParkingPlace> getParkingPlaces() {
        return parkingPlaces;
    }

    @Override
    public String toString() {
        return "Place{" +
                "type='" + type + '\'' +
                ", totalFeatures=" + totalFeatures +
                '}';
    }
}
