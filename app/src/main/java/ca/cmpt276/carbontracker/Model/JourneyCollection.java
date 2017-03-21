package ca.cmpt276.carbontracker.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Keeps a collection of Journey objects
 */

public class JourneyCollection implements Iterable<Journey>{
    private ArrayList<Journey> journeys = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> carsNameList = new ArrayList<>();
    private ArrayList<Float> totalDistanceList = new ArrayList<>();
    private ArrayList<Float> carbonEmissionList = new ArrayList<>();

    public void add(Journey journey) {
        journeys.add(journey);
    }


    public void add(Car car, Route route) {
        journeys.add(new Journey(car, route));
    }

    public void remove(int index) {
        journeys.remove(index);
    }

    public void remove(Journey journey) {
        journeys.remove(journey);
    }

    public int size() {
        return journeys.size();
    }

    public Journey get(int index){
        return journeys.get(index);
    }

    public ArrayList<String> getAllJourneyDates(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        for(Journey journey : journeys){
            Calendar date = journey.getDate();
            dateList.add(sdf.format(date.getTime()));
        }
        return dateList;
    }

    public ArrayList<String> getCarsNameList(){
        for(Journey journey : journeys){
            carsNameList.add(journey.getCar().getNickname());
        }
        return carsNameList;
    }
    public ArrayList<Float> getTotalDistanceList(){
        for(Journey journey : journeys){
            totalDistanceList.add(journey.getRoute().getTotalDistance());
        }
        return totalDistanceList;
    }
    public ArrayList<Float> getCarbonEmissionList(){
        for(Journey journey : journeys){
            carbonEmissionList.add(journey.getCarbonEmissionValue());
        }
        return carbonEmissionList;
    }

    public ArrayList<Journey> getAllJourneys(){
        return journeys;
    }

    @Override
    public Iterator<Journey> iterator() {
        return journeys.iterator();
    }
}
