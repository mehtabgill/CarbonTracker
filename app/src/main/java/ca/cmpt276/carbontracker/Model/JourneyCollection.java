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
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(Journey journey : journeys){
            Calendar date = journey.getDate();
            dateList.add(sdf.format(date.getTime()));
        }
        return dateList;
    }

    public ArrayList<String> getTransportationNameList(){
        ArrayList<String> transportationNameList = new ArrayList<>();
        for(Journey journey : journeys){
            if(journey.getTransportation().getType().equals(Transportation.TRANSPORTATION_TYPE.CAR)){
                transportationNameList.add(journey.getTransportation().getNickname());
            }
            else{
                switch (journey.getTransportation().getType()){
                    case WALK:
                        transportationNameList.add("Walk");
                        break;
                    case BIKE:
                            transportationNameList.add("Bike");
                            break;
                    case BUS:
                            transportationNameList.add("Bus");
                            break;
                    case SKYTRAIN:
                            transportationNameList.add("Skytrain");
                            break;
                }
            }
        }
        return transportationNameList;
    }
    public ArrayList<Float> getTotalDistanceList(){
        ArrayList<Float> totalDistanceList = new ArrayList<>();
        for(Journey journey : journeys){
            totalDistanceList.add(journey.getRoute().getTotalDistance());
        }
        return totalDistanceList;
    }
    public ArrayList<Float> getCarbonEmissionList(){
        ArrayList<Float> carbonEmissionList = new ArrayList<>();
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
