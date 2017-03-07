package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SelectTransportationModeActivity extends AppCompatActivity {
    Spinner makeSpinner;
    String selectedMake;
    //This will get reference from Singleton model;
    CarCollection currentCarCollection;
    Spinner selectCarSpinner;
    Button addCarButton;

    Model model = Model.getInstance();
    //carCollection needs to be moved to Singleton model when the model is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);

        ArrayList<String> carMakeList = model.getCarMakeList();

        makeSpinner = (Spinner) findViewById(R.id.select_make_spinner);
        populateSpinner(makeSpinner, carMakeList);
        updateModelSpinner();
        selectCarSpinner = (Spinner)findViewById(R.id.select_car_spinner);
        addCarButton = (Button) findViewById(R.id.add_car_button);
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTransportationModeActivity.this, AddCarActivity.class));
            }
        });
    }

    /*
     * Update spinner for car models based on which make is selected
     */
    private void updateModelSpinner() {
        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedMake = makeSpinner.getSelectedItem().toString();
                Spinner modelSpinner = (Spinner) findViewById(R.id.select_model_spinner);
                CarCollection currentCollection = model.getCarList();

                if (!selectedMake.equals("make")){
                    CarCollection currentCollectionByMake = currentCollection.findCarsWithMake(selectedMake);
                    currentCollectionByMake.searchUniqueModelName();
                    ArrayList<String> currentModelList = currentCollectionByMake.getUniqueModelName();
                    populateSpinner(modelSpinner, currentModelList);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSpinner(Spinner spinner, List<String> stringArrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, stringArrayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }


}
