package ca.cmpt276.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ViewCarbonFootprintActivity extends AppCompatActivity {

    //arrays to be displayed
    float rainfall[] = {98.8f, 123.8f, 161.6f, 24.2f, 52f, 58.2f, 35.4f, 13.8f, 78.4f, 203.4f, 240.2f, 159.7f};
    String monthNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_carbon_footprint);


        //function that sets up the piechart on creatation of activity
        setupPieChart();
    }

    private void setupPieChart(){
        //populating a list of entires into the pie graph
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i=0; i<rainfall.length; i++)
        {
            pieEntries.add(new PieEntry(rainfall[i], monthNames[i]));
        }

        //parameters are the array of entries followed by name of graph
        PieDataSet dataset = new PieDataSet(pieEntries, "Rainfall for Vancouver");

        //change the color of the pie chart
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //to keep data for the pie chart
        PieData data = new PieData(dataset);

        //get the chart from the layout
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);

        //make chart animate
        chart.animateY(1000);


        chart.invalidate();




    }
}
