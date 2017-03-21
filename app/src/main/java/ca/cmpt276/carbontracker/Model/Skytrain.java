package ca.cmpt276.carbontracker.Model;

/**
 * Represents Skytrain Travel
 * Carbon emissions are calculated based on distance travelled.
 */

public class Skytrain extends Transportation {

    private final float SKYTRAIN_CARBON_EMISSIONS_PER_KM = 15f; //89g per km per person

    private float distance;
    private float carbonEmitted;

    //default constructor
    public Skytrain() {
        distance = 0;
        carbonEmitted = 0;
    }

    //alternate constructor
    public Skytrain(float distance){
        this.distance = distance;
        carbonEmitted = distance * SKYTRAIN_CARBON_EMISSIONS_PER_KM;
    }

    public void setDistance(float distance){
        this.distance = distance;
        carbonEmitted = distance * SKYTRAIN_CARBON_EMISSIONS_PER_KM;
    }

    public float getDistance(){
        return distance;
    }

    public float getCarbonEmitted(){
        return this.carbonEmitted;
    }

}