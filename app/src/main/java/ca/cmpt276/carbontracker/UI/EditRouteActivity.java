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
 * UI class to display edit route activity, including edit name, city and/or highway distance
 */
public class EditRouteActivity extends AppCompatActivity {

    EditText editName ;
    EditText editCityDistance ;
    EditText editHighDistance ;
    String name = "no" ;
    String cDistance = "0";
    String hDistance = "0" ;
    final static String SIGNAL_EDITING_ROUTE = "signalEditingRoute";
    int originalIndex;
    float temp1 = 0 ;
    float temp2 =  0  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);
        //extractDataFromIntent() ;
        Intent intent = getIntent();
        originalIndex = intent.getIntExtra(SelectRouteActivity.SIGNAL_ORIGINAL_INDEX, 0);


        editName = (EditText) findViewById(R.id.editTextEditRouteName) ;
        editCityDistance = (EditText) findViewById(R.id.editTextEditCity) ;
        editHighDistance = (EditText) findViewById(R.id.editTextEditHighway) ;

        SingletonModel model = SingletonModel.getInstance();
        Route route = model.getRoute(originalIndex);

        editName.setText(route.getName());
        editCityDistance.setText(String.valueOf(route.getCityDriveDistance()));
        editHighDistance.setText(String.valueOf(route.getHighwayDriveDistance()));

        editName.requestFocus();

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = editName.getText().toString() ;
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
                 cDistance = editCityDistance.getText().toString() ;
                 if(!cDistance.isEmpty()){
                     temp1 = Float.parseFloat(cDistance);
                 }
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
                hDistance = editHighDistance.getText().toString() ;
                if(!hDistance.isEmpty()){
                    temp2 = Float.parseFloat(hDistance);
                }
            }
        });

        Button btnOK = (Button) findViewById(R.id.buttonOKEdit) ;
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent() ;
                intent.putExtra(SelectRouteActivity.EDITED_NAME_STRING, name) ;
                intent.putExtra(SelectRouteActivity.EDITED_CITY_STRING , temp1) ;
                intent.putExtra(SelectRouteActivity.EDITED_HIGHWAY_STRING, temp2) ;
                intent.putExtra(SelectRouteActivity.SIGNAL_ORIGINAL_INDEX, originalIndex) ;
                setResult(Activity.RESULT_OK , intent);
                finish();
            }
        });
        Button btnCancel = (Button) findViewById(R.id.buttonCancelEdit) ;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

//    private void extractDataFromIntent() {
//        Intent intent = getIntent();
//        if(intent.hasExtra(SIGNAL_EDITING_ROUTE)){
//        originalIndex = intent.getStringExtra(SIGNAL_EDITING_ROUTE) ;}
//    }
//
//    public static Intent intentMakerEditRoute(Context context, String name){
//        Intent intent = new Intent(context, EditRouteActivity.class);
//        intent.putExtra(SIGNAL_EDITING_ROUTE, name) ;
//        return intent;
//    }
//    float ParseFloat(String str ) {
//        if (str != null && str.length() > 0) {
//            try {
//                return Float.parseFloat(str);
//            } catch(Exception e) {
//                return -1;
//            }
//        }
//        else return 0;
//    }

}
