package ca.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditRoute extends AppCompatActivity {

    EditText editName ;
    EditText editCityDistance ;
    EditText editHighDistance ;
    String name = "no" ;
    String Cdistance = "0";
    String hdistance = "0" ;
    String OrignalName ;
    float temp1 = 0 ;
    float temp2 =  0  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);
        ExtractdataFRomIntent11() ;

        editName = (EditText) findViewById(R.id.editTextEditRouteName) ;
        editCityDistance = (EditText) findViewById(R.id.editTextEditCity) ;
        editHighDistance = (EditText) findViewById(R.id.editTextEditHighway) ;


        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = editName.getText().toString();
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
                Cdistance = editCityDistance.getText().toString() ;
            }

            @Override
            public void afterTextChanged(Editable s) {
                 Cdistance = editCityDistance.getText().toString() ;

            }
        });

        editHighDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hdistance = editHighDistance.getText().toString() ;
            }

            @Override
            public void afterTextChanged(Editable s) {
                hdistance = editHighDistance.getText().toString() ;
            }
        });
        temp1 = ParseFloat(Cdistance);
        temp2 = ParseFloat(hdistance) ;

        Button btnok = (Button) findViewById(R.id.buttonOKEdit) ;
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent() ;
                intent.putExtra("editedName", name) ;
                intent.putExtra("editcity" , temp1) ;
                intent.putExtra("edithighway", temp2) ;
                intent.putExtra("signalOrignalName", OrignalName) ;
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

    private void ExtractdataFRomIntent11() {
        Intent intent = getIntent() ;
        if(intent.hasExtra("signalEditingROute")){
        OrignalName = intent.getStringExtra("signalEditingROute") ;}
    }

    public static Intent intentmakerEditRoute(Context context, String name){
        Intent intent  =  new Intent(context, EditRoute.class);
        intent.putExtra("signalEditingROute" , name) ;
        return intent ;
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
