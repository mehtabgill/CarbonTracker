package ca.cmpt276.carbontracker.Model;

/**
 * Represents a walk
 * Carbon emissions are 0.
 */

public class Walk extends Transportation {

    private float distance;

    //default constructor
    public Walk() {
        distance = 0;
    }

    //alternate constructor
    public Walk(float distance){
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
        return TRANSPORTATION_TYPE.WALK;
    }
}
