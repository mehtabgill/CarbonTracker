package ca.cmpt276.carbontracker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {
    private enum Spinner {Model, Year};
    android.widget.Spinner makeSpinner;
    android.widget.Spinner modelSpinner;
    android.widget.Spinner yearSpinner;
    Button searchButton;
    String makeHeader;
    String modelHeader;
    String yearHeader;
    String selectedMake;
    String selectedModel;
    String selectedYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        InputStream is = getResources().openRawResource(R.raw.make_list_data);
        makeSpinner = (android.widget.Spinner) findViewById(R.id.select_make_spinner);
        modelSpinner = (android.widget.Spinner) findViewById(R.id.select_model_spinner);
        yearSpinner = (android.widget.Spinner) findViewById(R.id.select_year_spinner);
        searchButton = (Button) findViewById(R.id.search_button);
        makeHeader = getString(R.string.make_spinner_header);
        modelHeader = getString(R.string.model_spinner_header);
        yearHeader = getString(R.string.year_spinner_header);
        ArrayList<String> carMakeList = Model.getCarMakeList();
        populateSpinner(makeSpinner, carMakeList);
        updateOnClickSpinner(Spinner.Model);
        updateOnClickSpinner(Spinner.Year);
        updateOnClickButton(searchButton);

    }

    private void updateOnClickButton(Button searchButton) {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMake = makeSpinner.getSelectedItem().toString();
                selectedModel = modelSpinner.getSelectedItem().toString();
                selectedYear = yearSpinner.getSelectedItem().toString();
                Model.updateCurrentSearchByYear(Integer.parseInt(selectedYear));

                //Create a popup with list of current car entries matched search entries
                //and ask the user to select one
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddCarActivity.this);
                builder1.setTitle(getString(R.string.select_car_popup_title));

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.select_dialog_singlechoice);
                for (String description : Model.getCarEntriesDescription(Model.RetriveEntries.Search)) {
                    arrayAdapter.add(description);
                }
                builder1.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String selection = arrayAdapter.getItem(which);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(AddCarActivity.this);
                        builder2.setMessage(getString(R.string.selected_entries_title, selection));
                        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Model.addNewCarBasedOnDecsription(selection);
                                Model.resetCurrentSearchCollection();
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder2.show();
                    }
                });

                builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Model.resetCurrentSearchCollection();
                        dialog.dismiss();
                    }
                });
                builder1.show();
            }
        });
    }

    /*
     * Update spinner for car models and car years option
     */
    private void updateOnClickSpinner(Spinner mode) {
        switch (mode){
            case Model:
                makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedMake = makeSpinner.getSelectedItem().toString();
                        Model.resetCurrentSearchCollection();
                        ArrayList<String> currentModelList = Model.getCarModelsOfMake(selectedMake);
                        populateSpinner(modelSpinner, currentModelList);
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
                        selectedModel = modelSpinner.getSelectedItem().toString();
                        ArrayList<String> currentYearList = Model.getCarYearsOfModels(selectedModel);
                        populateSpinner(yearSpinner, currentYearList);
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
