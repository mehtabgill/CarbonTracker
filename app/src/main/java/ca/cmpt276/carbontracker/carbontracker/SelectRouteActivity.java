package ca.cmpt276.carbontracker.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import ca.cmpt276.carbontracker.carbontracker.R;

public class SelectRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        Spinner selectRouteSpinner = (Spinner) findViewById(R.id.select_route_spinner);
    }
}
