package ca.cmpt276.carbontracker.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.carbontracker.carbontracker.R;
import ca.cmpt276.carbontracker.carbontracker.SelectTransportationModeActivity;
import ca.cmpt276.carbontracker.carbontracker.ViewCarbonFootprintActivity;

public class MainMenuActivity extends AppCompatActivity {

    private enum BUTTONS{CREATE_JOURNEY, VIEW_CARBON_FOOTPRINT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button createJourneyButton = (Button) findViewById(R.id.createJourneyButton);
        Button viewCarbonFootprintButton = (Button) findViewById(R.id.viewCarbonFootprintButton);
        createJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.CREATE_JOURNEY);
            }
        });
        viewCarbonFootprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainMenuButton(BUTTONS.VIEW_CARBON_FOOTPRINT);
            }
        });

    }

    private void clickMainMenuButton(BUTTONS function) {
        switch (function){
            case CREATE_JOURNEY:
            {
                startActivity(new Intent(MainMenuActivity.this, SelectTransportationModeActivity.class));
            }
            break;
            case VIEW_CARBON_FOOTPRINT:
            {
                startActivity(new Intent(MainMenuActivity.this, ViewCarbonFootprintActivity.class));
            }
            break;
        }
    }
}
