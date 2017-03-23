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
    private static SingletonModel model = SingletonModel.getInstance();
    private GraphDataRetriever(){};
    public enum GRAPH_MODE{DAY, MONTH, YEAR};
    private static Calendar date;
    private static ArrayList<Emission> emissionArrayList = new ArrayList<>();
    private static ArrayList<String> emissionTypeList = new ArrayList<>();
    private static ArrayList<String> emissionNameList = new ArrayList<>();
    private static ArrayList<Float> carbonEmissionValueList = new ArrayList<>();

    public static void setUpGraphData(GRAPH_MODE mode, Calendar date){
        if( (!emissionArrayList.isEmpty()) ||
                (!emissionTypeList.isEmpty()) ||
                (!emissionNameList.isEmpty()) ||
                (!carbonEmissionValueList.isEmpty())){
            resetCurrentCollection();
        }
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
                    emissionTypeList.add(emission.getClass().getSimpleName());
                }
                break;
        }
    }

    public static ArrayList<String> getEmissionTypeList(){
        return emissionTypeList;
    }

    public static ArrayList<String> getEmissionNameList(){
        return emissionNameList;
    }

    public static ArrayList<Float> getCarbonEmissionValueList(){
        return carbonEmissionValueList;
    }

    public static int getEmissionArrayListSize(){
        return emissionArrayList.size();
    }

    public static void resetCurrentCollection(){
        ArrayList<Emission> emissionArrayList = new ArrayList<>();
        ArrayList<String> emissionTypeList = new ArrayList<>();
        ArrayList<String> emissionNameList = new ArrayList<>();
        ArrayList<Float> carbonEmissionValueList = new ArrayList<>();
    }
}
