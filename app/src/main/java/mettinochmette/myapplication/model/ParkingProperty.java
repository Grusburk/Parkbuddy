package mettinochmette.myapplication.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mattias on 03/12/15.
 */
public class ParkingProperty {
    @SerializedName("FEATURE_OBJECT_ID")
    private int featureObjectId;
    @SerializedName("FEATURE_VERSION_ID")
    private int featureVersionId;
    @SerializedName("EXTENT_NO")
    private int extentNumber;
    @SerializedName("VALID_FROM")
    private Date validFrom;
    @SerializedName("START_TIME")
    private int startTime;
    @SerializedName("END_TIME")
    private int endTime;
    @SerializedName("START_WEEKDAY")
    private String startWeekDay;
    @SerializedName("START_MONTH")
    private int startMonth;
    @SerializedName("END_MONTH")
    private int endMonth;
    @SerializedName("START_DAY")
    private int startDay;
    @SerializedName("END_DAY")
    private int endDay;
    @SerializedName("CITATION")
    private String citation;
    @SerializedName("STREET_NAME")
    private String streetName;
    @SerializedName("CITY_DISTRICT")
    private String cityDistrict;
    @SerializedName("PARKING_DISTRICT")
    private String parkingDistrict;
    @SerializedName("ADDRESS")
    private String address;
    @SerializedName("bbox")
    private ArrayList<Float> mapBoxCoordinates;
    private ArrayList<LatLng> boxCoordinatesLatLng;
    private LatLng latLngs;

    public int getFeatureObjectId() {
        return featureObjectId;
    }

    public int getFeatureVersionId() {
        return featureVersionId;
    }

    public int getExtentNumber() {
        return extentNumber;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getStartWeekDay() {
        return startWeekDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public String getCitation() {
        return citation;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCityDistrict() {
        return cityDistrict;
    }

    public String getParkingDistrict() {
        return parkingDistrict;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ParkingProperty{" +
                "featureObjectId=" + featureObjectId +
                ", featureVersionId=" + featureVersionId +
                ", extentNumber=" + extentNumber +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startWeekDay='" + startWeekDay + '\'' +
                ", startMonth=" + startMonth +
                ", endMonth=" + endMonth +
                ", startDay=" + startDay +
                ", endDay=" + endDay +
                ", streetName='" + streetName + '\'' +
                ", cityDistrict='" + cityDistrict + '\'' +
                ", parkingDistrict='" + parkingDistrict + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}