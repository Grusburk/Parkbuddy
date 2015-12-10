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
 * Created by Mattin on 2015-12-09.
 */
public class SettingsFragment extends Fragment {

    private final String TAG = CityChooserFragment.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String CarCheckboxStatus = "checkboxCarIsChecked;";
    public static final String DisabledCheckboxStatus = "checkboxDisabledIsChecked;";
    public static final String TruckCheckboxStatus = "checkboxTruckIsChecked;";
    public static final String MCCheckboxStatus = "checkboxMcIsChecked;";
    public static final String PaymentCheckboxStatus = "checkboxPaymentIsChecked;";
    public static final String NoPaymentCheckboxStatus = "checkboxNoPaymentIsChecked;";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button buttonForget = (Button)view.findViewById(R.id.button_forget);
        final CheckBox checkboxCar = (CheckBox) view.findViewById(R.id.car_checkbox);
        final CheckBox checkboxDisabled = (CheckBox) view.findViewById(R.id.rorelse_checkbox);
        final CheckBox checkboxTruck = (CheckBox) view.findViewById(R.id.lastbil_checkbox);
        final CheckBox checkboxMc = (CheckBox) view.findViewById(R.id.mc_checkbox);
        final CheckBox checkBoxPayment = (CheckBox) view.findViewById(R.id.payment_checkbox);
        final CheckBox checkBoxNoPayment = (CheckBox) view.findViewById(R.id.nopayment_checkbox);
        sharedPreferences = SettingsFragment.this.getActivity().getSharedPreferences("PBuddy_Storage", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkboxCar.setChecked(sharedPreferences.getBoolean(SettingsFragment.CarCheckboxStatus, true));
        checkboxDisabled.setChecked(sharedPreferences.getBoolean(SettingsFragment.DisabledCheckboxStatus, false));
        checkboxMc.setChecked(sharedPreferences.getBoolean(SettingsFragment.MCCheckboxStatus, false));
        checkboxTruck.setChecked(sharedPreferences.getBoolean(SettingsFragment.TruckCheckboxStatus, false));
        checkBoxPayment.setChecked(sharedPreferences.getBoolean(SettingsFragment.PaymentCheckboxStatus, true));
        checkBoxNoPayment.setChecked(sharedPreferences.getBoolean(SettingsFragment.NoPaymentCheckboxStatus, true));

        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        checkboxCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(CarCheckboxStatus, isChecked);
                editor.apply();
            }
        });

        checkboxDisabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(DisabledCheckboxStatus, isChecked);
                editor.apply();
            }
        });
        checkboxTruck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(TruckCheckboxStatus, isChecked);
                editor.apply();
            }
        });
        checkboxMc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(MCCheckboxStatus, isChecked);
                editor.apply();
            }
        });
        checkBoxPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(PaymentCheckboxStatus, isChecked);
                editor.apply();
            }
        });
        checkBoxNoPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(NoPaymentCheckboxStatus, isChecked);
                editor.apply();
            }
        });

    }
}
