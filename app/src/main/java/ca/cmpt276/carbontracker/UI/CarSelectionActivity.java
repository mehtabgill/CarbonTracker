package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
public class CarSelectionActivity extends AppCompatActivity {

    private SingletonModel model = SingletonModel.getInstance();
    Spinner selectCarSpinner;
    int selectedCarIndex;
    Button selectCarButton;
    Menu menu;
    ArrayAdapter<String> adapter;
    ArrayList<String> currentCarListDescription;
    public static final String DESCRIPTION_KEY = "description";
    private static String ERROR_NO_CAR;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_plus, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.plus_item){
            startActivity(new Intent(CarSelectionActivity.this, AddCarActivity.class));
        }
        else if(id == R.id.edit_delete_item){
            if(currentCarListDescription.isEmpty()){
                Toast.makeText(CarSelectionActivity.this,
                        ERROR_NO_CAR,
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(CarSelectionActivity.this, EditDeleteCarActivity.class);
                intent.putExtra(DESCRIPTION_KEY, selectedCarIndex);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_selection);
        ERROR_NO_CAR = getString(R.string.error_no_car);

        selectCarButton = (Button) findViewById(R.id.select_car_button);
        selectCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCarListDescription.isEmpty()){
                    Toast.makeText(CarSelectionActivity.this,
                            ERROR_NO_CAR,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(CarSelectionActivity.this, SelectRouteActivity.class);
                    intent.putExtra(DESCRIPTION_KEY, selectedCarIndex);
                    startActivity(intent);
                }
            }
        });
        setupSpinnner();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setupSpinnner();
    }

    private void setupSpinnner() {
        selectCarSpinner = (Spinner)findViewById(R.id.select_car_spinner);
        currentCarListDescription = model.getCarEntriesDescription();
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, currentCarListDescription
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selectCarSpinner.setAdapter(adapter);
        selectCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCarIndex = selectCarSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
