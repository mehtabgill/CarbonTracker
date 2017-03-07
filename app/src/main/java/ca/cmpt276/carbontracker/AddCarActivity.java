package ca.cmpt276.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {
    private enum Spinner {Model, Year};
    android.widget.Spinner makeSpinner;
    android.widget.Spinner modelSpinner;
    android.widget.Spinner yearSpinner;
    String selectedMake;
    CarCollection testCollection = new CarCollection();
    CarCollection currentCollection = new CarCollection();
    //carCollection needs to be moved to Singleton model when the model is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        InputStream is = getResources().openRawResource(R.raw.make_list_data);
        ArrayList<String> carMakeList = DataReader.getCarMakeList(is);

        testCollection = Model.getInstance().getTotalCarList();

        makeSpinner = (android.widget.Spinner) findViewById(R.id.select_make_spinner);
        modelSpinner = (android.widget.Spinner) findViewById(R.id.select_model_spinner);
        yearSpinner = (android.widget.Spinner) findViewById(R.id.select_year_spinner);
        populateSpinner(makeSpinner, carMakeList);
        updateSpinner(Spinner.Model);
        updateSpinner(Spinner.Year);
    }

    /*
     * Update spinner for car models and car years option
     */
    private void updateSpinner(Spinner mode) {
        switch (mode){
            case Model:
                makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedMake = makeSpinner.getSelectedItem().toString();
                        modelSpinner.setVisibility(View.INVISIBLE);
                        if (!selectedMake.equals(getResources().getString(R.string.Make_header))) {
                            modelSpinner.setVisibility(View.VISIBLE);
                            currentCollection = testCollection.findCarsWithMake(selectedMake);
                            currentCollection.searchUniqueModelName();
                            ArrayList<String> currentModelList = currentCollection.getUniqueModelNames();
                            populateSpinner(modelSpinner, currentModelList);
                        }
                        else{
                            modelSpinner.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case Year:
                modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        yearSpinner.setVisibility(View.INVISIBLE);
                        if (modelSpinner.getVisibility() == View.VISIBLE){

                            String selectedModel = modelSpinner.getSelectedItem().toString();
                            yearSpinner.setVisibility(View.VISIBLE);

                            CarCollection selectedModelCollection = currentCollection.findCarsWithModel(selectedModel);
                            ArrayList<String> currentYearList = new ArrayList<String>();

                            //add the first year in the current Models list
                            currentYearList.add(Integer.toString(selectedModelCollection.getCar(0).getYear()));

                            for (Car car: selectedModelCollection){
                                boolean yearIsUnique = true;
                                String modelYear = Integer.toString(car.getYear());
                                for(String uniqueYear : currentYearList){
                                    if (modelYear.equals(uniqueYear)){
                                        yearIsUnique = false;
                                    }
                                }
                               if(yearIsUnique){
                                    currentYearList.add(modelYear);
                               }
                            }
                            populateSpinner(yearSpinner, currentYearList);
                        }
                        else{
                            if(modelSpinner.getVisibility() == View.INVISIBLE){
                                yearSpinner.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
        }
    }
    
    private void populateSpinner(android.widget.Spinner spinner, List<String> stringArrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, stringArrayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }


}
