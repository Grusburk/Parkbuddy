package mettinochmette.myapplication;


import android.content.Context;
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
        final CheckBox checkboxRorelse = (CheckBox) view.findViewById(R.id.rorelse_checkbox);
        final CheckBox checkBoxLastbil = (CheckBox) view.findViewById(R.id.lastbil_checkbox);
        final CheckBox checkboxMc = (CheckBox) view.findViewById(R.id.mc_checkbox);
        final CheckBox checkBoxPayment = (CheckBox) view.findViewById(R.id.payment_checkbox);

        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"forget");
            }
        });

        checkboxCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Checked");
                } else {
                    Log.i(TAG, "NotChecked");
                }
            }
        });
        checkboxRorelse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Checked");
                } else {
                    Log.i(TAG, "NotChecked");
                }
            }
        });
        checkBoxLastbil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Checked");
                } else {
                    Log.i(TAG, "NotChecked");
                }
            }
        });
        checkboxMc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Checked");
                } else {
                    Log.i(TAG, "NotChecked");
                }
            }
        });
        checkBoxPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

}
