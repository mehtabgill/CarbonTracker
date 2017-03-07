package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectTransportationModeActivity extends AppCompatActivity {
    Spinner selectCarSpinner;
    Button addCarButton;

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

        //TODO: Populate it with actual current cars info
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Model.getInstance().getCarMakeList()
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selectCarSpinner = (Spinner)findViewById(R.id.select_car_spinner);
        selectCarSpinner.setAdapter(adapter);
    }
}
