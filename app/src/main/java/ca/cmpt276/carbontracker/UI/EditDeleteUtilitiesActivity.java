package ca.cmpt276.carbontracker.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.cmpt276.carbontracker.Model.SingletonModel;
import ca.cmpt276.carbontracker.Model.Utilities;

public class EditDeleteUtilitiesActivity extends AppCompatActivity {
    private SingletonModel model = SingletonModel.getInstance();

    private RelativeLayout selectBillLayout;
    private ListView selectBillListView;
    private RelativeLayout editBillLayout;
    private int selectedBillIndex;
    private Utilities selectedBill;

    private Menu menu;
    private Spinner selectBillTypeSpinner;
    private EditText billAmountEditText;
    private EditText numberOfPeopleEditText;
    private static TextView startDateTextView;
    private static TextView endDateTextView;
    private String START_DATE_KEY = "startDatePicker";
    private String END_DATE_KEY = "endDatePicker";

    private String editedUtilitiesType;
    private String ELECTRICITY;
    private String GAS;
    private String BILL_ERROR;
    private String NUMBER_OF_PEOPLE_ERROR;
    private static AddUtilitiesBillActivity.CURRENT_PICKER current_picker;
    private ArrayList<String> billTypeArrayList = new ArrayList<>();
    private Button saveEditedBillInfoButton;
    private Utilities.BILL editedBillType;
    private float editedBillAmount;
    private static Calendar editedStartDate;
    private static Calendar editedEndDate;
    private int editedNumberOfPeople;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<String> utilitiesBillList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_plus, menu);
        this.menu = menu;
        menu.findItem(R.id.edit_delete_item).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.plus_item){
            startActivity(new Intent(EditDeleteUtilitiesActivity.this, AddUtilitiesBillActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_utilities_bill);
        selectBillLayout = (RelativeLayout) findViewById(R.id.selectBillLayout);
        selectBillListView = (ListView) findViewById(R.id.select_bill_listView);

        editBillLayout = (RelativeLayout) findViewById(R.id.editBillLayout);
        editBillLayout.setVisibility(View.INVISIBLE);
        selectBillTypeSpinner = (Spinner) findViewById(R.id.select_bill_type_spinner);
        billAmountEditText = (EditText) findViewById(R.id.bill_amount_editText);
        numberOfPeopleEditText = (EditText) findViewById(R.id.number_of_people_editText);
        startDateTextView = (TextView) findViewById(R.id.select_start_date_trigger);
        endDateTextView = (TextView) findViewById(R.id.select_end_date_trigger);
        saveEditedBillInfoButton = (Button) findViewById(R.id.save_bill_edited_button);
        setupUtilitiesBillListView();
    }

    @Override
    public void onResume(){
        super.onResume();
        setupUtilitiesBillListView();
    }

    @Override
    public void onBackPressed(){
        if(editBillLayout.getVisibility() == View.VISIBLE){
            selectBillLayout.setVisibility(View.VISIBLE);
            editBillLayout.setVisibility(View.INVISIBLE);
        }
        else{
            super.onBackPressed();
        }
    }


    private void setupUtilitiesBillListView() {
        editBillLayout.setVisibility(View.INVISIBLE);
        utilitiesBillList = model.getUtilitiesDescriptionList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (EditDeleteUtilitiesActivity.this, android.R.layout.simple_spinner_item, utilitiesBillList);
        selectBillListView.setAdapter(adapter);
        selectBillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //not sure if correct
                selectedBillIndex = position;
                selectedBill = model.getUtilities(selectedBillIndex);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDeleteUtilitiesActivity.this);
                builder.setTitle(getString(R.string.select_utilities_bill_popup_title));
                final ArrayAdapter<String> arrayAdapter
                        = new ArrayAdapter<String>(EditDeleteUtilitiesActivity.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add(getString(R.string.edit));
                arrayAdapter.add(getString(R.string.delete));
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = arrayAdapter.getItem(which);
                        if(selection.equals(getString(R.string.edit))){
                            setupEditBillLayout();
                            editBillLayout.setVisibility(View.VISIBLE);
                            selectBillLayout.setVisibility(View.INVISIBLE);

                        }
                        else{
                            model.removeUtilities(selectedBillIndex);
                            Toast.makeText(EditDeleteUtilitiesActivity.this,
                                    getString(R.string.deleted_bill_confirmed), Toast.LENGTH_SHORT).show();
                            setupUtilitiesBillListView();
                        }
                        dialog.dismiss();
                    }
                });
                if(utilitiesBillList.isEmpty()){
                    Toast.makeText(EditDeleteUtilitiesActivity.this, getString(R.string.select_bill_error), Toast.LENGTH_SHORT).show();
                }
                else{
                    builder.show();
                }
            }
        });
    }

    private void setupEditBillLayout() {
        selectBillTypeSpinner = (Spinner) findViewById(R.id.select_bill_type_spinner);
        billAmountEditText = (EditText) findViewById(R.id.bill_amount_editText);
        numberOfPeopleEditText = (EditText) findViewById(R.id.number_of_people_editText);
        startDateTextView = (TextView) findViewById(R.id.select_start_date_trigger);
        endDateTextView = (TextView) findViewById(R.id.select_end_date_trigger);
        saveEditedBillInfoButton = (Button) findViewById(R.id.save_bill_edited_button);

        if(selectedBill.getBill().equals(Utilities.BILL.ELECTRICITY)){
            selectBillTypeSpinner.setSelection(0);
            editedBillAmount = selectedBill.getElectricityAmount();
            billAmountEditText.setText(Float.toString(editedBillAmount));
        }
        else{
            selectBillTypeSpinner.setSelection(1);
            editedBillAmount = selectedBill.getGasAmount();
            billAmountEditText.setText(Float.toString(editedBillAmount));
        }
        editedNumberOfPeople = selectedBill.getNumberOfPeople();
        numberOfPeopleEditText.setText(Integer.toString(editedNumberOfPeople));

        editedStartDate = selectedBill.getStartDate();
        startDateTextView.setText(sdf.format(editedStartDate.getTime()));
        editedEndDate = selectedBill.getEndDate();
        endDateTextView.setText(sdf.format(editedEndDate.getTime()));

        ELECTRICITY = getResources().getString(R.string.electricity_bill);
        GAS = getResources().getString(R.string.gas_bill);
        BILL_ERROR = getResources().getString(R.string.bill_amount_error);
        NUMBER_OF_PEOPLE_ERROR = getResources().getString(R.string.number_of_people_error);
        billTypeArrayList.add(ELECTRICITY);
        billTypeArrayList.add(GAS);

        setUpEditedBillTypeSpinner();
        setupEditText();
        setupSelectDate();
        setupSaveEditedButton();
    }

    private void setUpEditedBillTypeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                EditDeleteUtilitiesActivity.this, android.R.layout.simple_spinner_item, billTypeArrayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selectBillTypeSpinner.setAdapter(adapter);
        selectBillTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editedUtilitiesType = selectBillTypeSpinner.getSelectedItem().toString();
                if(editedUtilitiesType.equals(ELECTRICITY)){
                    editedBillType = Utilities.BILL.ELECTRICITY;
                }
                else{
                    editedBillType = Utilities.BILL.GAS;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupEditText() {
        billAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveEditedBillInfoButton.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    editedBillAmount = Float.parseFloat(s.toString());
                    if(editedBillAmount <= 0){
                        throw new IllegalArgumentException();
                    }
                    saveEditedBillInfoButton.setEnabled(true);
                }
                catch (IllegalArgumentException e){
                    Toast.makeText(EditDeleteUtilitiesActivity.this, BILL_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOfPeopleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveEditedBillInfoButton.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    editedNumberOfPeople = Integer.parseInt(s.toString());
                    if(editedNumberOfPeople <= 0){
                        throw new IllegalArgumentException();
                    }
                    saveEditedBillInfoButton.setEnabled(true);
                }
                catch (IllegalArgumentException e){
                    Toast.makeText(EditDeleteUtilitiesActivity.this, NUMBER_OF_PEOPLE_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupSelectDate() {

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EditDeleteUtilitiesActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), START_DATE_KEY);
                current_picker = AddUtilitiesBillActivity.CURRENT_PICKER.START_DATE;
            }
        });
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EditDeleteUtilitiesActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), END_DATE_KEY);
                current_picker = AddUtilitiesBillActivity.CURRENT_PICKER.END_DATE;
            }
        });
    }

    private void setupSaveEditedButton() {
        saveEditedBillInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editedBillType == null) || (editedBillAmount <= 0) || (editedNumberOfPeople <= 0) ||
                        (editedStartDate == null) || (editedEndDate == null)) {
                    Toast.makeText(EditDeleteUtilitiesActivity.this, getString(R.string.add_bill_button_error), Toast.LENGTH_SHORT).show();
                } else {
                    if(dateOrderCorrect()){
                        Utilities editedUtilities = new Utilities(editedBillType, editedBillAmount, editedStartDate, editedEndDate, editedNumberOfPeople);
                        model.editUtilities(selectedBillIndex, editedUtilities);
                        editBillLayout.setVisibility(View.INVISIBLE);
                        selectBillLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(EditDeleteUtilitiesActivity.this, getString(R.string.date_order_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean dateOrderCorrect() {
        boolean order = false;
        int yearStart = editedStartDate.get(Calendar.YEAR);
        int yearEnd = editedEndDate.get(Calendar.YEAR);
        if(yearStart == yearEnd){
            int start = editedStartDate.get(Calendar.DAY_OF_YEAR);
            int end = editedEndDate.get(Calendar.DAY_OF_YEAR);
            if(start < end){
                order = true;
            }
        }
        else{
            if(yearStart < yearEnd){
                order = true;
            }
        }
        return order;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            switch (current_picker){
                case START_DATE:
                    editedStartDate = Calendar.getInstance();
                    editedStartDate.set(year, month, day);
                    startDateTextView.setText(sdf.format(editedStartDate.getTime()));
                    break;
                case END_DATE:
                    editedEndDate = Calendar.getInstance();
                    editedEndDate.set(year, month, day);
                    endDateTextView.setText(sdf.format(editedEndDate.getTime()));
                    break;
            }
        }
    }




}
