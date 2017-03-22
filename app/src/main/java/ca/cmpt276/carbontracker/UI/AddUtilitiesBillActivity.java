package ca.cmpt276.carbontracker.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private Button addBillButton;

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utilities_bill);
        selectBillTypeSpinner = (Spinner) findViewById(R.id.select_bill_type_spinner);
        billAmountEditText = (EditText) findViewById(R.id.bill_amount_editText);
        numberOfPeopleEditText = (EditText) findViewById(R.id.number_of_people_editText);
        startDateTextView = (TextView) findViewById(R.id.select_start_date_trigger);
        endDateTextView = (TextView) findViewById(R.id.select_end_date_trigger);
        addBillButton = (Button) findViewById(R.id.add_bill_button);
        ELECTRICITY = getResources().getString(R.string.electricity_bill);
        GAS = getResources().getString(R.string.gas_bill);
        BILL_ERROR = getResources().getString(R.string.bill_amount_error);
        NUMBER_OF_PEOPLE_ERROR = getResources().getString(R.string.number_of_people_error);
        billTypeArrayList.add(ELECTRICITY);
        billTypeArrayList.add(GAS);
        setUpSpinner();
        setupEditText();
        setupSelectDate();
        setupButton();
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
    private void setupButton() {
        addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Handle Error of start date set after end date
                if((selectedBillType == null) || (billAmount <= 0) || (numberOfPeople <= 0) ||
                        (startDate == null) || (endDate == null)){
                    Toast.makeText(AddUtilitiesBillActivity.this, getString(R.string.add_bill_button_error), Toast.LENGTH_SHORT).show();
                }
                else{
                    model.addNewUtilitiesBill(selectedBillType, billAmount, startDate, endDate, numberOfPeople);
                    finish();
                }

            }
        });

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
