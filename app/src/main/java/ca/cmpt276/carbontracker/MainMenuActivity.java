package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;

public class MainMenuActivity extends AppCompatActivity {

    private enum BUTTONS{CREATE_JOURNEY, VIEW_CARBON_FOOTPRINT};
    public static CarCollection collection;
    private class LoadDataTask extends AsyncTask<InputStream, Integer, CarCollection>
    {
        @Override
        protected CarCollection doInBackground(InputStream... is) {
            CarCollection result = new CarCollection();
            DataReader.readCarData(is[0], result);
            return result;
        }

        protected void onPostExecute(CarCollection carCollection){
            Toast.makeText(MainMenuActivity.this,
                    "Load completed", Toast.LENGTH_SHORT).show();
            Button createJourneyButton = (Button) findViewById(R.id.createJourneyButton);
            Button viewCarbonFootprintButton = (Button) findViewById(R.id.viewCarbonFootprintButton);
            createJourneyButton.setEnabled(true);
            viewCarbonFootprintButton.setEnabled(true);
            collection = carCollection;
        }
    }

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
        new LoadDataTask().execute(getResources().openRawResource(R.raw.data));

    }

    private void clickMainMenuButton(BUTTONS function) {
        switch (function){
            //Change this later
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

