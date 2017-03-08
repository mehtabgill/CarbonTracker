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
    String name  ;
    String Cdistance ;
    String hdistance  ;
    String OrignalName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);
        ExtractdataFRomIntent() ;

        editName = (EditText) findViewById(R.id.editTextEditRouteName) ;
        editCityDistance = (EditText) findViewById(R.id.editTextEditHighway) ;
        editHighDistance = (EditText) findViewById(R.id.editTextEditHighway) ;


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
            }
        });

        Button btnok = (Button) findViewById(R.id.buttonOKEdit) ;
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent() ;
                intent.putExtra("editedName", name) ;
                intent.putExtra("editcity" , Cdistance) ;
                intent.putExtra("edithighway", hdistance) ;
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

    private void ExtractdataFRomIntent() {
        Intent intent = getIntent() ;
        OrignalName = intent.getStringExtra("signalEditingROute") ;
    }

    public static Intent intentmakerEditRoute(Context context, String name){
        Intent intent  =  new Intent(context, EditRoute.class);
        intent.putExtra("signalEditingROute" , name) ;
        return intent ;
    }

}
