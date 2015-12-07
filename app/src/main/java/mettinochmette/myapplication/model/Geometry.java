package mettinochmette.myapplication.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Mattias on 03/12/15.
 */
public class Geometry {

    private String type;
    private ArrayList<ArrayList<Float>> coordinates;
    private ArrayList<LatLng> latLngs;

    public String getType() {
        return type;
    }

    public ArrayList<ArrayList<Float>> getCoordinates() {
        return coordinates;
    }

    public ArrayList<LatLng> convertToLatLng() {
        if (latLngs == null) {
            latLngs = new ArrayList<>();
            for (ArrayList<Float> coordinateObj : coordinates) {
                latLngs.add(new LatLng(coordinateObj.get(1), coordinateObj.get(0)));
            }
        }
        return latLngs;
    }
}
