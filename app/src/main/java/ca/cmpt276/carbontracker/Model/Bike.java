package ca.cmpt276.carbontracker.Model;

/**
 * Represents a Bike travel.
 * Carbon emissions are 0.
 */

public class Bike extends Transportation {

    private float distance;

    //default constructor
    public Bike() {

    }

    //alternate constructor, probably can remove these
    public Bike(float distance){
        this.distance = distance;
    }

    public void setDistance(float distance){
        this.distance = distance;
    }

    public float getDistance(){
        return distance;
    }

    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.BIKE;
    }

}
