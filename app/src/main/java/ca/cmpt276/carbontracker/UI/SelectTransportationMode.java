package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.content.res.Resources;
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

import ca.cmpt276.carbontracker.Model.ActivityConstants;
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

    //tips
    static int counter = 0; //determines the priority


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
        final Spinner TransportationItems = (Spinner) findViewById(R.id.spinnerTransportationMode);
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


        //tip array for add_not_car
        Resources res = getResources();
        final String[] carTips = res.getStringArray(R.array.add_not_car);



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

                    Transportation newTransportation;
                    SingletonModel model = SingletonModel.getInstance();

                    //TODO: Just a substitute name, need to have layout for getting route name;
                    String name = "name";
                    if(ModeSelected.equals("Walk"))
                    {
                        newTransportation = new Walk();
                        Route newRoute = new Route(name, inputDist, 0);
                        model.addNewJourney(newTransportation, newRoute);
                    }
                    else if (ModeSelected.equals("Bike"))
                    {
                        newTransportation = new Bike();
                        Route newRoute = new Route(name, inputDist, 0);
                        model.addNewJourney(newTransportation, newRoute);
                    }
                    else if (ModeSelected.equals("SkyTrain"))
                    {
                        newTransportation = new Skytrain();
                        Route newRoute = new Route(name, inputDist, 0);
                        model.addNewJourney(newTransportation, newRoute);
                    }
                    else
                    {
                        newTransportation = new Bus();
                        Route newRoute = new Route(name, inputDist, 0);
                        model.addNewJourney(newTransportation, newRoute);
                    }

                    int latest = model.getJourneysCarbonEmissionList().size() - 1;
                    String s = model.getJourneysCarbonEmissionList().get(latest);

                    if(counter<4) {

                        Toast.makeText(SelectTransportationMode.this, carTips[counter] + s, Toast.LENGTH_LONG).show();
                    }
                    else if (counter==4){ //walks (need searchByDate for JourneyCollection)
                        s = model.getWalks()+"";
                        Toast.makeText(SelectTransportationMode.this, carTips[counter] + s, Toast.LENGTH_LONG).show();
                    }
                    else if (counter==5){ //bikes
                        s = model.getBike()+"";
                        Toast.makeText(SelectTransportationMode.this, carTips[counter] + s, Toast.LENGTH_LONG).show();
                    }
                    else if (counter==6){ //bus
                        s = model.getBus()+"";
                        Toast.makeText(SelectTransportationMode.this, carTips[counter] + s, Toast.LENGTH_LONG).show();
                    }
                    else if (counter==7){ //skytrain
                        s = model.getSkytrain()+"";
                        Toast.makeText(SelectTransportationMode.this, carTips[counter] + s, Toast.LENGTH_LONG).show();
                    }
                    counter++;
                    if(counter == 8)
                        counter = 0;

                    Toast.makeText(SelectTransportationMode.this, "Journey Added", Toast.LENGTH_SHORT).show();
                    finish();

                    Intent tipsWindow = new Intent(SelectTransportationMode.this, TipsActivity.class);
                    tipsWindow.putExtra("callingActivity", ActivityConstants.ACTIVITY_SELECT_TRANSPORTATION_MODE);
                    startActivity(tipsWindow);


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


