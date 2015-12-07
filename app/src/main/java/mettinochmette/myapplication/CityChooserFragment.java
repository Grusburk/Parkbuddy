package mettinochmette.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityChooserFragment extends Fragment {

    private OnCitySelectedListener onCitySelectedListener;
    private final String TAG = CityChooserFragment.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public CityChooserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_chooser,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button buttonStockholm = (Button)view.findViewById(R.id.button_stockholm);
        Button buttonGoteborg = (Button)view.findViewById(R.id.button_goteborg);
        Button buttonMalmo = (Button)view.findViewById(R.id.button_malmo);
        final CheckBox checkboxRemember = (CheckBox) view.findViewById(R.id.remember_checkbox);

//        ApiManager.getApi().getServiceTimeByDay("weekday", "måndag").flatMap(place1 -> Observable.from(place1.getParkingPlaces()))
//                .map(ParkingPlace::getProperties)
//                .filter(parkingProperty -> parkingProperty.getParkingDistrict().equalsIgnoreCase("Södermalm"))
//                .take(10)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                    property -> Log.i(TAG, "Place is:  " + property.toString())
//                    ,throwable -> Log.i(TAG, throwable.getMessage()));



//        ApiManager.getApi().getServiceTimeByDay("area", "Södermalm").flatMap(place1 -> Observable.from(place1.getParkingPlaces())).map(ParkingPlace::getProperties).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                property -> Log.i(TAG, "Place is:  " + property.toString())
//                ,throwable -> Log.i(TAG, throwable.getMessage()));

        buttonStockholm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCitySelectedListener.onCitySelected(2);
                if (checkboxRemember.isChecked()) {
                    sharedPreferences = CityChooserFragment.this.getActivity().getSharedPreferences("PBuddy_Storage", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("PBuddy_SavedPreferences", checkboxRemember.isChecked());
                    editor.commit();
                }
            }
        });

        buttonGoteborg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "snackbar");
                Snackbar.make(getView(),"Denna stad är ej implementerad än.",Snackbar.LENGTH_LONG).show();
            }
        });

        buttonMalmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"snackbar");
                Snackbar.make(getView(),"Denna stad är ej implementerad än.",Snackbar.LENGTH_LONG).show();
            }
        });


        checkboxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Checked");
                } else {
                    Log.i(TAG, "NotChecked");
                }
            }
        });


    }
    public interface OnCitySelectedListener{
        public void onCitySelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onCitySelectedListener = (OnCitySelectedListener) context;

        } catch (ClassCastException e){
            throw new ClassCastException("");
        }
    }
}
