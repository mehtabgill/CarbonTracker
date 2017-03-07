package ca.cmpt276.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {
    Spinner makeSpinner;
    String selectedMake;
    //carCollection needs to be moved to Singleton model when the model is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        InputStream is = getResources().openRawResource(R.raw.make_list_data);
        DataReader.readCarMakeData(is);
        ArrayList<String> carMakeList = DataReader.getCarMakeList();

        InputStream is1 = getResources().openRawResource(R.raw.data);
        DataReader.readCarData(is1);


        makeSpinner = (Spinner) findViewById(R.id.select_make_spinner);
        populateSpinner(makeSpinner, carMakeList);
        updateModelSpinner();
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
                CarCollection currentCollection = DataReader.getCarList();

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
