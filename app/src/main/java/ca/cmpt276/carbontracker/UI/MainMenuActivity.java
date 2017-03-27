package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.cmpt276.carbontracker.Model.ActivityConstants;
import ca.cmpt276.carbontracker.Model.DataReader;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI class to display main menu, including create new journey and view current carbon emission
 */
public class MainMenuActivity extends AppCompatActivity {

    private enum BUTTONS{CREATE_JOURNEY, CREATE_UTILITY_BILL, VIEW_CARBON_FOOTPRINT, EDIT_DELETE_UTILTITY, VIEW_JOURNEY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button createJourneyButton = (Button) findViewById(R.id.createJourneyButton);
        Button viewCarbonFootprintButton = (Button) findViewById(R.id.viewCarbonFootprintButton);
        Button createUtilityBillButton = (Button) findViewById(R.id.create_utility_bill_button);
        Button editDeleteUtilityBillButton = (Button) findViewById(R.id.edit_delete_utility_buitton);
        Button btnViewJourney = (Button) findViewById(R.id.btn_View_Journey);
        createJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.CREATE_JOURNEY);
            }
        });

        createUtilityBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.CREATE_JOURNEY.CREATE_UTILITY_BILL);
            }
        });

        viewCarbonFootprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_CARBON_FOOTPRINT);
            }
        });
        editDeleteUtilityBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.EDIT_DELETE_UTILTITY);
            }
        });
        btnViewJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_JOURNEY);
            }
        });
    }

    private void clickMainMenuButton(BUTTONS function) {
        switch (function){
            //Change this later
            case CREATE_JOURNEY:
                if(DataReader.isLoaded()){
                    startActivity(new Intent(MainMenuActivity.this, SelectTransportationMode.class));
                }
                else{
                    Toast.makeText(MainMenuActivity.this, "Still loading data", Toast.LENGTH_SHORT).show();
                }
                break;
            case CREATE_UTILITY_BILL:
                startActivity(new Intent(MainMenuActivity.this, AddUtilitiesBillActivity.class));
                break;
            case VIEW_CARBON_FOOTPRINT:
                startActivity(new Intent(MainMenuActivity.this, ViewCarbonFootprintActivity.class));

                break;
            case EDIT_DELETE_UTILTITY:
                startActivity(new Intent(MainMenuActivity.this, EditDeleteUtilitiesActivity.class));
                break;
            case VIEW_JOURNEY:
                startActivity(new Intent(MainMenuActivity.this, ViewJourney.class));
                break;
        }
    }
}

