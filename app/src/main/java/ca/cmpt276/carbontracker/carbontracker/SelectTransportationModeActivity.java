package ca.cmpt276.carbontracker.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class SelectTransportationModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);
        Spinner selectCarSpinner = (Spinner) findViewById(R.id.select_car_spinner);
    }
}
