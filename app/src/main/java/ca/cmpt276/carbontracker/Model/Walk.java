package ca.cmpt276.carbontracker.Model;

/**
 * Represents a walk
 * Carbon emissions are 0.
 */

public class Walk extends Transportation {

    private float distance;

    //default constructor
    public Walk() {

    }

    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.WALK;
    }
}
