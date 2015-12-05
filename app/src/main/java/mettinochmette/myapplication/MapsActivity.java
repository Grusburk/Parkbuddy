package mettinochmette.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import mettinochmette.myapplication.data.api.ApiManager;
import mettinochmette.myapplication.model.Geometry;
import mettinochmette.myapplication.model.ParkingPlace;
import mettinochmette.myapplication.model.ParkingProperty;
import mettinochmette.myapplication.model.Place;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.InfoWindowAdapter,CityChooserFragment.OnCitySelectedListener {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private LocationManager locationManager;
    private String provider;
    private Marker marker;
    private SharedPreferences sharedPreferences;
    private SupportMapFragment mapFragment;
    private Boolean remember;
    private final String TAG = MapsActivity.class.getSimpleName();
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ArrayList<LatLng> locations;
    private ArrayList<String> mStreetNames;
    private HashMap<Marker, ParkingProperty> mMarkerMap;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView)findViewById(R.id.navList);
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
                    .beginTransaction().add(R.id.map,new CityChooserFragment()).commit();
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
        String[] drawerArray = { "test1", "test2", "test3", "test4", "test5" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerArray);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Criteria criteria = new Criteria();
            Log.i(TAG,"dfdf");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(provider, 500L, 1f, this);

        }
        ApiManager.getApi().getServiceTimeByDay("weekday", "måndag").enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Response<Place> response, Retrofit retrofit) {
                locations = new ArrayList<>();
                final ArrayList<ParkingPlace> mParkingPlaces = response.body().getParkingPlaces();
                mStreetNames = new ArrayList<>();
                mMarkerMap = new HashMap<>();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (ParkingPlace parkPlace : mParkingPlaces) {
                            mStreetNames.add(parkPlace.getProperties().getStreetName());
                            Geometry geotag = parkPlace.getGeometry();

                            for (ArrayList<Float> cordinates : geotag.getCoordinates()) {
                                locations.add(new LatLng(cordinates.get(1), cordinates.get(0)));
                            }
                        }
//                        for (ParkingPlace parkProperties : mParkingPlaces){
//                            ParkingProperty mParkingproperties = parkProperties.getProperties();
//                            for (ArrayList<String> mStreetNames : mParkingproperties.getStreetName(){
//                                mStreetNames.add(mParkingproperties.getStreetName());
//                            }
//                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Marker marker;
                                for (int i = 0; i < mStreetNames.size(); i++) {
                                    marker = mMap.addMarker(new MarkerOptions()
                                                    .position(locations.get(i))
                                                    .title(mStreetNames.get(i)));
                                    mMarkerMap.put(marker, mParkingPlaces.get(i).getProperties());

                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,25));
        Log.i(TAG, myLocation.latitude + "");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCitySelected(int position) {
        mapFragment = new SupportMapFragment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map,mapFragment).commit();
        mapFragment.getMapAsync(this);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        Log.i(TAG, "getInfoContents");
        View popUp = View.inflate(this, R.layout.popup, null);
        TextView street = (TextView) popUp.findViewById(R.id.street_text);
        TextView startTime = (TextView) popUp.findViewById(R.id.start_time_text);
        TextView endTime = (TextView) popUp.findViewById(R.id.end_time_text);
        street.setText(String.format(getString(R.string.street_name_annotation), marker.getTitle()));
        startTime.setText(String.format(getString(R.string.start_time_name_annotation), mMarkerMap.get(marker).getStartTime()));
        endTime.setText(String.format(getString(R.string.end_time_name_annotation), mMarkerMap.get(marker).getEndTime()));
        return popUp;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
