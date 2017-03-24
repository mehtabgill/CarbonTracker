package ca.cmpt276.carbontracker.UI;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.carbontracker.Model.ActivityConstants;
import ca.cmpt276.carbontracker.Model.SingletonModel;

/**
 * Responsible for displaying pop up window with tips
 */



public class TipsActivity extends AppCompatActivity {

    final double PERCENT_WIDTH = 0.8;
    final double PERCENT_HEIGHT = 0.5;

    //singleton model
    private SingletonModel model = SingletonModel.getInstance();

    static int carTipsCounter = 0; //determines the priority
    static int notCarTipsCounter = 0; //determines priority for 2nd type of tips
    static int generalTipsCounter = 0; //for general tips



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_layout);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int)(w*PERCENT_WIDTH), (int)(h*PERCENT_HEIGHT));



        int callingActivity = getIntent().getIntExtra("callingActivity", 0);

        Button next = (Button) findViewById(R.id.next);

        switch(callingActivity){
            case ActivityConstants.ACTIVITY_SELECT_ROUTE:
                carTips();
                //cycle tips on button click
                next.setOnClickListener(new View.OnClickListener(){
                   public void onClick(View v){
                       carTips();
                   }
                });
                break;
            case ActivityConstants.ACTIVITY_SELECT_TRANSPORTATION_MODE:
                notCarTips();
                //cycle tips on button click
                next.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        notCarTips();
                    }
                });
                break;
            case ActivityConstants.ACTIVITY_VIEW_FOOTPRINT:
                generalTips();
                //cycle tips on button click
                next.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        generalTips();
                    }
                });
                break;
        }


    }

    public void carTips(){
        //tip array for add_car_journey
        Resources res = getResources();
        final String[] carTips = res.getStringArray(R.array.add_car_journey);

        int latest = model.getJourneysCarbonEmissionList().size() - 1;
        String s = model.getJourneysCarbonEmissionList().get(latest);

        TextView tip = (TextView) findViewById(R.id.tip);

        tip.setText(carTips[carTipsCounter]+s);

        carTipsCounter++;
        if(carTipsCounter == 8)
            carTipsCounter = 0;

    }

    public void notCarTips(){

        //tip array for add_not_car
        Resources res = getResources();
        final String[] notCarTips = res.getStringArray(R.array.add_not_car);

        int latest = model.getJourneysCarbonEmissionList().size() - 1;
        String s = model.getJourneysCarbonEmissionList().get(latest);
        TextView tip = (TextView) findViewById(R.id.tip);

        if(notCarTipsCounter<4) {

            tip.setText(notCarTips[notCarTipsCounter]+s);
        }
        else if (notCarTipsCounter==4){ //walks (need searchByDate for JourneyCollection)
            s = model.getWalks()+"";
            tip.setText(notCarTips[notCarTipsCounter]+s);
        }
        else if (notCarTipsCounter==5){ //bikes
            s = model.getBike()+"";
            tip.setText(notCarTips[notCarTipsCounter]+s);
        }
        else if (notCarTipsCounter==6){ //bus
            s = model.getBus()+"";
            tip.setText(notCarTips[notCarTipsCounter]+s);
        }
        else if (notCarTipsCounter==7){ //skytrain
            s = model.getSkytrain()+"";
            tip.setText(notCarTips[notCarTipsCounter]+s);
        }
        notCarTipsCounter++;
        if(notCarTipsCounter == 8)
            notCarTipsCounter = 0;

    }

    //will appear when view carbon footprint is clicked for now
    public void generalTips(){

        //tip array for add_not_car
        Resources res = getResources();
        final String[] generalTips = res.getStringArray(R.array.general);

        float total = 0; //total carbon emitted

        for(String i: model.getJourneysCarbonEmissionList())
        {
            float temp = Float.parseFloat(i);
            total+=temp;
        }

        String s = total+"";
        TextView tip = (TextView) findViewById(R.id.tip);

        if(generalTipsCounter<4) {

            tip.setText(generalTips[generalTipsCounter]+s);
        }
        else if (generalTipsCounter==4){ //walks (need searchByDate for JourneyCollection)
            s = model.getWalks()+"";
            tip.setText(generalTips[generalTipsCounter]+s);
        }
        else if (generalTipsCounter==5){ //bikes
            s = model.getBike()+"";
            tip.setText(generalTips[generalTipsCounter]+s);
        }
        else if (generalTipsCounter==6){ //bus
            s = model.getBus()+"";
            tip.setText(generalTips[generalTipsCounter]+s);
        }
        else if (generalTipsCounter==7){ //skytrain
            s = model.getSkytrain()+"";
            tip.setText(generalTips[generalTipsCounter]+s);
        }
        generalTipsCounter++;
        if(generalTipsCounter == 8)
            generalTipsCounter = 0;
    }


}
