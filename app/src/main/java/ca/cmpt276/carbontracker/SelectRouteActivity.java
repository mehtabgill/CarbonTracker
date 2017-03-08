package ca.cmpt276.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SelectRouteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EditRoute = 1024;
    String name ;
    float CityDriveDistance  = -2;
    float HighwayDriveDistance = -2 ;
    ArrayList<String> dropdown = new ArrayList<>();
    RouteCollection routeCollection = new RouteCollection() ;
    String OrignalName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        ExtractDataFromIntent() ;

        if ( CityDriveDistance == -1 && HighwayDriveDistance == -1 ){
            ArrayList<Route> list=  routeCollection.findRouteWithName(name) ;
            for(Route route: list){
                routeCollection.remove(route);
            }
        }
        else{
        Route newRoute = new Route( name ,CityDriveDistance, HighwayDriveDistance) ;
        routeCollection.add(newRoute);}

        dropdown.add("Exsisting Rotes");

        for ( Route route : routeCollection.getAllRoutes()){
            dropdown.add(route.getName()) ;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dropdown);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.select_route_spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OrignalName = dropdown.get(i) ;

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Button btnAddRoute = (Button) findViewById(R.id.buttonAddRoute);
        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRouteActivity.this, AddRoute.class) ;
                startActivity(intent);
            }
        });

        Button btnDelete = (Button) findViewById(R.id.buttonDeleteRoute) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRouteActivity.this, DeleteRoute.class);
                startActivity(intent);

            }
        });

        Button btnEditRoute = (Button) findViewById(R.id.buttonLaunchEditRoute);
        btnEditRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditRoute.intentmakerEditRoute(SelectRouteActivity.this, OrignalName) ;
                startActivityForResult(intent, REQUEST_CODE_EditRoute);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                String newname = data.getStringExtra("editedName") ;
                float newcity = data.getFloatExtra("editcity", 0) ;
                float newhighway = data.getFloatExtra("edithighway", 0);
                String OrignalName = data.getStringExtra("signalOrignalName") ;
                routeCollection.EditRoute(OrignalName, newname, newcity, newhighway);

                dropdown = new ArrayList<>() ;
                dropdown.add("Exsisting Rotes");

                for ( Route route : routeCollection.getAllRoutes()){
                    dropdown.add(route.getName()) ;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                       this, android.R.layout.simple_spinner_item, dropdown);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = (Spinner) findViewById(R.id.select_route_spinner);
                spinner.setAdapter(adapter);


    }

    private void ExtractDataFromIntent() {
        Intent intent = getIntent() ;
        name = intent.getStringExtra("signalNewRouteName") ;
        CityDriveDistance = intent.getFloatExtra("signalNewRouteCity", 0);
        HighwayDriveDistance = intent.getFloatExtra("signalNewRouteHighway", 0) ;

    }

    public static Intent intentmakerSelectRoute (Context context , Route route){
        Intent intent = new Intent(context, SelectRouteActivity.class) ;
        intent.putExtra("signalNewRouteName", route.getName()) ;
        intent.putExtra("signalNewRouteCity", route.getCityDriveDistance()) ;
        intent.putExtra("signalNewRouteHighway" , route.getHighwayDriveDistance()) ;
        return intent ;
    }
}
