package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display Select transportation activity, including select car, delete car, edit car, add new car
 */
public class SelectTransportationModeActivity extends AppCompatActivity {
    Spinner selectCarSpinner;
    String selectedCarDescription;
    Button selectCarButton;
    Button addCarButton;
    Button editDeleteCarButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> currentCarListDescription;
    public static final String DESCRIPTION_KEY = "description";
    private static String ERROR_NO_CAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);
        ERROR_NO_CAR = getString(R.string.error_no_car);

        selectCarButton = (Button) findViewById(R.id.select_car_button);
        selectCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCarListDescription.isEmpty()){
                    Toast.makeText(SelectTransportationModeActivity.this,
                            ERROR_NO_CAR,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SelectTransportationModeActivity.this, SelectRouteActivity.class);
                    intent.putExtra(DESCRIPTION_KEY, selectedCarDescription);
                    startActivity(intent);
                }
            }
        });

        addCarButton = (Button) findViewById(R.id.add_car_button);
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTransportationModeActivity.this, AddCarActivity.class));
            }
        });


        editDeleteCarButton = (Button)findViewById(R.id.edit_delete_car_button);
        editDeleteCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCarListDescription.isEmpty()){
                    Toast.makeText(SelectTransportationModeActivity.this,
                            ERROR_NO_CAR,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SelectTransportationModeActivity.this, EditDeleteCarActivity.class);
                    intent.putExtra(DESCRIPTION_KEY, selectedCarDescription);
                    startActivity(intent);
                }

            }
        });
        selectCarSpinner = (Spinner)findViewById(R.id.select_car_spinner);

    }

    @Override
    protected void onResume(){
        super.onResume();
        //SingletonModel.updateCurrentCarCollectionDescription();
        currentCarListDescription = SingletonModel.getCarEntriesDescription(SingletonModel.RetriveEntries.Current);
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, currentCarListDescription
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selectCarSpinner.setAdapter(adapter);
        selectCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCarDescription = selectCarSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
