package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.List;

import ca.cmpt276.carbontracker.Model.ActivityConstants;
import ca.cmpt276.carbontracker.Model.GraphDataRetriever;
import ca.cmpt276.carbontracker.Model.ActivityConstants;
import ca.cmpt276.carbontracker.Model.SingletonModel;

import static ca.cmpt276.carbontracker.Model.GraphDataRetriever.GRAPH_MODE.DAY;

/*
 * UI Class for displaying carbon footprint activity, either by table or by pie chart
 */
public class ViewCarbonFootprintActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private PieChart chart;
    private SingletonModel model = SingletonModel.getInstance();

    //Column for: Emission type, name of transportation/bill, carbon emission value
    private final int COL_NUM = 3;
    //row = amount of emission + 1 row for header
    private int ROW_NUM;
    private int ARRAY_SIZE;
    private int arrayIndex;

    ArrayList<String> journeyDateList;
    ArrayList<String> emissionTypeList;
    ArrayList<String> emissionNameList;
    ArrayList<Float> carbonEmissionList;
    Calendar date = Calendar.getInstance();
    GraphDataRetriever.GRAPH_MODE graphMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtraFromIntent();
        GraphDataRetriever.setUpGraphData(graphMode, date);
        setContentView(R.layout.activity_view_carbon_footprint);
        setupLayout_Day();
        Intent tipsWindow = new Intent(ViewCarbonFootprintActivity.this, TipsActivity.class);
        tipsWindow.putExtra("callingActivity", ActivityConstants.ACTIVITY_VIEW_FOOTPRINT);
        startActivity(tipsWindow);
        ROW_NUM = GraphDataRetriever.getEmissionArrayListSize() + 1;
        ARRAY_SIZE = GraphDataRetriever.getEmissionArrayListSize();
        emissionTypeList = GraphDataRetriever.getEmissionTypeList_Day();
        emissionNameList = GraphDataRetriever.getEmissionNameList_Day();
        carbonEmissionList = GraphDataRetriever.getEmissionValueList_Day();
        setupLayout_Day();
        setupPieChart();
    }

    private void setupLayout_Day() {
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
                            textView.setText(getString(R.string.emission_type_header));
                            break;
                        case 1:
                            textView.setText(getString(R.string.emission_name_header));
                            break;
                        case 2:
                            textView.setText(getString(R.string.emission_value_header));
                            break;
                    }
                    currentRow.addView(textView);
                } else {
                    switch (col) {
                        case 0:
                            textView.setText(emissionTypeList.get(arrayIndex));
                            break;
                        case 1:
                            textView.setText(emissionNameList.get(arrayIndex));
                            break;
                        case 2:
                            String formattedValue = String.format(getString(R.string.emission_value_formatted_string), carbonEmissionList.get(arrayIndex));
                            textView.setText(formattedValue);
                            break;
                    }
                    currentRow.addView(textView);
                }
            }

            final Button pieChartButton = (Button) findViewById(R.id.switch_button);
            chart = (PieChart) findViewById(R.id.pieChart);
            pieChartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tableLayout.getVisibility() == View.VISIBLE) {
                        tableLayout.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                    } else {
                        tableLayout.setVisibility(View.VISIBLE);
                        chart.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    private void setupLayout_Month(){
    }

    private void getExtraFromIntent(){
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            String selected_mode = extra.getString(MainMenuActivity.MODE_KEY);
            if (selected_mode.equals("1 DAY")){
                graphMode = DAY;
            }
            else if(selected_mode.equals("LAST 28 DAY")){
                graphMode = GraphDataRetriever.GRAPH_MODE.MONTH;
            }
            else{
                graphMode = GraphDataRetriever.GRAPH_MODE.YEAR;
            }
            int selected_date = extra.getInt(MainMenuActivity.SELECTED_DATE_KEY);
            int selected_month = extra.getInt(MainMenuActivity.SELECTED_MONTH_KEY);
            int selected_year = extra.getInt(MainMenuActivity.SELECTED_YEAR_KEY);
            date.set(selected_year, selected_month, selected_date);
        }
    }

    private void setupPieChart(){
        //populating a list of entries into the pie graph
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int arrayIndex = 0; arrayIndex < ARRAY_SIZE; arrayIndex++)
        {
            pieEntries.add(new PieEntry(carbonEmissionList.get(arrayIndex), emissionNameList.get(arrayIndex)));
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

        chart.setVisibility(View.INVISIBLE);

    }
}
