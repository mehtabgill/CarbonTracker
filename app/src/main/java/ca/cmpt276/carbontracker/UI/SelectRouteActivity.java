package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.RouteCollection;
import ca.cmpt276.carbontracker.UI.R;

public class SelectRouteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EditRoute = 1024;
    public static final int REQUEST_CODE_LauchAddRoute = 1025;
    public static final int REQUEST_CODE_LaunchDeletRoute = 1027;
    Spinner spinner ;
    String name ;
    float CityDriveDistance ;
    float HighwayDriveDistance  ;
    ArrayList<String> dropdown = new ArrayList<>();
    RouteCollection routeCollection = new RouteCollection() ;
    String OrignalName;  //= "abc";
    Button btnAddRoute;
    Button btnDelete;
    Button btnEditRoute;
    List<Route> list = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        dropdown.add("Exsisting Routes") ;
        dropdown.add("No Exsisting Routes");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dropdown);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);

        btnAddRoute = (Button) findViewById(R.id.buttonAddRoute);
        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRouteActivity.this, AddRouteActivity.class) ;
                startActivityForResult(intent,  REQUEST_CODE_LauchAddRoute);

            }
        });

        btnDelete = (Button) findViewById(R.id.buttonDeleteRoute) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteRouteActivity.intentmakerDeleteRoute(SelectRouteActivity.this, OrignalName);
                startActivityForResult(intent, REQUEST_CODE_LaunchDeletRoute);

            }
        });

        btnEditRoute = (Button) findViewById(R.id.buttonLaunchEditRoute);
        btnEditRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditRouteActivity.intentmakerEditRoute(SelectRouteActivity.this, OrignalName) ;
                startActivityForResult(intent, REQUEST_CODE_EditRoute);
            }
        });
        btnDelete.setEnabled(false);
        btnEditRoute.setEnabled(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OrignalName = dropdown.get(i);
                if (!dropdown.get(i).equals("Exsisting Routes") && !dropdown.get(i).equals("No Exsisting Routes") && !dropdown.get(i).equals("abc")){
                    if(!dropdown.equals(null)) {
                        OrignalName = dropdown.get(i);
                        EnableButtons();
                    }
                }
                else if (dropdown.get(i).equals("Exsisting Routes")){
                    btnDelete.setEnabled(false);
                    btnEditRoute.setEnabled(false);
                }

            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (data.hasExtra("editedName") && data.hasExtra("editcity") && data.hasExtra("edithighway") && data.hasExtra("signalOrignalName")) {
                            String newname = data.getStringExtra("editedName");
                            float newcity = data.getFloatExtra("editcity", 0);
                            float newhighway = data.getFloatExtra("edithighway", 0);
                            String OrignalName1 = data.getStringExtra("signalOrignalName");
                            routeCollection.EditRoute(OrignalName1, newname, newcity, newhighway);
                            Log.d("hello" , "SATNAM ji");
                            dropdown = new ArrayList<>();
                            dropdown.add("Exsisting Routes");
                            for (Route route : routeCollection.getAllRoutes()) {
                                dropdown.add(route.getName());
                            }
                            RefreshSpinner();
                        }

                switch(requestCode) {
                case REQUEST_CODE_LauchAddRoute:
                    if (resultCode == Activity.RESULT_OK) {
                        name = data.getStringExtra("NewAddedName");
                        CityDriveDistance = data.getFloatExtra("NewAddedcity", 0);
                        HighwayDriveDistance = data.getFloatExtra("NewAddedhighway", 0);
                        Route newRoute = new Route(name, CityDriveDistance, HighwayDriveDistance);
                        routeCollection.add(newRoute);
                        dropdown = new ArrayList<>();
                        dropdown.add("Exsisting Routes");
                        for (Route route : routeCollection.getAllRoutes()) {
                            dropdown.add(route.getName());
                        }
                        RefreshSpinner();
                    }
                case REQUEST_CODE_LaunchDeletRoute:
                    if (resultCode == Activity.RESULT_OK) {
                       String DeletingName = data.getStringExtra("signalDeletingName");
                        if (data.hasExtra("signalDeletingName")) {
                            routeCollection.remove(DeletingName);
                        }
                        dropdown = new ArrayList<>();
                        dropdown.add("Exsisting Routes");
                        for (Route route : routeCollection.getAllRoutes()) {
                            dropdown.add(route.getName());
                        }
                        RefreshSpinner();
                    }
            }

    }
    private void RefreshSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, dropdown);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);

    }
    public void EnableButtons()
    {
        btnDelete.setEnabled(true);
        btnEditRoute.setEnabled(true);
    }



}
