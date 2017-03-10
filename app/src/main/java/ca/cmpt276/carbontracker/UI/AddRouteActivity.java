package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.SingletonModel;


/*
 * UI class to display Add Route Activity
 */
public class AddRouteActivity extends AppCompatActivity {

    String NEW_ADDED_NAME = "NewAddedName";
    String NEW_ADDED_CITY = "NewAddedCity";
    String NEW_ADDED_HIGHWAY = "NewAddedHighway";

    EditText editName ;
    EditText editCityDistance ;
    EditText editHighDistance ;
    float cityDis = 0 ;
    float highwayDis = 0 ;
    String name ;
    String Cdistance ;
    String hdistance  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        editName  = (EditText) findViewById(R.id.editText_RouteName) ;
        editCityDistance = ( EditText) findViewById(R.id.editText_cityDistance);
        editHighDistance = (EditText) findViewById(R.id.editText_highwayDistance) ;



        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                 name = editName.getText().toString() ;

            }
        });

        editCityDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Cdistance = editCityDistance.getText().toString() ;
                cityDis = ParseFloat(Cdistance) ;
            }
        });

        editHighDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hdistance = editHighDistance.getText().toString() ;
                highwayDis = ParseFloat(hdistance) ;


            }
        });

        Button btnAddRoute = (Button)findViewById(R.id.ButtonAddRoute) ;
        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route route = new Route();
                route.setName(name);
                route.setCityDriveDistance(cityDis);
                route.setHighwayDriveDistance(highwayDis);
                SingletonModel.addNewRoute(route);
                finish();

            }
        });

        Button btnCancel = (Button) findViewById(R.id.buttonCancel) ;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

        float ParseFloat(String str ) {
        if (str != null && str.length() > 0) {
        try {
        return Float.parseFloat(str);
        } catch(Exception e) {
        return -1;
        }
        }
        else return 0;
        }
}
