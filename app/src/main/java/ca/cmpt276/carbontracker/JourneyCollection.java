package ca.cmpt276.carbontracker;

import java.util.ArrayList;
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

    @Override
    public Iterator<Journey> iterator() {
        return journeys.iterator();
    }
}
