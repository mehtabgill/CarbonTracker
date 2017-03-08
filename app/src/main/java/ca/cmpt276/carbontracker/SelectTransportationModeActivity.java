package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SelectTransportationModeActivity extends AppCompatActivity {
    Spinner selectCarSpinner;
    Button addCarButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> currentCarListDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);

        addCarButton = (Button) findViewById(R.id.add_car_button);
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTransportationModeActivity.this, AddCarActivity.class));
            }
        });

        selectCarSpinner = (Spinner)findViewById(R.id.select_car_spinner);
    }

    @Override
    protected void onResume(){
        super.onResume();
        currentCarListDescription = Model.getCarEntriesDescription(Model.RetriveEntries.Current);
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, currentCarListDescription
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selectCarSpinner.setAdapter(adapter);

    }
}
