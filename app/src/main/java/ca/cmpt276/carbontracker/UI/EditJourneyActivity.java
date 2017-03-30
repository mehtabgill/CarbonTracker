package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.carbontracker.Model.Car;
import ca.cmpt276.carbontracker.Model.Route;
import ca.cmpt276.carbontracker.Model.SingletonModel;
import ca.cmpt276.carbontracker.Model.Transportation;


public class EditJourneyActivity extends AppCompatActivity {

    SingletonModel model = SingletonModel.getInstance();

    View transportationChild;
    View routeChild;
    Transportation transportation;
    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journey);

        Intent intent = getIntent();
        int index = intent.getIntExtra(ViewJourneyActivity.EXTRA_POSITION, -1);

        transportation = model.getTransportationOfJourneyAt(index);
        route = model.getRouteOfJourneyAt(index);

        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_edit_journey);
        transportationChild = getLayoutInflater().inflate(R.layout.list_transportation, null);

        layout.addView(transportationChild);
        routeChild = getLayoutInflater().inflate(R.layout.list_route, null);
        layout.addView(routeChild);


        refreshTransportation();
        refreshRoute();
        registerClickCallback();
    }

    private void registerClickCallback() {
        routeChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked on Route", Toast.LENGTH_SHORT).show();
            }
        });
        transportationChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked on transportation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshRoute() {
        TextView textViewDistance = (TextView) findViewById(R.id.textView_distance);
        textViewDistance.setText(String.valueOf(route.getTotalDistance()) + "KM");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(routeChild.getLayoutParams());
        params.setMargins(0, 10, 0, 10);
        routeChild.setLayoutParams(params);
    }

    private void refreshTransportation() {
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
