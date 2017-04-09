package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;

import ca.cmpt276.carbontracker.Model.DataReader;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display main menu, including create new journey and view current carbon emission
 */
public class MainMenuActivity extends AppCompatActivity {

    private enum BUTTONS{VIEW_CARBON_FOOTPRINT, VIEW_UTILITIES, VIEW_JOURNEY};
    private static int selected_year;
    private static int selected_month;
    private static int selected_date;
    public static String selected_mode;

    public static final String SELECTED_DATE_KEY = "SELECTED_DAY";
    public static final String SELECTED_MONTH_KEY = "SELECTED_MONTH";
    public static final String SELECTED_YEAR_KEY = "SELECTED_YEAR";
    public static final String DAY_MODE_KEY = "DAY";
    public static final String MODE_KEY = "MODE";

    BitmapDrawable journeyBackground;
    BitmapDrawable utilitiesBackground;
    BitmapDrawable viewDataBackground;

    Button btnViewJourney;
    Button editDeleteUtilityBillButton;
    Button viewCarbonFootprintButton;
    Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        this.menu = menu;
        if(SingletonModel.getInstance().inTestingMode()){
            menu.findItem(R.id.delete_all_data_item).setVisible(true);
        }
        else{
            menu.findItem(R.id.delete_all_data_item).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.setting_item:
                startActivity(new Intent(MainMenuActivity.this,OptionsActivity.class));
                break;
            case R.id.info_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                builder.setTitle(getString(R.string.about_header));
                builder.setMessage(getString(R.string.about));
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.delete_all_data_item:
                if(SingletonModel.getInstance().inTestingMode()){
                    SingletonModel.getInstance().deleteAllDataFromDB();
                    Toast.makeText(MainMenuActivity.this, "All data deleted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnViewJourney = (Button) findViewById(R.id.btn_View_Journey);
        editDeleteUtilityBillButton = (Button) findViewById(R.id.edit_delete_utility_buitton);
        viewCarbonFootprintButton = (Button) findViewById(R.id.viewCarbonFootprintButton);
        setUpButtonImage();

        viewCarbonFootprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_CARBON_FOOTPRINT);
            }
        });
        editDeleteUtilityBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_UTILITIES);
            }
        });
        btnViewJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_JOURNEY);
            }
        });
    }

    private void setUpButtonImage() {

        final RelativeLayout mainMenuLayout = (RelativeLayout) findViewById(R.id.activity_main_menu);
        ViewTreeObserver observer = mainMenuLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int newWidth = mainMenuLayout.getWidth();
                int newHeight = (int) (mainMenuLayout.getHeight()/3.5);
                Bitmap originalBitmap1 =  BitmapFactory.decodeResource(getResources(), R.drawable.journey_tab);
                Bitmap originalBitmap2 =  BitmapFactory.decodeResource(getResources(), R.drawable.utilities_tab);
                Bitmap originalBitmap3 =  BitmapFactory.decodeResource(getResources(), R.drawable.data_tab2);
                Bitmap scaledBitmap1 =  Bitmap.createScaledBitmap(originalBitmap1, newWidth, newHeight, true);
                Bitmap scaledBitmap2 =  Bitmap.createScaledBitmap(originalBitmap2, newWidth, newHeight, true);
                Bitmap scaledBitmap3 =  Bitmap.createScaledBitmap(originalBitmap3, newWidth, newHeight, true);
                journeyBackground = new BitmapDrawable(getResources(), scaledBitmap1);
                utilitiesBackground = new BitmapDrawable(getResources(), scaledBitmap2);
                viewDataBackground = new BitmapDrawable(getResources(), scaledBitmap3);
                btnViewJourney.setBackgroundDrawable(journeyBackground);
                editDeleteUtilityBillButton.setBackgroundDrawable(utilitiesBackground);
                viewCarbonFootprintButton.setBackgroundDrawable(viewDataBackground);
            }
        });

    }

    private void clickMainMenuButton(BUTTONS function) {
        switch (function){
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
            case VIEW_UTILITIES:
                startActivity(new Intent(MainMenuActivity.this, EditDeleteUtilitiesActivity.class));
                break;
            case VIEW_JOURNEY:
                startActivity(new Intent(MainMenuActivity.this, ViewJourneyActivity.class));
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

