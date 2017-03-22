package ca.cmpt276.carbontracker.Model;

/**
 * Represents a Bike travel.
 * Carbon emissions are 0.
 */

public class Bike extends Transportation {
    //default constructor
    public Bike() {

    }
    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.BIKE;
    }

}
