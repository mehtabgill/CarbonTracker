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
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.Bike;
import ca.cmpt276.carbontracker.Model.Bus;
import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.SingletonModel;
import ca.cmpt276.carbontracker.Model.Skytrain;
import ca.cmpt276.carbontracker.Model.Transportation;
import ca.cmpt276.carbontracker.Model.Walk;

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

        final EditText distEntered= (EditText)findViewById(R.id.distanceEntered);
        final TextView dist = (TextView)findViewById(R.id.distance);

        TransportationItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModeSelected = TransportationItems.getSelectedItem().toString();

                if(ModeSelected.equals("Car")){


                    dist.setVisibility(View.INVISIBLE);
                    distEntered.setVisibility(View.INVISIBLE);
                }
                if(ModeSelected.equals("Bus") || ModeSelected.equals("SkyTrain") || ModeSelected.equals("Walk") || ModeSelected.equals("Bike")){

                    dist.setVisibility(View.VISIBLE);
                    distEntered.setVisibility(View.VISIBLE);
                }

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
                    EditText d = (EditText)findViewById(R.id.distanceEntered);
                    String input = d.getText().toString();
                    float inputDist = Float.parseFloat(input);

                    Transportation mode;

                    if(ModeSelected.equals("Walk"))
                    {
                        mode = new Walk(inputDist);
                        Route r = new Route();
                        SingletonModel.addNewJourney(mode, r, 0f);
                    }
                    else if (ModeSelected.equals("Bike"))
                    {
                        mode = new Bike(inputDist);
                        Route r = new Route();
                        SingletonModel.addNewJourney(mode, r, 0f);
                    }
                    else if (ModeSelected.equals("SkyTrain"))
                    {
                        mode = new Skytrain(inputDist);
                        Route r = new Route();
                        SingletonModel.addNewJourney(mode, r, mode.getCarbonEmitted());
                    }
                    else
                    {
                        mode = new Bus(inputDist);
                        Route r = new Route();
                        SingletonModel.addNewJourney(mode, r, mode.getCarbonEmitted());
                    }

                    Toast.makeText(SelectTransportationMode.this, "Journey Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SelectTransportationMode.this, MainMenuActivity.class);
                    startActivity(intent);


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


