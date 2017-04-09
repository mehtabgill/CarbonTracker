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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.cmpt276.carbontracker.Model.SingletonModel;
import ca.cmpt276.carbontracker.Model.Utilities;

/*
 *
 */
public class AddUtilitiesBillActivity extends AppCompatActivity {
    private String ELECTRICITY;
    private String GAS;
    private String BILL_ERROR;
    private String NUMBER_OF_PEOPLE_ERROR;

    private SingletonModel model = SingletonModel.getInstance();
    private ArrayList<String> billTypeArrayList = new ArrayList<>();
    private Spinner selectBillTypeSpinner;
    private EditText billAmountEditText;
    private EditText numberOfPeopleEditText;
    private static TextView startDateTextView;
    private static TextView endDateTextView;
    private String START_DATE_KEY = "startDatePicker";
    private String END_DATE_KEY = "endDatePicker";
    private Menu menu;

    private String selectedBillTypeString;
    public enum CURRENT_PICKER{START_DATE, END_DATE};
    private static CURRENT_PICKER current_picker;
    private Utilities.BILL selectedBillType;
    private Float billAmount;
    private int numberOfPeople;
    private static Calendar startDate;
    private static Calendar endDate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit_delete_save, menu);
        this.menu = menu;
        menu.findItem(R.id.delete_item).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.save_item){
            if((selectedBillType == null) || (billAmount == null) || (numberOfPeople <= 0) ||
                    (startDate == null) || (endDate == null)){
                Toast.makeText(AddUtilitiesBillActivity.this, getString(R.string.add_bill_button_error), Toast.LENGTH_SHORT).show();
            }
            else{
                if(dateOrderCorrect()){
                    Utilities utilities = new Utilities(selectedBillType, billAmount, startDate, endDate, numberOfPeople);
                    model.addNewUtilities(utilities);
                    finish();
                }
                else{
                    Toast.makeText(AddUtilitiesBillActivity.this, getString(R.string.date_order_error), Toast.LENGTH_SHORT).show();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utilities_bill);
        model.openDB(this);
        model.loadDataFromDB(getApplicationContext());

        selectBillTypeSpinner = (Spinner) findViewById(R.id.select_bill_type_spinner);
        billAmountEditText = (EditText) findViewById(R.id.bill_amount_editText);
        numberOfPeopleEditText = (EditText) findViewById(R.id.number_of_people_editText);
        startDateTextView = (TextView) findViewById(R.id.select_start_date_trigger);
        endDateTextView = (TextView) findViewById(R.id.select_end_date_trigger);
        ELECTRICITY = getResources().getString(R.string.electricity_bill);
        GAS = getResources().getString(R.string.gas_bill);
        BILL_ERROR = getResources().getString(R.string.bill_amount_error);
        NUMBER_OF_PEOPLE_ERROR = getResources().getString(R.string.number_of_people_error);
        billTypeArrayList.add(ELECTRICITY);
        billTypeArrayList.add(GAS);
        setUpSpinner();
        setupEditText();
        setupSelectDate();
    }

    private void setUpSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddUtilitiesBillActivity.this, android.R.layout.simple_spinner_item, billTypeArrayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selectBillTypeSpinner.setAdapter(adapter);
        selectBillTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBillTypeString = selectBillTypeSpinner.getSelectedItem().toString();
                if(selectedBillTypeString.equals(ELECTRICITY)){
                    selectedBillType = Utilities.BILL.ELECTRICITY;
                }
                else{
                    selectedBillType = Utilities.BILL.GAS;
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
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    billAmount = Float.parseFloat(s.toString());
                    if(billAmount <= 0){
                        throw new IllegalArgumentException();
                    }
                }
                catch (IllegalArgumentException e){
                    Toast.makeText(AddUtilitiesBillActivity.this, BILL_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOfPeopleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    numberOfPeople = Integer.parseInt(s.toString());
                    if(numberOfPeople <= 0){
                        throw new IllegalArgumentException();
                    }
                }
                catch (IllegalArgumentException e){
                    Toast.makeText(AddUtilitiesBillActivity.this, NUMBER_OF_PEOPLE_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupSelectDate() {

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), START_DATE_KEY);
                current_picker = CURRENT_PICKER.START_DATE;
            }
        });
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), END_DATE_KEY);
                current_picker = CURRENT_PICKER.END_DATE;
            }
        });
    }

    private boolean dateOrderCorrect() {
        boolean order = false;
        int yearStart = startDate.get(Calendar.YEAR);
        int yearEnd = endDate.get(Calendar.YEAR);
        if(yearStart == yearEnd){
            int start = startDate.get(Calendar.DAY_OF_YEAR);
            int end = endDate.get(Calendar.DAY_OF_YEAR);
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


    //source code: https://developer.android.com/guide/topics/ui/controls/pickers.html#DatePicker
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
                    startDate = Calendar.getInstance();
                    startDate.set(year, month, day);
                    startDateTextView.setText(sdf.format(startDate.getTime()));
                    break;
                case END_DATE:
                    endDate = Calendar.getInstance();
                    endDate.set(year, month, day);
                    endDateTextView.setText(sdf.format(endDate.getTime()));
                    break;
            }

        }
    }

}
