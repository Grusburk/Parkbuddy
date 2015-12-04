package mettinochmette.myapplication.model;

import java.util.ArrayList;

/**
 * Created by Mattias on 03/12/15.
 */
public class Geometry {

    private String type;
    private ArrayList<ArrayList<Float>> coordinates;

    public String getType() {
        return type;
    }

    public ArrayList<ArrayList<Float>> getCoordinates() {
        return coordinates;
    }
}
