package mettinochmette.myapplication.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mattias on 03/12/15.
 */
public class ParkingPlace {

    private String type;
    private String id;
    private Geometry geometry;
    @SerializedName("geometry_name")
    private String geometryName;
    private ParkingProperty properties;
    private LatLng location;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getGeometryName() {
        return geometryName;
    }

    public ParkingProperty getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "ParkingPlace{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", geometryName='" + geometryName + '\'' +
                '}';
    }
}
