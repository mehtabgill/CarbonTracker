package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Static class that read data from Model and return data accordingly
 * For one day: Entry is activity - Journey or Utilities(electricity/gas)
 * For one month: Same as one day
 * One year: Entry is weeks
 */

public final class GraphDataRetriever {
    private SingletonModel model = SingletonModel.getInstance();
    private GraphDataRetriever(){};
    public enum GRAPH_MODE{DAY, MONTH, YEAR};
    private static Calendar date;
    private static ArrayList<Emission> emissionArrayList = new ArrayList<>();
    private static ArrayList<String> emissionTypeList = new ArrayList<>();
    private static ArrayList<String> emissionNameList = new ArrayList<>();
    private static ArrayList<Float> carbonEmissionValueList = new ArrayList<>();

    public void setUpGraphData(GRAPH_MODE mode, Calendar date){
        
        switch (mode){
            case DAY:
                emissionArrayList = model.getEmissionListOnDay(date);
                for(Emission emission : emissionArrayList){
                    if(emission instanceof Journey){
                        emissionNameList.add( ((Journey) emission).getTransportationName());
                        carbonEmissionValueList.add(emission.getCarbonEmissionValue());
                    }
                    else{
                        if(emission instanceof Utilities){
                            emissionNameList.add(((Utilities) emission).getBillMode().toString());
                            carbonEmissionValueList.add(((Utilities) emission).getDailyAverageEmission());
                        }
                    }
                    emissionTypeList.add(emission.getClass().getName());
                }
                break;
        }
    }
}
