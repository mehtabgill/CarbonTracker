package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.SingletonModel;


public class ViewJourneyActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT_JOURNEY = 101;
    private ArrayList<String[]> journeyData;

    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    public static int COL_IMAGE = 0;
    public static int COL_TRANSPORTATION = 1;
    public static int COL_DISTANCE = 2;
    public static int COL_DATE = 3;
    public static int COL_CO2 = 4;

    public static int NUM_COLUMNS = 5;

    SingletonModel model = SingletonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journey);

        journeyData = model.getJourneyData();
        populateJourneyList();
        registerClickCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateJourneyList();
    }

    private void populateJourneyList() {
        journeyData = model.getJourneyData();
        ArrayAdapter<String[]> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView_journey);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listView_journey);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewJourneyActivity.this, EditJourneyActivity.class);
                intent.putExtra(EXTRA_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_JOURNEY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateJourneyList();
    }

    private class MyListAdapter extends ArrayAdapter<String[]> {
        public MyListAdapter() {

            super(ViewJourneyActivity.this, R.layout.list_journey, journeyData);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.list_journey, parent, false);
            }
            if(journeyData.size() > 0) {
                String[] journey = journeyData.get(position);

                ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_icon);
                //imageView.setImageResource();

                TextView textViewTransportation = (TextView) itemView.findViewById(R.id.text_transportation);
                textViewTransportation.setText(journey[COL_TRANSPORTATION]);

                TextView textViewDistance = (TextView) itemView.findViewById(R.id.text_distance);
                textViewDistance.setText(journey[COL_DISTANCE]);

                TextView textViewDate = (TextView) itemView.findViewById(R.id.text_date);
                textViewDate.setText(journey[COL_DATE]);

                TextView textViewCO2 = (TextView) itemView.findViewById(R.id.text_CO2);
                textViewCO2.setText(journey[COL_CO2]);
            }
            return itemView;
        }
    }
}
