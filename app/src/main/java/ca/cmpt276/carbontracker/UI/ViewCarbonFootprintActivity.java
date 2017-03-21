package ca.cmpt276.carbontracker.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI Class for displaying carbon footprint activity, either by table or by pie chart
 */
public class ViewCarbonFootprintActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private PieChart chart;
    SingletonModel model = SingletonModel.getInstance();

    //Column for: date of trip; distance; vehicle name; carbon emitted;
    private final int COL_NUM = 4;

    //first row is for header
    private final int ROW_NUM = model.getJourneyCollectionSize() + 1;
    private final int ARRAY_SIZE = model.getJourneyCollectionSize();
    private int arrayIndex;

    ArrayList<String> journeyDateList = model.getJourneysDates();
    ArrayList<String> totalDistanceList = model.getJourneysTotalDistanceList();
    ArrayList<String> carNameList = model.getJourneysCarList();
    ArrayList<String> carbonEmissionList = model.getJourneysCarbonEmissionList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_carbon_footprint);
        setupLayout();
    }

    private void setupLayout() {
        tableLayout = (TableLayout) findViewById(R.id.table_Layout);
        for (int row = 0; row < ROW_NUM; row++) {
            arrayIndex = row - 1;
            TableRow currentRow = new TableRow(this);
            currentRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            tableLayout.addView(currentRow);
            for (int col = 0; col < COL_NUM; col++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        1.0f));
                if (row == 0) {
                    switch (col) {
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
                } else {
                    switch (col) {
                        case 0:
                            textView.setText(journeyDateList.get(arrayIndex));
                            break;
                        case 1:
                            textView.setText(totalDistanceList.get(arrayIndex));
                            break;
                        case 2:
                            textView.setText(carNameList.get(arrayIndex));
                            break;
                        case 3:
                            textView.setText(carbonEmissionList.get(arrayIndex));
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
                    if (tableLayout.getVisibility() == View.VISIBLE) {
                        tableLayout.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                        setupPieChart();
                    } else {
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

        for(int arrayIndex = 0; arrayIndex < ARRAY_SIZE; arrayIndex++)
        {
            //Instead of testInt, put carbon emission, and dateString is journey date/name
            pieEntries.add(new PieEntry(Float.parseFloat(carbonEmissionList.get(arrayIndex)), journeyDateList.get(arrayIndex)));
        }

        //parameters are the array of entries followed by name of graph
        PieDataSet dataset = new PieDataSet(pieEntries, "Carbon Emission Chart");

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
