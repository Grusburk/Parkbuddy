package mettinochmette.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;

import mettinochmette.myapplication.data.api.ApiManager;
import mettinochmette.myapplication.model.ParkingPlace;
import mettinochmette.myapplication.model.Place;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.InfoWindowAdapter, CityChooserFragment.OnCitySelectedListener, GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private LocationManager locationManager;
    private String provider;
    private SharedPreferences sharedPreferences;
    private SupportMapFragment mapFragment;
    private Boolean remember;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private HashMap<LatLng, Marker> markerMap;
    private HashMap<LatLng, Polyline> polyMap;
    private ArrayList<ParkingPlace> mParkingPlaces;
    private final String TAG = MapsActivity.class.getSimpleName();
    private final int KM = 50;
//    private ClusterManager<MyMarker> clusterManager;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView) findViewById(R.id.navList);
//        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name) + "</font>")));
        sharedPreferences = getSharedPreferences("PBuddy_Storage", Context.MODE_PRIVATE);
        remember = sharedPreferences.getBoolean("PBuddy_SavedPreferences", true);
//        http://openparking.stockholm.se/LTF-Tolken/v1/ptillaten/all?apiKey=0d49d540-8c75-4a01-b8c7-ad221c4708ba
//        if (remember) {
//            Log.i(TAG,"SAVED");
//            mapFragment = new SupportMapFragment();
//            getSupportFragmentManager()
//                    .beginTransaction().replace(R.id.map,mapFragment).commit();
//            mapFragment.getMapAsync(this);
//        } else {
        getSupportFragmentManager()
                .beginTransaction().add(R.id.map, new CityChooserFragment()).commit();
//        }
        addDrawerItems();
        setupDrawer();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addDrawerItems() {
        String[] drawerArray = {"test1", "test2", "test3", "test4", "test5"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        clusterManager = new ClusterManager<>(this, googleMap);
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnCameraChangeListener(this);
//        mMap.setOnCameraChangeListener(clusterManager);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Criteria criteria = new Criteria();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(provider, 500L, 1f, this);
        }
        ApiManager.getApi().getAvailableParkings().enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Response<Place> response, Retrofit retrofit) {
                mParkingPlaces = response.body().getParkingPlaces();
                markerMap = new HashMap<>(response.body().getTotalFeatures());
                polyMap = new HashMap<>(response.body().getTotalFeatures());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    // Will show or hide marker depending on distance.
    private void showMarkers() {
        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng marker;
        for (ParkingPlace place : mParkingPlaces) {
            marker = place.getGeometry().convertToLatLng().get(0);
            if (bounds.contains(marker) && markerMap.containsKey(marker) && !markerMap.get(marker).isVisible()) {
                markerMap.get(marker).setVisible(true);
            } else if (bounds.contains(marker) && !markerMap.containsKey(marker)) {
                markerMap.put(marker, mMap.addMarker(new MarkerOptions().position(marker)));
                polyMap.put(marker, mMap.addPolyline(new PolylineOptions().color(Color.BLACK).width(10).addAll(place.getGeometry().convertToLatLng())));
            } else if(!bounds.contains(marker) && markerMap.containsKey(marker)) {
                markerMap.get(marker).remove();
                markerMap.remove(marker);
                polyMap.get(marker).remove();
                polyMap.remove(marker);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myLocationAsLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocationAsLatLng, 25));
        if (mParkingPlaces != null) {
            showMarkers();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onCitySelected(int position) {
        mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View popUp = View.inflate(this, R.layout.popup, null);
        TextView street = (TextView) popUp.findViewById(R.id.street_text);
        TextView startTime = (TextView) popUp.findViewById(R.id.start_time_text);
        TextView endTime = (TextView) popUp.findViewById(R.id.end_time_text);
//        street.setText(String.format(getString(R.string.street_name_annotation), mMarkerMap.get(marker).getStreetName()));
//        startTime.setText(String.format(getString(R.string.start_time_name_annotation), mMarkerMap.get(marker).getStartTime()));
//        endTime.setText(String.format(getString(R.string.end_time_name_annotation), mMarkerMap.get(marker).getEndTime()));
        return popUp;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.i(TAG, "------------CAMERA CHANGED--------------- zoom: " + cameraPosition.zoom);
        // Show partially if zoomed in. Check for mMarkerMap since it can be null.
        // Could prob initialize hashMap earlier ot avoid.
        if (mParkingPlaces != null && mParkingPlaces.size() > 100) {
            showMarkers();
        }
//        else if (viableMarkerPositions != null && cameraPosition.zoom < 12) {
            // We really don't need to hide markers when zoomed out to far. Looks ridonculous when moving, so instead we show all.
            // TODO: Performance bad if doing this? mb lock zoom level itc go figure.
//            showAllMarkers();

//        }
//        }
    }
}