package ca.cmpt276.carbontracker.Model;

/**
 * Represents a Bike travel.
 * Carbon emissions are 0.
 */

public class Bike extends Transportation {

    private float distance;

    //default constructor
    public Bike() {
        distance = 0;
    }

    //alternate constructor
    public Bike(float distance){
        this.distance = distance;
    }

    public void setDistance(float distance){
        this.distance = distance;
    }

    public float getDistance(){
        return distance;
    }

}
