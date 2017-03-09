package ca.cmpt276.carbontracker.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.carbontracker.R;

/*
 * Class for displaying carbon footprint activity
 */
public class ViewCarbonFootprintActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private TableLayout tableLayout;
    private PieChart chart;

    //Column for: date of trip; distance; vehicle name; carbon emitted;
    private int colNum = 4;

    //test with 3 for now, updated when journey number increase
    //first row is for header
    private int rowNum = 4;

    //substitute data for journey data
    ArrayList<String> dateString = new ArrayList<String>();
    ArrayList<Integer> testInt = new ArrayList<Integer>();
    ArrayList<Integer> testInt2 = new ArrayList<Integer>();
    ArrayList<Integer> testInt3 = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup a table
        setContentView(R.layout.activity_view_carbon_footprint);
        dateString.add("11/12/12");
        dateString.add("11/12/13");
        dateString.add("11/12/15");
        dateString.add("11/12/17");
        for (int i = 0; i < rowNum; i++) {
            testInt.add(17);
            testInt2.add(4);
            testInt3.add(10);
        }

        mainLayout = (RelativeLayout) findViewById(R.id.activity_view_carbon_footprint);
        tableLayout = (TableLayout) findViewById(R.id.table_Layout);
        for (int row = 0; row < rowNum; row++) {
            TableRow currentRow = new TableRow(this);
           currentRow.setLayoutParams(new TableLayout.LayoutParams(
                   TableLayout.LayoutParams.MATCH_PARENT,
                   TableLayout.LayoutParams.WRAP_CONTENT,
                   1.0f
                   ));
            tableLayout.addView(currentRow);
            for(int col = 0; col < colNum; col++){
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        1.0f));
                if(row == 0){
                    switch (col){
                        case 0:
                            textView.setText(getString(R.string.journey_date_header));
                            break;
                        case 1:
                            textView.setText(getString(R.string.journey_distance_header));
                            break;
                        case 2:
                            textView.setText(getString(R.string.journey_car_name_header));
                            break;
                        case 3:
                            textView.setText(getString(R.string.journey_carbon_emission_header));
                            break;
                    }
                    currentRow.addView(textView);
                }
                else{
                    switch (col){
                        case 0:
                            //should be date of journey
                            textView.setText(dateString.get(row));
                            break;
                        case 1:
                            //should be getJourney(row).getRoute.getDistance() or something like that
                            textView.setText(Integer.toString(testInt.get(row)));
                            break;
                        case 2:
                            //should be getJourney(row).getCar.getNickname() or getJourney(col).getCar.getDescription()
                            textView.setText(Integer.toString(testInt2.get(row)));
                            break;
                        case 3:
                            //should be getJourney(row).getCarbonEmission
                            textView.setText(Integer.toString(testInt3.get(row)));
                            break;
                    }
                    currentRow.addView(textView);
                }
            }
            final Button pieChartButton = (Button) findViewById(R.id.switch_button);
            chart = (PieChart) findViewById(R.id.chart);
            pieChartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tableLayout.getVisibility() == View.VISIBLE){
                        tableLayout.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                        setupPieChart();
                    }
                    else{
                        tableLayout.setVisibility(View.VISIBLE);
                        chart.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    private void setupPieChart(){
        //populating a list of entries into the pie graph
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i=0; i< rowNum; i++)
        {
            //Instead of testInt, put carbon emission, and dateString is journey date/name
            pieEntries.add(new PieEntry(testInt.get(i), dateString.get(i)));
        }

        //parameters are the array of entries followed by name of graph
        PieDataSet dataset = new PieDataSet(pieEntries, "Test");

        //change the color of the pie chart
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //to keep data for the pie chart
        PieData data = new PieData(dataset);

        //get the chart from the layout
        chart.setData(data);

        //make chart animate
        chart.animateY(1000);

        chart.invalidate();

    }
}
