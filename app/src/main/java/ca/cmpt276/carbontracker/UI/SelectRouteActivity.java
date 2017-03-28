package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.ActivityConstants;
import ca.cmpt276.carbontracker.Model.Car;
import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display Select Route Activity, including select route, add new route, delete/edit routes
 */
public class SelectRouteActivity extends AppCompatActivity {
    private SingletonModel model = SingletonModel.getInstance();
    public static final int REQUEST_CODE_EditRoute = 1024;
    public static final int REQUEST_CODE_LauchAddRoute = 1025;
    public static final int REQUEST_CODE_LaunchDeleteRoute = 1027;
    //String selectedCarDescription;
    int selectedCarIndex;
    Spinner spinner ;
    String name;
    String selectedRouteName;
    float CityDriveDistance ;
    float HighwayDriveDistance  ;
    ArrayList<String> currentRouteList = new ArrayList<>();
    public static final String EDITED_NAME_STRING = "editedName";
    public static final String EDITED_HIGHWAY_STRING = "editedHighway";
    public static final String EDITED_CITY_STRING = "editedCity";
    public static final String SIGNAL_ORIGINAL_INDEX = "signalOrignalIndex";
    public static final String NEW_ADDED_NAME_STRING = "NewAddedName";
    public static final String NEW_ADDED_CITY_STRING = "NewAddedCity";
    public static final String NEW_ADDED_HIGHWAY_STRING = "NewAddedHighway";
    public static final String SIGNAL_DELETING_INDEX = "signalDeletingName";
    String selectedRoute;
    String ERROR_NO_ROUTE;
    Button btnAddRoute;
    Button btnDelete;
    Button btnEditRoute;
    Button btnSelectRoute;

    //tips
    //static int counter = 0; //determines the priority
    //private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        receiveDescription();
        ERROR_NO_ROUTE = getString(R.string.error_no_route_selected);
        currentRouteList = model.getRouteCollectionNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, currentRouteList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);

        //tip array for add_car_journey
        //Resources res = getResources();
        //final String[] carTips = res.getStringArray(R.array.add_car_journey);
        //*final RelativeLayout parent = new RelativeLayout(this);

        btnSelectRoute = (Button) findViewById(R.id.select_route_button);
        btnSelectRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRouteList.isEmpty()){
                    Toast.makeText(SelectRouteActivity.this,
                            ERROR_NO_ROUTE,
                                    Toast.LENGTH_SHORT).show();
                }
                else{
                    Car car = model.getCar(selectedCarIndex);
                    int selectedRouteIndex = spinner.getSelectedItemPosition();
                    Route route = model.getRoute(selectedRouteIndex);
                    /*
                    selectedRouteName = spinner.getSelectedItem().toString();
                    model.addNewJourney(selectedCarDescription, selectedRouteName);*/
                    model.addNewJourney(car, route);
                    Toast.makeText(getApplicationContext(), "Journey added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SelectRouteActivity.this, MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    //TODO delete later

                    //*************************************************************************************
                    //show tip------------------------------------------------------------------------------

                    //**layoutInflater= (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    //**ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.tips_layout, null);
                    //**final PopupWindow window = new PopupWindow(container, 400, 400, true);
                    //window.showAtLocation(parent, Gravity.BOTTOM, 500, 500);

                    /*new Handler().postDelayed(new Runnable(){

                        public void run() {
                            window.showAtLocation(parent, Gravity.CENTER, 0, 0);
                        }

                    }, 100L);*/
                    /*
                    int latest = model.getJourneysCarbonEmissionList().size() - 1;
                    String s = model.getJourneysCarbonEmissionList().get(latest);

                    Toast.makeText(SelectRouteActivity.this, carTips[counter]+s, Toast.LENGTH_LONG).show();
                    counter++;
                    if(counter == 8)
                        counter = 0;
                    */
                    //show tip-----------------------------------------------------------------------------
                    //**************************************************************************************
                    //end TODO
                    startActivity(intent);
                    Intent tipsWindow = new Intent(SelectRouteActivity.this, TipsActivity.class);
                    tipsWindow.putExtra("callingActivity", ActivityConstants.ACTIVITY_SELECT_ROUTE);
                    startActivity(tipsWindow);
                }
            }
        });

        btnAddRoute = (Button) findViewById(R.id.buttonAddRoute);
        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRouteActivity.this, AddRouteActivity.class) ;
                startActivity(intent);

            }
        });

        btnDelete = (Button) findViewById(R.id.buttonDeleteRoute) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getCount() == 0){
                    Toast.makeText(SelectRouteActivity.this,
                            getString(R.string.error_no_route_selected),
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), DeleteRouteActivity.class);
                    intent.putExtra(SIGNAL_DELETING_INDEX, spinner.getSelectedItemPosition());
                    startActivityForResult(intent, REQUEST_CODE_LaunchDeleteRoute);
                }

            }
        });

        btnEditRoute = (Button) findViewById(R.id.buttonLaunchEditRoute);
        btnEditRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner.getCount() == 0) {
                    Toast.makeText(SelectRouteActivity.this,
                            getString(R.string.error_no_route_selected),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), EditRouteActivity.class);
                    intent.putExtra(SIGNAL_ORIGINAL_INDEX, spinner.getSelectedItemPosition());
                    startActivityForResult(intent, REQUEST_CODE_EditRoute);
                }
            }
        });
        btnDelete.setEnabled(false);
        btnEditRoute.setEnabled(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoute = spinner.getSelectedItem().toString();
                if (selectedRoute.isEmpty()){
                    btnDelete.setEnabled(false);
                    btnEditRoute.setEnabled(false);
                }
                else{
                    enableButtons();
                }

            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    protected void onResume(){
        super.onResume();
        refreshSpinner();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LauchAddRoute:
                    name = data.getStringExtra(NEW_ADDED_NAME_STRING);

                    CityDriveDistance = data.getFloatExtra(NEW_ADDED_CITY_STRING, 0);

                    HighwayDriveDistance = data.getFloatExtra(NEW_ADDED_HIGHWAY_STRING, 0);
                    Route newRoute = new Route(name, CityDriveDistance, HighwayDriveDistance);
                    model.addRoute(newRoute);
                    if(!btnDelete.isEnabled() || !btnEditRoute.isEnabled()){
                        btnDelete.setEnabled(true);
                        btnEditRoute.setEnabled(true);
                    }
                    break;
                case REQUEST_CODE_LaunchDeleteRoute:
                    int deletingIndex = data.getIntExtra(SIGNAL_DELETING_INDEX, 0);
                    if (data.hasExtra(SIGNAL_DELETING_INDEX)) {
                        model.removeRoute(deletingIndex);
                    }
                    break;
                case REQUEST_CODE_EditRoute:
                    String newName = data.getStringExtra(EDITED_NAME_STRING);
                    float newCity = data.getFloatExtra(EDITED_CITY_STRING, 0);
                    float newHighway = data.getFloatExtra(EDITED_HIGHWAY_STRING, 0);
                    int originalIndex = data.getIntExtra(SIGNAL_ORIGINAL_INDEX, 0);
                    Route route = new Route(newName, newCity, newHighway);
                    model.editRoute(originalIndex, route);
                    break;
            }
        }
    }
    private void refreshSpinner(){
        currentRouteList = model.getRouteCollectionNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, currentRouteList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);
        if(spinner.getCount() == 0) {
            btnDelete.setEnabled(false);
            btnEditRoute.setEnabled(false);
        }

    }
    private void receiveDescription(){
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            selectedCarIndex = extra.getInt(CarSelectionActivity.DESCRIPTION_KEY);
        }
    }

    public void enableButtons()
    {
        btnDelete.setEnabled(true);
        btnEditRoute.setEnabled(true);
    }
}
