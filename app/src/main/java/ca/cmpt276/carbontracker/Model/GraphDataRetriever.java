package ca.cmpt276.carbontracker.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    //These are used for Day
    private static ArrayList<Emission> emissionArrayList = new ArrayList<>();
    private static ArrayList<String> emissionTypeList_Day = new ArrayList<>();
    private static ArrayList<String> emissionNameList_Day = new ArrayList<>();
    private static ArrayList<Float> emissionValueList_Day = new ArrayList<>();
    private static boolean dateInBill = false;

    //These are used for Month

    private static ArrayList<String> dateList_Month = new ArrayList<>();
    private static ArrayList<String> emissionTypeList_Month = new ArrayList<>();
    private static ArrayList<Float> carEmissionValueList_Month = new ArrayList<>();
    private static ArrayList<Float> skytrainEmissionValueList_Month = new ArrayList<>();
    private static ArrayList<Float> busEmissionValueList_Month = new ArrayList<>();
    private static ArrayList<Float> electricityBillEmissionValueList_Month = new ArrayList<>();
    private static ArrayList<Float> gasBillEmissionValueList_Month = new ArrayList<>();

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

    public static void setUpGraphData(GRAPH_MODE mode, Calendar date){
        sdf.setCalendar(new GregorianCalendar());
        switch (mode){
            case DAY:
                resetCurrentCollection(GRAPH_MODE.DAY);
                emissionArrayList = model.getEmissionListOnDay(date);
                for(Emission emission : emissionArrayList){
                    if(emission instanceof Journey){
                        emissionNameList_Day.add( ((Journey) emission).getTransportationName());
                        emissionValueList_Day.add(emission.getCarbonEmissionValue());
                    }
                    else{
                        if(emission instanceof Utilities){
                            dateInBill = true;
                            emissionNameList_Day.add(((Utilities) emission).getBill().toString());
                            emissionValueList_Day.add(((Utilities) emission).getDailyAverageEmission());
                        }
                    }
                    emissionTypeList_Day.add(emission.getClass().getSimpleName());
                }
                if(!dateInBill){
                    Utilities electricityBill = model.getRelativeUtilitiesValue(date, Utilities.BILL.ELECTRICITY);
                    emissionArrayList.add(electricityBill);
                    emissionTypeList_Day.add(Utilities.class.getSimpleName());
                    emissionNameList_Day.add(Utilities.BILL.ELECTRICITY.toString());
                    emissionValueList_Day.add(electricityBill.getDailyAverageEmission());

                    Utilities gasBill = model.getRelativeUtilitiesValue(date, Utilities.BILL.GAS);
                    emissionArrayList.add(gasBill);
                    emissionTypeList_Day.add(Utilities.class.getSimpleName());
                    emissionNameList_Day.add(Utilities.BILL.GAS.toString());
                    emissionValueList_Day.add(gasBill.getDailyAverageEmission());
                }
                break;

            case MONTH:
                resetCurrentCollection(GRAPH_MODE.MONTH);
                emissionTypeList_Month.add("Car");
                emissionTypeList_Month.add("Skytrain");
                emissionTypeList_Month.add("Bus");
                emissionTypeList_Month.add("Electricity Bill");
                emissionTypeList_Month.add("Gas Bill");
                final int DAY_BACKWARD = 28;
                Calendar currentDate = (Calendar) date.clone();
                currentDate.add(Calendar.DATE, -DAY_BACKWARD);
                Calendar endDate = (Calendar) date.clone();
                while(!currentDate.getTime().equals(endDate.getTime()) ){
                    float carValueByDay = 0;
                    float busValueByDay = 0;
                    float skytrainValueByDay = 0;
                    float elecBillValueByDay = 0;
                    float gasBillValueByDay = 0;
                    emissionArrayList = model.getEmissionListOnDay(currentDate);
                    boolean elecBillByDay = false;
                    boolean gasBillByDay = false;
                    for (Emission emission :emissionArrayList){
                        if(emissionArrayList.size() > 0){
                            if(emission instanceof Journey){
                                switch ( ((Journey) emission).getTransportation().getType()){
                                    case CAR:
                                        carValueByDay += emission.getCarbonEmissionValue();
                                        break;
                                    case SKYTRAIN:
                                        skytrainValueByDay += emission.getCarbonEmissionValue();
                                        break;
                                    case BUS:
                                        busValueByDay += emission.getCarbonEmissionValue();
                                        break;
                                }
                            }
                            else if(emission instanceof Utilities){
                                switch (((Utilities) emission).getBill()){
                                    case ELECTRICITY:
                                        elecBillByDay = true;
                                        elecBillValueByDay += ((Utilities) emission).getDailyAverageEmission();
                                        break;
                                    case GAS:
                                        gasBillByDay = true;
                                        gasBillValueByDay += ((Utilities) emission).getDailyAverageEmission();
                                        break;
                                }
                            }
                        }
                    }

                    if(!elecBillByDay){
                        Utilities electricityBill = model.getRelativeUtilitiesValue(date, Utilities.BILL.ELECTRICITY);
                        elecBillValueByDay += electricityBill.getDailyAverageEmission();
                    }
                    if(!gasBillByDay){
                        Utilities gasBill = model.getRelativeUtilitiesValue(date, Utilities.BILL.GAS);
                        gasBillValueByDay += gasBill.getDailyAverageEmission();
                    }

                    carEmissionValueList_Month.add(carValueByDay);
                    skytrainEmissionValueList_Month.add(skytrainValueByDay);
                    busEmissionValueList_Month.add(busValueByDay);
                    electricityBillEmissionValueList_Month.add(elecBillValueByDay);
                    gasBillEmissionValueList_Month.add(gasBillValueByDay);
                    dateList_Month.add(sdf.format(currentDate.getTime()));
                    currentDate.add(Calendar.DATE, 1);
                }
                break;
        }
    }


    public static ArrayList<String> getEmissionTypeList_Day(){
        return emissionTypeList_Day;
    }

    public static ArrayList<String> getEmissionNameList_Day(){
        return emissionNameList_Day;
    }

    public static ArrayList<Float> getEmissionValueList_Day(){
        return emissionValueList_Day;
    }

    public static ArrayList<String> getDateList_Month(){
        return dateList_Month;
    }

    public static ArrayList<Float> getCarEmissionValueList_Month(){
        return carEmissionValueList_Month;
    }
    public static ArrayList<Float> getBusEmissionValueList_Month(){
        return busEmissionValueList_Month;
    }
    public static ArrayList<Float> getSkytrainEmissionValueList_Month(){
        return skytrainEmissionValueList_Month;
    }
    public static ArrayList<Float> getElectricityBillEmissionValueList_Month(){
        return electricityBillEmissionValueList_Month;
    }
    public static ArrayList<Float> getGasBillEmissionValueList_Month(){
        return gasBillEmissionValueList_Month;
    }


    public static int getEmissionArrayListSize(){
        return emissionArrayList.size();
    }

    public static void resetCurrentCollection(GRAPH_MODE mode){
        switch (mode){
            case DAY:
                emissionArrayList = new ArrayList<>();
                emissionTypeList_Day = new ArrayList<>();
                emissionNameList_Day = new ArrayList<>();
                emissionValueList_Day = new ArrayList<>();
                dateInBill = false;
                break;
            case MONTH:
                dateList_Month = new ArrayList<>();
                emissionTypeList_Month = new ArrayList<>();
                carEmissionValueList_Month = new ArrayList<>();
                skytrainEmissionValueList_Month = new ArrayList<>();
                busEmissionValueList_Month = new ArrayList<>();
                electricityBillEmissionValueList_Month = new ArrayList<>();
                gasBillEmissionValueList_Month = new ArrayList<>();
        }

    }
}
