package ca.cmpt276.carbontracker.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.Model;
import ca.cmpt276.carbontracker.UI.R;

public class AddCarActivity extends AppCompatActivity {
    public enum Spinner {Model, Year};
    android.widget.Spinner makeSpinner;
    android.widget.Spinner modelSpinner;
    android.widget.Spinner yearSpinner;
    private EditText nicknameEditText;
    private String nicknameInput;
    public static String NICKNAME_INPUT_ERROR;
    private String selectedMake;
    private String selectedModel;
    private String selectedYear;
    private Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        makeSpinner = (android.widget.Spinner) findViewById(R.id.select_make_spinner);
        modelSpinner = (android.widget.Spinner) findViewById(R.id.select_model_spinner);
        yearSpinner = (android.widget.Spinner) findViewById(R.id.select_year_spinner);
        searchButton = (Button) findViewById(R.id.search_button);
        nicknameEditText = (EditText) findViewById(R.id.enter_nickname_editText);
        NICKNAME_INPUT_ERROR = getString(R.string.nickname_input_error);
        ArrayList<String> carMakeList = Model.getCarMakeList();
        populateSpinner(AddCarActivity.this, makeSpinner, carMakeList);
        updateOnClickSpinner(Spinner.Model);
        updateOnClickSpinner(Spinner.Year);
        updateOnClickButton();
        updateOnClickEditText();
    }

    private void updateOnClickEditText() {
        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    nicknameInput = nicknameEditText.getText().toString();
                    if(nicknameInput.length() == 0){
                        throw new IllegalArgumentException();
                    }
                    else{
                        searchButton.setEnabled(true);

                    }
                } catch(IllegalArgumentException e){
                    Toast.makeText(AddCarActivity.this,
                                    NICKNAME_INPUT_ERROR,
                                    Toast.LENGTH_SHORT).show();
                    searchButton.setEnabled(false);
                }
            }
        });
    }

    private void updateOnClickButton() {
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
                        builder2.setPositiveButton(getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Model.isCurrentCarAdded(selection)){
                                    Toast.makeText(AddCarActivity.this,
                                            getString(R.string.car_existed_message), Toast.LENGTH_SHORT).show();
                                    Model.resetCurrentSearchCollection();
                                    dialog.dismiss();
                                }

                                else{
                                    Model.addNewCarBasedOnDecsription(nicknameInput, selection);
                                    dialog.dismiss();
                                    Model.resetCurrentSearchCollection();
                                    finish();

                                }
                            }
                        });
                        builder2.show();
                    }
                });

                builder1.setNegativeButton(getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
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
                        populateSpinner(AddCarActivity.this, modelSpinner, currentModelList);
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
                        populateSpinner(AddCarActivity.this, yearSpinner, currentYearList);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
        }
    }
    
    public static void populateSpinner(Context context, android.widget.Spinner spinner, ArrayList<String> stringArrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, stringArrayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }


}
