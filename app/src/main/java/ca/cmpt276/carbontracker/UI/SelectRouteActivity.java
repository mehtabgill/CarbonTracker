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

import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display Select Route Activity, including select route, add new route, delete/edit routes
 */
public class SelectRouteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EditRoute = 1024;
    public static final int REQUEST_CODE_LauchAddRoute = 1025;
    public static final int REQUEST_CODE_LaunchDeleteRoute = 1027;
    String selectedCarDescription;
    Spinner spinner ;
    String name;
    String selectedRouteName;
    float CityDriveDistance ;
    float HighwayDriveDistance  ;
    ArrayList<String> currentRouteList = new ArrayList<>();
    String orignalName;
    public static final String EDITED_NAME_STRING = "editedName";
    public static final String EDITED_HIGHWAY_STRING = "editedHighway";
    public static final String EDITED_CITY_STRING = "editedCity";
    public static final String SIGNAL_ORIGINAL_NAME_STRING = "signalOrignalName";
    public final String NEW_ADDED_NAME_STRING = "NewAddedName";
    public final String NEW_ADDED_CITY_STRING = "NewAddedCity";
    public final String NEW_ADDED_HIGHWAY_STRING = "NewAddedHighway";
    public final String SIGNAL_DELETING_NAME = "signalDeletingName";
    String selectedRoute;
    String ERROR_NO_ROUTE;
    Button btnAddRoute;
    Button btnDelete;
    Button btnEditRoute;
    Button btnSelectRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        receiveDescription();
        ERROR_NO_ROUTE = getString(R.string.error_no_route_selected);
        currentRouteList = SingletonModel.getRouteCollectionNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, currentRouteList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);

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
                    selectedRouteName = spinner.getSelectedItem().toString();
                    SingletonModel.addNewJourney(selectedCarDescription, selectedRouteName);
                    startActivity(new Intent(SelectRouteActivity.this, MainMenuActivity.class));
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
                selectedRouteName = spinner.getSelectedItem().toString();
                if(selectedRouteName.isEmpty()){
                    Toast.makeText(SelectRouteActivity.this,
                            getString(R.string.error_no_route_selected),
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = DeleteRouteActivity.intentMakerDeleteRoute(SelectRouteActivity.this, orignalName);
                    startActivityForResult(intent, REQUEST_CODE_LaunchDeleteRoute);
                }

            }
        });

        btnEditRoute = (Button) findViewById(R.id.buttonLaunchEditRoute);
        btnEditRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRouteName = spinner.getSelectedItem().toString();
                if (selectedRouteName.isEmpty()) {
                    Toast.makeText(SelectRouteActivity.this,
                            getString(R.string.error_no_route_selected),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = EditRouteActivity.intentMakerEditRoute(SelectRouteActivity.this, orignalName);
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
                    orignalName = selectedRoute;
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
        if (data.hasExtra(EDITED_NAME_STRING) && data.hasExtra(EDITED_CITY_STRING) && data.hasExtra(EDITED_HIGHWAY_STRING) && data.hasExtra(SIGNAL_ORIGINAL_NAME_STRING)) {
            String newName = data.getStringExtra(EDITED_NAME_STRING);
            float newCity = data.getFloatExtra(EDITED_CITY_STRING, 0);
            float newHighway = data.getFloatExtra(EDITED_HIGHWAY_STRING, 0);
            String orignalName = data.getStringExtra(SIGNAL_ORIGINAL_NAME_STRING);
            SingletonModel.editRoute(orignalName, newName, newCity, newHighway);
        }
        switch(requestCode) {
        case REQUEST_CODE_LauchAddRoute:
            if (resultCode == Activity.RESULT_OK) {

                name = data.getStringExtra(NEW_ADDED_NAME_STRING);

                CityDriveDistance = data.getFloatExtra(NEW_ADDED_CITY_STRING, 0);

                HighwayDriveDistance = data.getFloatExtra(NEW_ADDED_HIGHWAY_STRING, 0);
                Route newRoute = new Route(name, CityDriveDistance, HighwayDriveDistance);
                SingletonModel.addNewRoute(newRoute);
        }
        case REQUEST_CODE_LaunchDeleteRoute:
            if (resultCode == Activity.RESULT_OK) {
                String deletingName = data.getStringExtra(SIGNAL_DELETING_NAME);
                if (data.hasExtra(SIGNAL_DELETING_NAME)) {
                    SingletonModel.removeFromRouteCollection(deletingName);
                }
            }
        }
    }
    private void refreshSpinner(){
        currentRouteList = SingletonModel.getRouteCollectionNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, currentRouteList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);

    }
    private void receiveDescription(){
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            selectedCarDescription = extra.getString(CarSelectionActivity.DESCRIPTION_KEY);
        }
    }

    public void enableButtons()
    {
        btnDelete.setEnabled(true);
        btnEditRoute.setEnabled(true);
    }
}
