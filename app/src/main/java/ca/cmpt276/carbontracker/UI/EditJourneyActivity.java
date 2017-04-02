package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.Car;
import ca.cmpt276.carbontracker.Model.CarStorage;
import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.SingletonModel;
import ca.cmpt276.carbontracker.Model.Transportation;


public class EditJourneyActivity extends AppCompatActivity {

    SingletonModel model = SingletonModel.getInstance();

    LinearLayout layout;
    View transportationChild;
    View routeChild;
    View editTransportationChild;
    View editRouteChild;
    Transportation transportation;
    Route route;
    int index;

    Menu menu;

    Spinner spinnerMake;
    Spinner spinnerModel;
    Spinner spinnerYear;

    boolean isSelectedTransportation = false;
    boolean isSelectedRoute = false;
    boolean setupMode = true;
    boolean setupCar = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete) {
            model.removeJourney(index);
            finish();
        }
        else if(id == R.id.cancel) {
            if(isSelectedRoute) {
                routeClicked();
            }
            else if(isSelectedTransportation) {
                transportationClicked();
            }
        }
        else if(id == R.id.edit) {
            if(isSelectedRoute) {
                int cityDistance = 0;
                int hwyDistance = 0;
                EditText editTextCity = (EditText) findViewById(R.id.editText_cityDistance);
                EditText editTextHwy = (EditText) findViewById(R.id.editText_hwyDistance);

                String city = editTextCity.getText().toString();
                String hwy = editTextHwy.getText().toString();

                if(city.length() > 0) {
                    cityDistance = Integer.parseInt(city);
                }
                if(hwy.length() > 0) {
                    hwyDistance = Integer.parseInt(hwy);
                }
                if(cityDistance + hwyDistance == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a value greater than 0", Toast.LENGTH_SHORT).show();
                }
                else {
                    Route route = new Route(this.route.getName(), cityDistance, hwyDistance);
                    model.setRouteOfJourneyAt(index, route);
                    refreshRoute();
                    routeClicked();
                    Toast.makeText(getApplicationContext(), "Route updated!", Toast.LENGTH_SHORT).show();
                }
            }
            if(isSelectedTransportation) {

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journey);

        Intent intent = getIntent();
        index = intent.getIntExtra(ViewJourneyActivity.EXTRA_POSITION, -1);

        transportation = model.getTransportationOfJourneyAt(index);
        route = model.getRouteOfJourneyAt(index);

        layout = (LinearLayout)findViewById(R.id.layout_edit_journey);
        transportationChild = getLayoutInflater().inflate(R.layout.list_transportation, null);
        layout.addView(transportationChild);
        routeChild = getLayoutInflater().inflate(R.layout.list_route, null);
        layout.addView(routeChild);
        editTransportationChild = getLayoutInflater().inflate(R.layout.activity_edit_transportation_journey, null);
        editRouteChild = getLayoutInflater().inflate(R.layout.activity_edit_route_journey, null);

        ActionBar actionBar = getSupportActionBar();

        refreshTransportation();
        refreshRoute();
        registerClickCallback();
    }

    private void setupModeSpinner() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_mode);
        if (spinner != null && setupMode) {
            EditText editText = (EditText) findViewById(R.id.editText_car_name);
            if(transportation.getType() == Transportation.TRANSPORTATION_TYPE.CAR) {
                Car car = (Car) transportation;
                editText.setText(car.getNickname());
            }
            else {
                setVisibilityOfCarFields(View.GONE);
            }
            Transportation.TRANSPORTATION_TYPE[] temp = Transportation.TRANSPORTATION_TYPE.values();
            ArrayList<String> modes = new ArrayList<>();
            for (Transportation.TRANSPORTATION_TYPE type : temp) {
                modes.add(type.toString());
            }

            populateSpinner(spinner, modes);

            spinner.setSelection(transportation.getType().ordinal());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(layout.getChildCount() > 3) {
                        layout.removeView(editRouteChild);
                    }
                    if (spinner.getSelectedItemPosition() != Transportation.TRANSPORTATION_TYPE.CAR.ordinal()) {
                        setVisibilityOfCarFields(View.GONE);
                        if(transportation.getType() == Transportation.TRANSPORTATION_TYPE.CAR) {
                            if(layout.getChildCount() <= 3) {
                                layout.addView(editRouteChild);
                                EditText editTextCity = (EditText) findViewById(R.id.editText_cityDistance);
                                EditText editTextHwy = (EditText) findViewById(R.id.editText_hwyDistance);
                                TextView textViewCity = (TextView) findViewById(R.id.textView_city_distance);
                                TextView textViewHwy = (TextView) findViewById(R.id.textView_hwy_distance);

                                editTextHwy.setVisibility(View.GONE);
                                textViewHwy.setVisibility(View.GONE);

                            }
                        }
                    } else {
                        setVisibilityOfCarFields(View.VISIBLE);
                        if(transportation.getType() != Transportation.TRANSPORTATION_TYPE.CAR) {
                            if(layout.getChildCount() <= 3) {
                                layout.addView(editRouteChild);
                                EditText editTextCity = (EditText) findViewById(R.id.editText_cityDistance);
                                EditText editTextHwy = (EditText) findViewById(R.id.editText_hwyDistance);
                                TextView textViewCity = (TextView) findViewById(R.id.textView_city_distance);
                                TextView textViewHwy = (TextView) findViewById(R.id.textView_hwy_distance);

                                editTextHwy.setVisibility(View.VISIBLE);
                                textViewHwy.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void setVisibilityOfCarFields(int visibility) {
        EditText editText = (EditText) findViewById(R.id.editText_car_name);
        editText.setVisibility(visibility);
        Spinner temp = (Spinner) findViewById(R.id.spinner_make);
        temp.setVisibility(visibility);
        temp = (Spinner) findViewById(R.id.spinner_model);
        temp.setVisibility(visibility);
        temp = (Spinner) findViewById(R.id.spinner_year);
        temp.setVisibility(visibility);
    }

    private void populateSpinner(Spinner spinner, ArrayList<String> choices) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, choices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    private void registerClickCallback() {
        routeChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeClicked();
            }
        });
        transportationChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transportationClicked();
            }
        });
    }

    private void transportationClicked() {
        isSelectedTransportation = !isSelectedTransportation;
        isSelectedRoute = false;
        refreshEditRoute();
        refreshEditTransportation();
    }

    private void routeClicked() {
        isSelectedRoute = !isSelectedRoute;
        isSelectedTransportation = false;
        refreshEditTransportation();
        refreshEditRoute();
    }

    private void menuEditMode(boolean edit) {
        menu.findItem(R.id.delete).setVisible(!edit);
        menu.findItem(R.id.edit).setVisible(edit);
        menu.findItem(R.id.cancel).setVisible(edit);
    }

    private void refreshEditTransportation() {
        if(isSelectedTransportation){
            layout.addView(editTransportationChild);
            transportationChild.setBackgroundResource(R.drawable.background_border_blue);
            Spinner spinner = (Spinner) findViewById(R.id.spinner_mode);
            if(spinner.getSelectedItemPosition() != Transportation.TRANSPORTATION_TYPE.CAR.ordinal() &&
                    transportation.getType() == Transportation.TRANSPORTATION_TYPE.CAR) {
                layout.addView(editRouteChild);
                setVisibilityOfHwyDistance(View.GONE);
            }
            else if(spinner.getSelectedItemPosition() == Transportation.TRANSPORTATION_TYPE.CAR.ordinal() &&
                    transportation.getType() != Transportation.TRANSPORTATION_TYPE.CAR) {
                layout.addView(editRouteChild);
                setVisibilityOfHwyDistance(View.VISIBLE);
            }
            if(setupMode) {
                setupSpinners();
            }
            menuEditMode(true);
        }
        else {
            layout.removeView(editTransportationChild);
            if(layout.getChildCount() > 2) {
                layout.removeView(layout.getChildAt(layout.getChildCount() - 1));
            }
            transportationChild.setBackgroundResource(R.drawable.background_border_green);
            menuEditMode(false);
        }
    }

    private void setVisibilityOfHwyDistance(int visibility) {
        EditText editTextHwy = (EditText) findViewById(R.id.editText_hwyDistance);
        TextView textViewHwy = (TextView) findViewById(R.id.textView_hwy_distance);
        editTextHwy.setVisibility(visibility);
        textViewHwy.setVisibility(visibility);
    }

    private void setupSpinners() {
        setupModeSpinner();
        setupCarSpinners();
        setupMode = false;
    }

    private void setupCarSpinners() {
        spinnerMake = (Spinner) findViewById(R.id.spinner_make);
        spinnerModel = (Spinner) findViewById(R.id.spinner_model);
        spinnerYear = (Spinner) findViewById(R.id.spinner_year);

        if(spinnerMake != null && spinnerModel != null && spinnerYear != null) {
            CarStorage carStorage = CarStorage.getInstance();
            ArrayList<String> makeList = carStorage.getCarMakeList();

            setupMakeSpinner();
            populateSpinner(spinnerMake, makeList);
            setupModelSpinner();
            if (transportation.getType() == Transportation.TRANSPORTATION_TYPE.CAR) {
                setupCar = true;
                setupCar();
            }
        }
    }

    public void setupCar() {
        CarStorage carStorage = CarStorage.getInstance();
        ArrayList<String> makeList = carStorage.getCarMakeList();
        Car car = (Car) transportation;
        String make = car.getMake();
        int index = makeList.indexOf(make);
        spinnerMake.setSelection(index);
        ArrayList<String> modelList = carStorage.getCarModelsOfMake(make);
        populateSpinner(spinnerModel, modelList);
        String model = car.getModel();
        index = modelList.indexOf(model);
        populateSpinner(spinnerModel, modelList);
        spinnerModel.setSelection(index);
        ArrayList<String> yearList = carStorage.getCarYearsOfModels(model);
        String year = String.valueOf(car.getYear());
        index = yearList.indexOf(year);
        populateSpinner(spinnerYear, yearList);
        spinnerYear.setSelection(index);
    }


    private void setupModelSpinner() {
        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!setupCar) {
                    String model = spinnerModel.getSelectedItem().toString();
                    ArrayList<String> yearList = CarStorage.getInstance().getCarYearsOfModels(model);
                    populateSpinner(spinnerYear, yearList);
                }
                else {
                    setupCar = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupMakeSpinner() {
        spinnerMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!setupCar) {
                    String make = spinnerMake.getSelectedItem().toString();
                    ArrayList<String> modelList = CarStorage.getInstance().getCarModelsOfMake(make);
                    populateSpinner(spinnerModel, modelList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void refreshEditRoute() {
        if(isSelectedRoute) {
            layout.addView(editRouteChild);
            EditText editTextHwy = (EditText) findViewById(R.id.editText_hwyDistance);
            TextView textViewHwy = (TextView) findViewById(R.id.textView_hwy_distance);

            if(transportation.getType() == Transportation.TRANSPORTATION_TYPE.CAR) {
                editTextHwy.setVisibility(View.VISIBLE);
                textViewHwy.setVisibility(View.VISIBLE);
            }
            else {
                editTextHwy.setVisibility(View.GONE);
                textViewHwy.setVisibility(View.GONE);
            }
            routeChild.setBackgroundResource(R.drawable.background_border_blue);
            menuEditMode(true);
        }
        else {
            layout.removeView(editRouteChild);
            routeChild.setBackgroundResource(R.drawable.background_border_green);
            menuEditMode(false);
        }
    }

    private void refreshRoute() {
        route = model.getRouteOfJourneyAt(index);
        TextView textViewDistance = (TextView) findViewById(R.id.textView_distance);
        textViewDistance.setText(String.valueOf(route.getTotalDistance()) + "KM");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(routeChild.getLayoutParams());
        params.setMargins(0, 10, 0, 10);
        routeChild.setLayoutParams(params);
    }

    private void refreshTransportation() {
        transportation = model.getTransportationOfJourneyAt(index);
        ImageView imageView = (ImageView) findViewById(R.id.imageView_list_transportation_icon);
        TextView textViewType = (TextView) findViewById(R.id.textView_type);
        textViewType.setText(Transportation.TYPE[transportation.getType().ordinal()]);

        TextView textViewName = (TextView) findViewById(R.id.textView_name);
        TextView textViewMake = (TextView) findViewById(R.id.textView_make);
        TextView textViewModel = (TextView) findViewById(R.id.textView_model);
        TextView textViewYear = (TextView) findViewById(R.id.textView_year);
        if(transportation.getType() == Transportation.TRANSPORTATION_TYPE.CAR) {
            Car car = (Car) transportation;
            textViewName.setText(car.getNickname());
            textViewModel.setText(car.getModel());
            textViewMake.setText(car.getMake());
            textViewYear.setText(String.valueOf(car.getYear()));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(transportationChild.getLayoutParams());
        params.setMargins(0, 10, 0, 10);
        transportationChild.setLayoutParams(params);

    }
}
