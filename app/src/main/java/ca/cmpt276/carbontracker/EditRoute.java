package ca.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditRoute extends AppCompatActivity {

    EditText editName ;
    EditText editCityDistance ;
    EditText editHighDistance ;
    String EditingRouteName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        editName = (EditText) findViewById(R.id.editTextEditRouteName) ;
        editCityDistance = (EditText) findViewById(R.id.editTextEditHighway) ;
        editHighDistance = (EditText) findViewById(R.id.editTextEditHighway) ;


        final String name = editName.getText().toString() ;
        final String Cdistance = editCityDistance.getText().toString() ;
        final String hdistance = editHighDistance.getText().toString() ;
        Button btnok = (Button) findViewById(R.id.buttonOKEdit) ;
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent() ;
                intent.putExtra("editedName", name) ;
                intent.putExtra("editcity" , Cdistance) ;
                intent.putExtra("edithighway", hdistance) ;
                setResult(Activity.RESULT_OK , intent);
            }
        });


    }


}
