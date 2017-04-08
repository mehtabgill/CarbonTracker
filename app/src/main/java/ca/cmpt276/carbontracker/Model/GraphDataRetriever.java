package ca.cmpt276.carbontracker.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ca.cmpt276.carbontracker.UI.ViewCarbonFootprintActivity;

/**
 * Static class that read data from Model and return data accordingly
 * For one day: Entry is activity - Journey or Utilities(electricity/gas)
 * For one month: Same as one day
 * One year: Entry is weeks
 */

public final class GraphDataRetriever {
    //TODO: Change this to non-static maybe
    private static SingletonModel model = SingletonModel.getInstance();
    private GraphDataRetriever(){};
    private static GraphDataRetriever instance = new GraphDataRetriever();
    public enum GRAPH_MODE{DAY, MONTH, YEAR};
    private GRAPH_MODE mode;
    private Calendar date;
    private int NUMBER_OF_DAYS;
    private int NUMBER_OF_ENTRIES;
    public final float INDIVIDUAL_TARGET_DAILY = 53f;
    public final float INDIVIDUAL_ACTUAL_DAILY = 56f;


    private ArrayList<Emission> emissionArrayList = new ArrayList<>();
    private ArrayList<String> emissionTypeList_Day = new ArrayList<>();
    private ArrayList<String> emissionNameList_Day = new ArrayList<>();
    private ArrayList<Float> emissionValueList_Day = new ArrayList<>();
    private boolean dateInBill = false;

    //These are used for Month / Year
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Float> carEmissionValue = new ArrayList<>();
    private ArrayList<Float> skytrainEmissionValue = new ArrayList<>();
    private ArrayList<Float> busEmissionValue = new ArrayList<>();
    private ArrayList<Float> electricityBillEmissionValue = new ArrayList<>();
    private ArrayList<Float> gasBillEmissionValue = new ArrayList<>();
    private ArrayList<Float> totalEmissionValue = new ArrayList<>();


    //These are used for pie chart by route
    private ArrayList<Float> routeEmissionValue = new ArrayList<>();
    private ArrayList<String> emissionNameList_Route = new ArrayList<>();

    private JourneyCollection journeyCollection = new JourneyCollection();
    private UtilitiesCollection utilitiesCollection = new UtilitiesCollection();
    private ArrayList<Calendar> calendarArrayList = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

    public static GraphDataRetriever getInstance(){
        return instance;
    }
    public void setUpGraphData(GRAPH_MODE mode, Calendar date){
        this.mode = mode;
        this.date = date;
        sdf.setCalendar(new GregorianCalendar());
        switch (mode){
            case DAY:
                setupData_Day();
                break;
            default:
                setupData_MultipleDays();
                break;
        }
    }

    private void setupData_Day() {
        resetCurrentCollection();
        emissionArrayList = model.getEmissionListOnDay(date);
        if(emissionArrayList.size() > 0){
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
            boolean emissionEmpty = (model.getUtilitiesCollectionSize() == 0);
            if(!emissionEmpty && !dateInBill){
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
            populateRouteEmissionList();
        }

    }

    private void setupData_MultipleDays() {
        resetCurrentCollection();
        if(mode.equals(GRAPH_MODE.MONTH)){
            NUMBER_OF_DAYS = 28;
            NUMBER_OF_ENTRIES = 29;
        }
        else{
            if(mode.equals(GRAPH_MODE.YEAR)){
                NUMBER_OF_DAYS = 365;
                NUMBER_OF_ENTRIES = 13;
            }
        }
        Calendar endDate = (Calendar) date.clone();
        Calendar endClone = (Calendar) endDate.clone();
        endClone.add(Calendar.DATE, 1);
        Calendar startDate = (Calendar) endDate.clone();
        startDate.add(Calendar.DATE, -NUMBER_OF_DAYS);

        journeyCollection = model.getJourneyInRange(startDate, endDate);
        utilitiesCollection = model.getUtilitiesInRange(startDate, endDate);

        populateDateList(startDate, endClone);
        populateJourneyList(startDate);
        populateUtilitiesList();
        populateTotalArrayList();
        populateRouteEmissionList();
    }

    private void populateDateList(Calendar startDate, Calendar endClone) {
        Calendar startClone = (Calendar) startDate.clone();
        switch (mode){
            case MONTH:/*
                while(!equalDate(startClone, endClone)){
                    Calendar date = (Calendar) startClone.clone();
                    dateList.add(sdf.format(date.getTime()));
                    calendarArrayList.add(date);
                    Log.d("Date added", sdf.format(date.getTime()));
                    startClone.add(Calendar.DATE, 1);
                }*/
                for(int i = 0; i < NUMBER_OF_ENTRIES; i++){
                    Calendar date = (Calendar) startClone.clone();
                    int year = date.get(Calendar.YEAR);
                    int month = date.get(Calendar.MONTH);
                    int day = date.get(Calendar.DATE);
                    dateList.add(sdf.format(date.getTime()));
                    calendarArrayList.add(date);
                    startClone.add(Calendar.DATE, 1);
                }
                break;
            case YEAR:
                for(int i = 0; i < NUMBER_OF_ENTRIES; i++){
                    if(i == 11){
                        Calendar date = (Calendar) startClone.clone();
                        dateList.add(sdf.format(date.getTime()));
                        calendarArrayList.add(date);
                        startClone.add(Calendar.DATE, 35);
                    }
                    else{
                        Calendar date = (Calendar) startClone.clone();
                        dateList.add(sdf.format(date.getTime()));
                        calendarArrayList.add(date);
                        startClone.add(Calendar.DATE, 30);
                    }
                }
                break;
        }
    }

    private void populateJourneyList(Calendar startDate) {
        switch (mode){
            case MONTH:
                for(int i = 0; i < NUMBER_OF_ENTRIES; i++){
                    carEmissionValue.add(0.0f);
                    skytrainEmissionValue.add(0.0f);
                    busEmissionValue.add(0.0f);
                }
                for(Journey journey : journeyCollection){
                    Calendar currentDate = journey.getDate();
                    int index = calculateDistanceBetweenDate(startDate, currentDate);
                    switch (journey.getTransportation().getType()){
                        case CAR:
                            carEmissionValue.add(index, journey.getCarbonEmissionValue());
                            break;
                        case BUS:
                            busEmissionValue.add(index, journey.getCarbonEmissionValue());
                            break;
                        case SKYTRAIN:
                            skytrainEmissionValue.add(index, journey.getCarbonEmissionValue());
                            break;
                    }
                }
                break;
            case YEAR:
                for(int i = 0; i < NUMBER_OF_ENTRIES - 1; i++){
                    Calendar startOfMonth = (Calendar) calendarArrayList.get(i).clone();
                    Calendar endOfMonth = (Calendar) calendarArrayList.get(i+1).clone();
                    float carValueMonth = 0.0f;
                    float busValueMonth = 0.0f;
                    float skytrainValueMonth = 0.0f;
                    for(Journey journey : journeyCollection){
                        if (dateInRange(startOfMonth, endOfMonth, journey.getDate())){
                            switch (journey.getTransportation().getType()){
                                case CAR:
                                    carValueMonth += journey.getCarbonEmissionValue();
                                    break;
                                case BUS:
                                    busValueMonth += journey.getCarbonEmissionValue();
                                    break;
                                case SKYTRAIN:
                                    skytrainValueMonth += journey.getCarbonEmissionValue();
                                    break;
                            }
                        }
                    }
                    carEmissionValue.add(carValueMonth);
                    busEmissionValue.add(busValueMonth);
                    skytrainEmissionValue.add(skytrainValueMonth);
                }
                int lastIndex = NUMBER_OF_ENTRIES - 1;
                carEmissionValue.add(carEmissionValue.get(lastIndex - 1));
                busEmissionValue.add(busEmissionValue.get(lastIndex - 1));
                skytrainEmissionValue.add(skytrainEmissionValue.get(lastIndex - 1));
                break;
        }
    }

    private void populateUtilitiesList() {
        switch (mode){
            case MONTH:
                Calendar startCheckingDate = calendarArrayList.get(0);
                Calendar endCheckingDate = calendarArrayList.get(NUMBER_OF_ENTRIES-1);
                getUtilitiesValueByMonth(startCheckingDate, endCheckingDate);
                break;
            case YEAR:
                for(int i = 0; i < NUMBER_OF_ENTRIES - 1; i++){
                    getUtilitiesValueByMonth(calendarArrayList.get(i), calendarArrayList.get(i+1));
                }
                int lastIndex = NUMBER_OF_ENTRIES - 1;
                electricityBillEmissionValue.add(electricityBillEmissionValue.get(lastIndex - 1));
                gasBillEmissionValue.add(gasBillEmissionValue.get(lastIndex - 1));
                break;
        }
    }

    private void getUtilitiesValueByMonth(Calendar startMonthDate, Calendar endMonthDate) {
        int numberOfDays = calculateDistanceBetweenDate(startMonthDate, endMonthDate) + 1;
        Calendar start;
        Calendar end;

        ArrayList<Float> electricValueArrayByMonth = new ArrayList<>();
        ArrayList<Float> gasValueArrayByMonth = new ArrayList<>();
        ArrayList<Calendar> currentCalendarList = new ArrayList<>();

        //Range of date for checking
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
        if(mode.equals(GRAPH_MODE.MONTH)){
            currentCalendarList = calendarArrayList;
        }
        else if(mode.equals(GRAPH_MODE.YEAR)){
            for(int i = 0; i < numberOfDays; i++){
                Calendar date = (Calendar) startMonthDate.clone();
                date.add(Calendar.DATE, i);
                currentCalendarList.add(date);
            }
        }

        float electricityValueForYearEntries = 0.0f;
        float gasValueForYearEntries = 0.0f;

        for(int i = 0; i < numberOfDays; i++){
            electricValueArrayByMonth.add(0.0f);
            gasValueArrayByMonth.add(0.0f);
        }

        for(Utilities utilities : utilitiesCollection){
            boolean startDateInRange = dateInRange(startMonthDate, endMonthDate, utilities.getStartDate());
            boolean endDateInRange = dateInRange(startMonthDate, endMonthDate, utilities.getEndDate());
            if( startDateInRange || endDateInRange) {
                start = (Calendar) startMonthDate.clone();
                end = (Calendar) endMonthDate.clone();
                if(startDateInRange){
                    start = (Calendar) utilities.getStartDate().clone();
                }
                if(endDateInRange){
                    end = (Calendar) utilities.getEndDate().clone();
                }
                end.add(Calendar.DATE, 1);
                while(!equalDate(start, end)){
                    int index = getDateIndex(start, currentCalendarList);
                    if(index == -1){
                        Log.d("Start", sdf2.format(currentCalendarList.get(0).getTime()));
                        String endString = sdf2.format(currentCalendarList.get(28).getTime());
                        Log.d("End", endString);
                        String currentDate = sdf2.format(start.getTime());
                        Log.d("Current", currentDate);
                    }
                    switch (utilities.getBill()){
                        case ELECTRICITY:
                            if(electricValueArrayByMonth.get(index) == 0.0f){
                                electricValueArrayByMonth.set(index, utilities.getDailyAverageEmission());
                            }
                            break;
                        case GAS:
                            if(gasValueArrayByMonth.get(index) == 0.0f){
                                gasValueArrayByMonth.set(index, utilities.getDailyAverageEmission());
                            }
                            break;
                    }
                    start.add(Calendar.DATE, 1);
                }
            }
        }
        if(mode == GRAPH_MODE.MONTH){
            for(int i = 0; i < numberOfDays; i++){
                if(electricValueArrayByMonth.get(i) == 0.0f){
                    Utilities relativeUtilities = model.getRelativeUtilitiesValue(currentCalendarList.get(i), Utilities.BILL.ELECTRICITY);
                    float value = relativeUtilities.getDailyAverageEmission();

                    electricValueArrayByMonth.set(i, value);
                }
                if(gasValueArrayByMonth.get(i) == 0.0f){
                    Utilities relativeUtilities = model.getRelativeUtilitiesValue(currentCalendarList.get(i), Utilities.BILL.GAS);
                    float value = relativeUtilities.getDailyAverageEmission();
                    gasValueArrayByMonth.set(i, value);
                }
            }
            electricityBillEmissionValue = electricValueArrayByMonth;
            gasBillEmissionValue = gasValueArrayByMonth;
        }
        else if(mode.equals(GRAPH_MODE.YEAR)){
            int middleIndex = currentCalendarList.size()/2;
            Calendar middleDate = currentCalendarList.get(middleIndex);
            float electricityRelativeValue = model.getRelativeUtilitiesValue(middleDate, Utilities.BILL.ELECTRICITY).getDailyAverageEmission();
            float gasRelativeValue = model.getRelativeUtilitiesValue(middleDate, Utilities.BILL.GAS).getDailyAverageEmission();

            for(int i = 0; i < numberOfDays; i++){
                if(electricValueArrayByMonth.get(i) == 0){
                    electricityValueForYearEntries += electricityRelativeValue;
                }
                else{
                    electricityValueForYearEntries += electricValueArrayByMonth.get(i);
                }

                if(gasValueArrayByMonth.get(i) == 0){
                    gasValueForYearEntries += gasRelativeValue;
                }
                else{
                    gasValueForYearEntries += gasValueArrayByMonth.get(i);
                }
            }
            electricityBillEmissionValue.add(electricityValueForYearEntries);
            gasBillEmissionValue.add(gasValueForYearEntries);
        }
    }

    private void populateTotalArrayList(){
        for(int i = 0; i < NUMBER_OF_ENTRIES; i++){
            float totalValue = carEmissionValue.get(i) + busEmissionValue.get(i) + skytrainEmissionValue.get(i)
                        + electricityBillEmissionValue.get(i) + gasBillEmissionValue.get(i);
            totalEmissionValue.add(totalValue);
        }
    }

    private void populateRouteEmissionList(){
        float electricityValue = 0f;
        float gasValue = 0f;
        if(mode.equals(GRAPH_MODE.DAY)){
            if(emissionArrayList.size() == 0){
                emissionArrayList = model.getEmissionListOnDay(date);
            }
            if(emissionArrayList.size() > 0){
                for(Emission emission : emissionArrayList){
                    if(emission instanceof Journey){
                        journeyCollection.add((Journey) emission);
                    }

                    else if(emission instanceof Utilities){
                        if(((Utilities) emission).getBill().equals(Utilities.BILL.ELECTRICITY)){
                            electricityValue += ((Utilities) emission).getDailyAverageEmission();
                        }
                        else{
                            gasValue += ((Utilities) emission).getDailyAverageEmission();
                        }
                    }
                }
            }
        }
        if(journeyCollection.size() > 0) {
            emissionNameList_Route = journeyCollection.getUniqueRouteNames();
            for (String routeName : emissionNameList_Route) {
                if (routeName.equals("name")) {
                    int index = emissionNameList_Route.indexOf(routeName);
                    emissionNameList_Route.set(index, "unnamed");
                }
            }
            for (int i = 0; i < emissionNameList_Route.size(); i++) {
                Float routeValue = 0f;
                for (Journey journey : journeyCollection) {
                    if (journey.getRoute().getName().equals(emissionNameList_Route.get(i))) {
                        routeValue += journey.getCarbonEmissionValue();
                    }
                }
                routeEmissionValue.add(routeValue);
            }
        }
        if(!mode.equals(GRAPH_MODE.DAY)) {
            for(int i = 0; i < NUMBER_OF_ENTRIES; i++){
                electricityValue += electricityBillEmissionValue.get(i);
                gasValue += gasBillEmissionValue.get(i);
            }
        }
        routeEmissionValue.add(electricityValue);
        routeEmissionValue.add(gasValue);
        emissionNameList_Route.add(ViewCarbonFootprintActivity.ELECTRICITY);
        emissionNameList_Route.add(ViewCarbonFootprintActivity.GAS);
    }

    private int getDateIndex(Calendar date, ArrayList<Calendar> currentCalendar){
        for(int i = 0; i < currentCalendar.size(); i++){
            if(equalDate(currentCalendar.get(i), date)){
                return i;
            }
        }
        return -1;
    }

    private int calculateDistanceBetweenDate(Calendar startDate, Calendar endDate) {
        int days = 0;
        if(startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)){
            days = Math.abs( endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR) );
        }
        else{
            if(startDate.get(Calendar.YEAR) > endDate.get(Calendar.YEAR)){
                Calendar temp = startDate;
                startDate = endDate;
                endDate = temp;
            }

            Calendar start = (Calendar) startDate.clone();
            Calendar end = (Calendar) endDate.clone();
            int daysLeftInStartYear = Math.abs(startDate.getActualMaximum(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR));
            int daysPassedInEndYear = endDate.get(Calendar.DAY_OF_YEAR);
            int daysInBetweenYears = 0;
            while(end.get(Calendar.YEAR) - start.get(Calendar.YEAR) > 1){
                start.add(Calendar.YEAR, 1);
                daysInBetweenYears += start.getActualMaximum(Calendar.DAY_OF_YEAR);
            }
            days = daysLeftInStartYear + daysInBetweenYears + daysPassedInEndYear;
        }
        return days;
    }

    private boolean equalDate(Calendar day1, Calendar day2){
        boolean equal = false;
        boolean yearEqual = day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR);
        boolean monthEqual = day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH);
        boolean dateEqual = day1.get(Calendar.DATE) == day2.get(Calendar.DATE);
        if((yearEqual && monthEqual) && dateEqual) {
            equal = true;
        }
        return equal;
    }

    private boolean dateInRange(Calendar startDate, Calendar endDate, Calendar checkingDate){
        /*Calendar currentCheckingDate = (Calendar) startDate.clone();
        Calendar endDateClone = (Calendar) endDate.clone();
        endDateClone.add(Calendar.DATE, 1);
        boolean equal = false;
        while(!equalDate(currentCheckingDate, endDateClone)){
            if(equalDate(currentCheckingDate, checkingDate)) {
                equal = true;
                break;
            }
            else{
                currentCheckingDate.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return equal;*/
        boolean inRange;
        if(model.firstDateBeforeSecondDate(startDate, checkingDate) &&
            (model.firstDateBeforeSecondDate(checkingDate, endDate))){
            inRange = true;
        }
        else{
            inRange = false;
        }
        return inRange;
    }

    public ArrayList<String> getEmissionTypeList_Day(){
        return emissionTypeList_Day;
    }

    public ArrayList<String> getEmissionNameList_Day(){
        return emissionNameList_Day;
    }

    public ArrayList<Float> getEmissionValueList_Day(){
        return emissionValueList_Day;
    }

    public ArrayList<String> getDateList(){
        return dateList;
    }

    public ArrayList<Float> getCarEmissionValue(){
        return carEmissionValue;
    }
    public ArrayList<Float> getBusEmissionValue(){
        return busEmissionValue;
    }
    public ArrayList<Float> getSkytrainEmissionValue(){
        return skytrainEmissionValue;
    }
    public ArrayList<Float> getElectricityBillEmissionValue(){
        return electricityBillEmissionValue;
    }
    public ArrayList<Float> getGasBillEmissionValue(){
        return gasBillEmissionValue;
    }

    public ArrayList<Float> getEmissionValueByMode(){
        ArrayList<Float> emissionValueByMode = new ArrayList<>();
        Float carValue = 0f;
        Float busValue = 0f;
        Float skytrainValue = 0f;
        Float electricityValue = 0f;
        Float gasValue = 0f;

        for(int i = 0; i < NUMBER_OF_ENTRIES; i++){
            carValue += carEmissionValue.get(i);
            busValue += busEmissionValue.get(i);
            skytrainValue += skytrainEmissionValue.get(i);
            electricityValue += electricityBillEmissionValue.get(i);
            gasValue += gasBillEmissionValue.get(i);
        }
        emissionValueByMode.add(carValue);
        emissionValueByMode.add(busValue);
        emissionValueByMode.add(skytrainValue);
        emissionValueByMode.add(electricityValue);
        emissionValueByMode.add(gasValue);
        return emissionValueByMode;
    }

    public ArrayList<Float> getTotalEmissionValue(){
        return totalEmissionValue;

    }

    public ArrayList<Float> getEmissionValueByRoute(){
        return routeEmissionValue;
    }

    public ArrayList<String> getEmissionNameList_Route(){
        return emissionNameList_Route;
    }

    public int getEmissionArrayListSize(){
        return emissionArrayList.size();
    }


    public void resetCurrentCollection(){
        switch (mode){
            case DAY:
                emissionTypeList_Day = new ArrayList<>();
                emissionNameList_Day = new ArrayList<>();
                emissionValueList_Day = new ArrayList<>();
                dateInBill = false;
                break;
            default:
                emissionArrayList = new ArrayList<>();
                calendarArrayList = new ArrayList<>();
                dateList = new ArrayList<>();
                carEmissionValue = new ArrayList<>();
                skytrainEmissionValue = new ArrayList<>();
                busEmissionValue = new ArrayList<>();
                electricityBillEmissionValue = new ArrayList<>();
                gasBillEmissionValue = new ArrayList<>();
                totalEmissionValue = new ArrayList<>();
        }
        routeEmissionValue = new ArrayList<>();
        emissionNameList_Route = new ArrayList<>();
    }
    public int getNumberOfEntries(){
        return NUMBER_OF_ENTRIES;
    }
    public float getIndividualTarget(){
        return INDIVIDUAL_TARGET_DAILY;
    }
    public float getIndividualActual(){
        return INDIVIDUAL_ACTUAL_DAILY;
    }
}
