package ca.cmpt276.carbontracker.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.Car;
import ca.cmpt276.carbontracker.Model.CarStorage;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display edit/delete car activity, including edit nickname, car make/model/year
 */
public class EditDeleteCarActivity extends AppCompatActivity {

    private SingletonModel model = SingletonModel.getInstance();
    private CarStorage carStorage = CarStorage.getInstance();
    private enum ButtonClicked{Delete, Save_Edited};
    private EditText nicknameEditText;
    private Spinner editMakeSpinner;
    private Spinner editModelSpinner;
    private Spinner editYearSpinner;
    private Button deleteCarButton;
    private Button saveEditedButton;
    private Menu menu;
    private String editedNickname;
    private String editedMake;
    private String editedModel;
    private String editedYear;
    private int selectedIndex;
    private String DELETED_MESSAGE;
    private String SAVE_EDITED_MESSAGE;
    private Car car;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit_delete_save, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.save_item){
            carStorage.updateCurrentSearchByYear(Integer.parseInt(editedYear));
            android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(EditDeleteCarActivity.this);
            builder1.setTitle(getString(R.string.select_car_popup_title));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditDeleteCarActivity.this, android.R.layout.select_dialog_singlechoice);
            for(String description : carStorage.getCarSearchList()){
                arrayAdapter.add(description);
            }
            builder1.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String selection = arrayAdapter.getItem(which);
                    android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(EditDeleteCarActivity.this);
                    builder2.setMessage(getString(R.string.confirm_saving_data_message, editedNickname + " " + selection));
                    builder2.setPositiveButton(getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = arrayAdapter.getPosition(selection);
                            Car editedCar = carStorage.getCarFromSearchList(index);
                            editedCar.setNickname(editedNickname);
                            model.editCar(selectedIndex, editedCar);
                            carStorage.resetCurrentSearchCollection();
                            Toast.makeText(EditDeleteCarActivity.this, SAVE_EDITED_MESSAGE, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    builder2.show();
                }
            });
            builder1.show();
        }
        else if(id == R.id.delete_item){
            model.removeCar(selectedIndex);
            Toast.makeText(EditDeleteCarActivity.this,
                    DELETED_MESSAGE, Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_car);
        receiveDescription();
        car = model.getCar(selectedIndex);
        nicknameEditText = (EditText) findViewById(R.id.edit_nickname_editText);
        editMakeSpinner = (Spinner) findViewById(R.id.edit_make_spinner);
        editModelSpinner = (Spinner) findViewById(R.id.edit_model_spinner);
        editYearSpinner = (Spinner) findViewById(R.id.edit_year_spinner);
        deleteCarButton = (Button) findViewById(R.id.delete_car_button);
        saveEditedButton = (Button) findViewById(R.id.save_edited_button);
        DELETED_MESSAGE = getString(R.string.deleted_car_message);
        SAVE_EDITED_MESSAGE = getString(R.string.saved_data_message);

        ArrayList<String> carMakeList = carStorage.getCarMakeList();
        AddCarActivity.populateSpinner(EditDeleteCarActivity.this, editMakeSpinner, carMakeList);
        updateOnClickSpinner(AddCarActivity.Spinner.Model);
        updateOnClickSpinner(AddCarActivity.Spinner.Year);
        updateOnClickEditText();
    }

    private void receiveDescription(){
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            selectedIndex = extra.getInt(CarSelectionActivity.DESCRIPTION_KEY);
        }
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
                    editedNickname = nicknameEditText.getText().toString();
                    if(editedNickname.length() == 0){
                        throw new IllegalArgumentException();
                    }
                    else{
                        saveEditedButton.setEnabled(true);

                    }
                } catch(IllegalArgumentException e){
                    Toast.makeText(EditDeleteCarActivity.this,
                            AddCarActivity.NICKNAME_INPUT_ERROR,
                            Toast.LENGTH_SHORT).show();
                    saveEditedButton.setEnabled(false);
                }
            }
        });
    }

    private void setOnClick(ButtonClicked button) {
        switch (button){
            case Delete:
                deleteCarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.removeCar(selectedIndex);
                        Toast.makeText(EditDeleteCarActivity.this,
                                DELETED_MESSAGE, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            break;
            case Save_Edited:
                saveEditedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carStorage.updateCurrentSearchByYear(Integer.parseInt(editedYear));
                        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(EditDeleteCarActivity.this);
                        builder1.setTitle(getString(R.string.select_car_popup_title));
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditDeleteCarActivity.this, android.R.layout.select_dialog_singlechoice);
                        for(String description : carStorage.getCarSearchList()){
                            arrayAdapter.add(description);
                        }
                        builder1.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String selection = arrayAdapter.getItem(which);
                                android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(EditDeleteCarActivity.this);
                                builder2.setMessage(getString(R.string.confirm_saving_data_message, editedNickname + " " + selection));
                                builder2.setPositiveButton(getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int index = arrayAdapter.getPosition(selection);
                                        Car editedCar = carStorage.getCarFromSearchList(index);
                                        editedCar.setNickname(editedNickname);
                                        model.editCar(selectedIndex, editedCar);
                                        carStorage.resetCurrentSearchCollection();
                                        Toast.makeText(EditDeleteCarActivity.this, SAVE_EDITED_MESSAGE, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                                builder2.show();
                            }
                        });
                        builder1.show();
                    };
                });
                break;
        }
    }

    private void updateOnClickSpinner(AddCarActivity.Spinner mode) {
        switch (mode){
            case Model:
                editMakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        editedMake = editMakeSpinner.getSelectedItem().toString();
                        carStorage.resetCurrentSearchCollection();
                        ArrayList<String> currentModelList = carStorage.getCarModelsOfMake(editedMake);
                        AddCarActivity.populateSpinner(EditDeleteCarActivity.this, editModelSpinner, currentModelList);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case Year:
                editModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  {
                        editedModel = editModelSpinner.getSelectedItem().toString();
                        ArrayList<String> currentYearList = carStorage.getCarYearsOfModels(editedModel);
                        AddCarActivity.populateSpinner(EditDeleteCarActivity.this, editYearSpinner, currentYearList);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                editYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        editedYear = editYearSpinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
        }
    }
}
