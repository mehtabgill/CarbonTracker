package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectTransportationMode extends AppCompatActivity {

    String ModeSelected = "" ;
    ArrayList<String> TransportationModeList = new ArrayList<>() ;
    Button btnOk ;
    Button btnCancel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);

        TransportationModeList.add("Car");
        TransportationModeList.add("Bus");
        TransportationModeList.add("Bike");
        TransportationModeList.add("SkyTrain");
        TransportationModeList.add("Walk");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, TransportationModeList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner TransportationItems = (Spinner) findViewById(R.id.spinnerTranspportationMode);
        TransportationItems.setAdapter(adapter);

        TransportationItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModeSelected = TransportationItems.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        btnOk = (Button) findViewById(R.id.buttonOkForSelectedTransportationMode);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ModeSelected.equals("Car")){
                    Intent intent = new Intent(SelectTransportationMode.this, CarSelectionActivity.class);
                    startActivity(intent) ;
                }
                else{



                }
            }
        });

        btnCancel = (Button) findViewById(R.id.buttonCancelForTRansporttationModeSelected);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /* fix later on laptop
    Spinner TransportationItems = (Spinner) findViewById(R.id.spinnerTranspportationMode);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, TransportationModeList);
    TransportationItems.setAdapter(adapter);
    TransportationItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ModeSelected = TransportationItems.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });


    EditText distEntered= (EditText)findViewById(R.id.distanceEntered);
    TextView dist = (TextView)findViewById(R.id.distance);

        if(ModeSelected.equals("Car")){


        dist.setVisibility(View.INVISIBLE);
        distEntered.setVisibility(View.INVISIBLE);
    }
        if(ModeSelected.equals("Bus") || ModeSelected.equals("SkyTrain") || ModeSelected.equals("Walk") || ModeSelected.equals("Bike")){

        dist.setVisibility(View.VISIBLE);
        distEntered.setVisibility(View.VISIBLE);
    }*/

}


