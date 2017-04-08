package ca.cmpt276.carbontracker.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.cmpt276.carbontracker.Model.ActivityConstants;
import ca.cmpt276.carbontracker.Model.GraphDataRetriever;

import ca.cmpt276.carbontracker.Model.SingletonModel;

/*
 * UI Class for displaying carbon footprint activity, either by table or by pie pieChart
 */

public class ViewCarbonFootprintActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private PieChart pieChart;
    private LineChart lineChart;
    private Menu menu;
    private Button switchButton;

    //Column for: Emission type, name of transportation/bill, carbon emission value
    private final int COL_NUM = 3;
    //row = amount of emission + 1 row for header
    private int ROW_NUM;
    private int ARRAY_SIZE;
    private int arrayIndex;

    private boolean individualLineChart = true;
    private boolean pieChartByMode = true;
    ArrayList<String> emissionTypeList;
    ArrayList<String> emissionNameList;
    ArrayList<Float> carbonEmissionList;
    Calendar date = Calendar.getInstance();
    GraphDataRetriever graphData = GraphDataRetriever.getInstance();
    GraphDataRetriever.GRAPH_MODE graphMode;

    public static String ELECTRICITY;
    public static String GAS;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        this.menu = menu;
        setupMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (graphMode){
            case DAY:
                if(id == R.id.pie_chart_menu_item){
                    tableLayout.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                    switchButton.setVisibility(View.VISIBLE);

                }
                else{
                    tableLayout.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.INVISIBLE);
                    switchButton.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                if(id == R.id.pie_chart_menu_item){
                    lineChart.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                }
                else{
                    lineChart.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_carbon_footprint);
        getExtraFromIntent();
        ELECTRICITY = getString(R.string.electricity_bill);
        GAS = getString(R.string.gas_bill);
        graphData.setUpGraphData(graphMode, date);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        Intent tipsWindow = new Intent(ViewCarbonFootprintActivity.this, TipsActivity.class);
        tipsWindow.putExtra("callingActivity", ActivityConstants.ACTIVITY_VIEW_FOOTPRINT);
        startActivity(tipsWindow);
        switch (graphMode){
            case DAY:
                ROW_NUM = graphData.getEmissionArrayListSize() + 1;
                ARRAY_SIZE = graphData.getEmissionArrayListSize();
                emissionTypeList = graphData.getEmissionTypeList_Day();
                emissionNameList = graphData.getEmissionNameList_Day();
                carbonEmissionList = graphData.getEmissionValueList_Day();
                pieChart.setVisibility(View.INVISIBLE);
                setupLayout_Day();
                break;
            case MONTH:
                pieChart.setVisibility(View.INVISIBLE);
                setupLayout_MultipleDays();
                break;
            case YEAR:
                pieChart.setVisibility(View.INVISIBLE);
                setupLayout_MultipleDays();
                break;
        }
        setupSwitchButton();

    }

    private void setupMenu() {
        switch (graphMode){
            case DAY:
                menu.findItem(R.id.table_item).setVisible(true);
                menu.findItem(R.id.line_chart_menu_item).setVisible(false);
                break;
            default:
                menu.findItem(R.id.table_item).setVisible(false);
                menu.findItem(R.id.line_chart_menu_item).setVisible(true);
        }
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
        }
        setupPieChart();
    }


    private void setupLayout_MultipleDays(){
        setupPieChart();
        setupLineChart();
    }


    //For day only right now
    private void setupPieChart(){
        //populating a list of entries into the pie graph

        PieDataSet dataset;
        if(pieChartByMode){
            dataset = getPieDataSet_Mode();
        }
        else{
            dataset = getPieDataSet_Route();
        }

        //change the color of the pie pieChart
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //to keep data for the pie pieChart
        PieData data = new PieData(dataset);

        //get the pieChart from the layout
        pieChart.setData(data);

        pieChart.setDrawEntryLabels(false);

        //make pieChart animate
        pieChart.animateY(1000);

        pieChart.invalidate();

        //pieChart.setVisibility(View.INVISIBLE);
    }

    private PieDataSet getPieDataSet_Mode() {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet dataset;
        switch (graphMode){
            case DAY:
                for(int arrayIndex = 0; arrayIndex < ARRAY_SIZE; arrayIndex++)
                {
                    pieEntries.add(new PieEntry(carbonEmissionList.get(arrayIndex), emissionNameList.get(arrayIndex)));
                }
                break;
            default:
                ArrayList<Float> emissionValueByMode = graphData.getEmissionValueByMode();
                pieEntries.add(new PieEntry(emissionValueByMode.get(0), getString(R.string.Car)));
                pieEntries.add(new PieEntry(emissionValueByMode.get(1), getString(R.string.Bus)));
                pieEntries.add(new PieEntry(emissionValueByMode.get(2), getString(R.string.Skytrain)));
                pieEntries.add(new PieEntry(emissionValueByMode.get(3), getString(R.string.electricity_bill)));
                pieEntries.add(new PieEntry(emissionValueByMode.get(4), getString(R.string.gas_bill)));
                break;
        }
        dataset = new PieDataSet(pieEntries, "C02 Emission By Mode");
        return dataset;
    }
    private PieDataSet getPieDataSet_Route() {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet dataset;
        ArrayList<Float> emissionValueByRoute = graphData.getEmissionValueByRoute();
        ArrayList<String> emissionNameList_Route = graphData.getEmissionNameList_Route();
        for(int i = 0; i < emissionValueByRoute.size(); i++){
            pieEntries.add(new PieEntry(emissionValueByRoute.get(i), emissionNameList_Route.get(i)));
        }
        dataset = new PieDataSet(pieEntries, "C02 Emission By Route");
        return dataset;
    }

    //Setup line chart for month
    private void setupLineChart(){
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setVisibility(View.VISIBLE);
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        if(individualLineChart){
            lineDataSets = getLineDataSetsIndividual();
        }
        else{
            lineDataSets = getLineDataSetsTotal();
        }
        lineChart.setData(new LineData(lineDataSets));
        lineChart.invalidate();
    }

    private ArrayList<ILineDataSet> getLineDataSetsIndividual() {
        ArrayList<Entry> carEntries = new ArrayList<>();
        ArrayList<Entry> busEntries = new ArrayList<>();
        ArrayList<Entry> skytrainEntries = new ArrayList<>();
        ArrayList<Entry> electricityBillEntries = new ArrayList<>();
        ArrayList<Entry> gasBillEntries = new ArrayList<>();

        final ArrayList<String> dateList = graphData.getDateList();
        ArrayList<Float> carEmissionList = graphData.getCarEmissionValue();
        ArrayList<Float> busEmissionList = graphData.getBusEmissionValue();
        ArrayList<Float> skytrainEmissionList = graphData.getSkytrainEmissionValue();
        ArrayList<Float> electricityBillEmissionList = graphData.getElectricityBillEmissionValue();
        ArrayList<Float> gasBillEmissionList = graphData.getGasBillEmissionValue();
        for(int i = 0; i < graphData.getNumberOfEntries(); i++){
            carEntries.add(new Entry(i, carEmissionList.get(i)));
            busEntries.add(new Entry(i, busEmissionList.get(i)));
            skytrainEntries.add(new Entry(i, skytrainEmissionList.get(i)));
            electricityBillEntries.add(new Entry(i, electricityBillEmissionList.get(i)));
            gasBillEntries.add(new Entry(i, gasBillEmissionList.get(i)));
        }

        LineDataSet carLineDataSet = new LineDataSet(carEntries, getString(R.string.Car));
        LineDataSet busLineDataSet = new LineDataSet(busEntries, getString(R.string.Bus));
        LineDataSet skytrainLineDataSet = new LineDataSet(skytrainEntries, getString(R.string.Skytrain));
        LineDataSet electricityLineDataSet = new LineDataSet(electricityBillEntries, getString(R.string.electricity_bill));
        LineDataSet gasLineDataSet = new LineDataSet(gasBillEntries, getString(R.string.gas_bill));


        carLineDataSet.setDrawCircles(false);
        carLineDataSet.setColor(Color.RED);

        busLineDataSet.setDrawCircles(false);
        busLineDataSet.setColor(Color.GREEN);

        skytrainLineDataSet.setDrawCircles(false);
        skytrainLineDataSet.setColor(Color.BLUE);

        electricityLineDataSet.setDrawCircles(false);
        electricityLineDataSet.setColor(Color.YELLOW);

        gasLineDataSet.setDrawCircles(false);
        gasLineDataSet.setColor(Color.MAGENTA);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
        lineDataSets.add(carLineDataSet);
        lineDataSets.add(busLineDataSet);
        lineDataSets.add(skytrainLineDataSet);
        lineDataSets.add(electricityLineDataSet);
        lineDataSets.add(gasLineDataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateList.get((int) value);
            }
        });
        return lineDataSets;
    }

    private ArrayList<ILineDataSet> getLineDataSetsTotal(){
        float individualTarget = 0;
        float individualActual = 0;
        if(graphMode.equals(GraphDataRetriever.GRAPH_MODE.MONTH)){
            individualTarget = graphData.getIndividualTarget();
            individualActual = graphData.getIndividualActual();
        }
        else{
            individualTarget = graphData.getIndividualTarget() * 30;
            individualActual = graphData.getIndividualActual() * 30;
        }
        ArrayList<Entry> totalEntries = new ArrayList<>();
        ArrayList<Entry> targetEntries = new ArrayList<>();
        ArrayList<Entry> actualEntries = new ArrayList<>();

        final ArrayList<String> dateList = graphData.getDateList();
        ArrayList<Float> totalArrayList = graphData.getTotalEmissionValue();

        for(int i = 0; i < graphData.getNumberOfEntries(); i++){
            totalEntries.add(new Entry(i, totalArrayList.get(i)));
            targetEntries.add(new Entry(i, individualTarget));
            actualEntries.add(new Entry(i, individualActual));
        }

        LineDataSet totalLineDataSet = new LineDataSet(totalEntries, getString(R.string.Total_Value));
        totalLineDataSet.setColor(Color.BLUE);
        LineDataSet targetLineDataSet = new LineDataSet(targetEntries, getString(R.string.Target_Value));
        targetLineDataSet.setColor(Color.GREEN);
        LineDataSet actualLineDataSet = new LineDataSet(actualEntries, getString(R.string.Actual_Value));
        actualLineDataSet.setColor(Color.RED);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(totalLineDataSet);
        lineDataSets.add(actualLineDataSet);
        lineDataSets.add(targetLineDataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateList.get((int) value);
            }
        });

        return lineDataSets;
    }

    private void setupSwitchButton() {
        switchButton = (Button) findViewById(R.id.switch_button);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (graphMode){
                    case DAY:
                        pieChartByMode = !pieChartByMode;
                        setupPieChart();
                        break;
                    default:
                        if(lineChart.getVisibility() == View.VISIBLE){
                            individualLineChart = !individualLineChart;
                            setupLineChart();
                            break;
                        }
                        else if(pieChart.getVisibility() == View.VISIBLE){
                            pieChartByMode = !pieChartByMode;
                            setupPieChart();
                            break;
                        }
                }
            }
        });
        if(graphMode.equals(GraphDataRetriever.GRAPH_MODE.DAY)){
            switchButton.setVisibility(View.INVISIBLE);
        }
    }

    private void getExtraFromIntent(){
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            String selected_mode = extra.getString(MainMenuActivity.MODE_KEY);
            if (selected_mode.equals("1 DAY")){
                graphMode = GraphDataRetriever.GRAPH_MODE.DAY;;
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
}
