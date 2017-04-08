package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import ca.cmpt276.carbontracker.Model.ActivityConstants;
import java.util.Calendar;

import ca.cmpt276.carbontracker.Model.DataReader;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display main menu, including create new journey and view current carbon emission
 */
public class MainMenuActivity extends AppCompatActivity {

    private enum BUTTONS{CREATE_JOURNEY, CREATE_UTILITY_BILL, VIEW_CARBON_FOOTPRINT, EDIT_DELETE_UTILTITY, VIEW_JOURNEY, SETTINGS};
    private static int selected_year;
    private static int selected_month;
    private static int selected_date;
    public static String selected_mode;

    public static final String SELECTED_DATE_KEY = "SELECTED_DAY";
    public static final String SELECTED_MONTH_KEY = "SELECTED_MONTH";
    public static final String SELECTED_YEAR_KEY = "SELECTED_YEAR";
    public static final String DAY_MODE_KEY = "DAY";
    public static final String MODE_KEY = "MODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button createJourneyButton = (Button) findViewById(R.id.createJourneyButton);
        Button viewCarbonFootprintButton = (Button) findViewById(R.id.viewCarbonFootprintButton);
        Button createUtilityBillButton = (Button) findViewById(R.id.create_utility_bill_button);
        Button editDeleteUtilityBillButton = (Button) findViewById(R.id.edit_delete_utility_buitton);
        Button btnViewJourney = (Button) findViewById(R.id.btn_View_Journey);
        Button btnSettings = (Button) findViewById(R.id.btn_settings);
        createJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.CREATE_JOURNEY);
            }
        });

        createUtilityBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.CREATE_JOURNEY.CREATE_UTILITY_BILL);
            }
        });

        viewCarbonFootprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_CARBON_FOOTPRINT);
            }
        });
        editDeleteUtilityBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.EDIT_DELETE_UTILTITY);
            }
        });
        btnViewJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_JOURNEY);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.SETTINGS);
            }
        });
        if(SingletonModel.getInstance().inTestingMode()){
            Button btnDeleteAll = (Button) findViewById(R.id.btn_delete_all);
            btnDeleteAll.setVisibility(View.VISIBLE);
            btnDeleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingletonModel.getInstance().deleteAllDataFromDB();
                    Toast.makeText(MainMenuActivity.this, "All data deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clickMainMenuButton(BUTTONS function) {
        switch (function){
            //Change this later
            case CREATE_JOURNEY:
                if(DataReader.isLoaded()){
                    startActivity(new Intent(MainMenuActivity.this, SelectTransportationMode.class));
                }
                else{
                    Toast.makeText(MainMenuActivity.this, "Still loading data", Toast.LENGTH_SHORT).show();
                }
                break;
            case CREATE_UTILITY_BILL:
                startActivity(new Intent(MainMenuActivity.this, AddUtilitiesBillActivity.class));
                break;
            case VIEW_CARBON_FOOTPRINT:
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainMenuActivity.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add(getString(R.string.mode_1_day));
                arrayAdapter.add(getString(R.string.mode_28_day));
                arrayAdapter.add(getString(R.string.mode_365_day));
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                builder.setTitle(getString(R.string.select_time_range_popup));
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected_mode = arrayAdapter.getItem(which);
                        if(selected_mode.equals(getString(R.string.mode_1_day))){
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog datePickerDialog = new DatePickerDialog(MainMenuActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int month, int day) {
                                            selected_year = year;
                                            selected_month = month;
                                            selected_date = day;
                                            Intent intent = new Intent(MainMenuActivity.this, ViewCarbonFootprintActivity.class);
                                            selected_mode = getString(R.string.mode_1_day);
                                            intent.putExtra(MODE_KEY, selected_mode);
                                            intent.putExtra(SELECTED_DATE_KEY, selected_date);
                                            intent.putExtra(SELECTED_MONTH_KEY, selected_month);
                                            intent.putExtra(SELECTED_YEAR_KEY, selected_year);
                                            startActivity(intent);
                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();
                        }
                        else{
                            Calendar currentDate = Calendar.getInstance();
                            selected_date = currentDate.get(Calendar.DATE);
                            selected_month = currentDate.get(Calendar.MONTH);
                            selected_year = currentDate.get(Calendar.YEAR);
                            Intent intent = new Intent(MainMenuActivity.this, ViewCarbonFootprintActivity.class);
                            intent.putExtra(MODE_KEY, selected_mode);
                            intent.putExtra(SELECTED_DATE_KEY, selected_date);
                            intent.putExtra(SELECTED_MONTH_KEY, selected_month);
                            intent.putExtra(SELECTED_YEAR_KEY, selected_year);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
                break;
            case EDIT_DELETE_UTILTITY:
                startActivity(new Intent(MainMenuActivity.this, EditDeleteUtilitiesActivity.class));
                break;
            case VIEW_JOURNEY:
                startActivity(new Intent(MainMenuActivity.this, ViewJourneyActivity.class));
                break;
            case SETTINGS:
                startActivity(new Intent(MainMenuActivity.this,OptionsActivity.class));
                break;
        }
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
            selected_year = year;
            selected_month = month;
            selected_date = day;
            Activity activity = getActivity();
            Intent intent = new Intent(activity, ViewCarbonFootprintActivity.class);
            intent.putExtra(MODE_KEY, selected_mode);
            intent.putExtra(SELECTED_DATE_KEY, selected_date);
            intent.putExtra(SELECTED_MONTH_KEY, selected_month);
            intent.putExtra(SELECTED_YEAR_KEY, selected_year);
            startActivity(intent);
        }
    }
}

