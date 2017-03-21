package ca.cmpt276.carbontracker.Model;

/**
 * Represents Bus Travel
 * Carbon emissions are calculated based on distance travelled.
 */

public class Bus extends Transportation {

    private final float BUS_CARBON_EMISSIONS_PER_KM = 89f; //89g per km

    private float distance;
    private float carbonEmitted;


    //default constructor
    public Bus() {
        distance = 0;
        carbonEmitted = 0;
    }

    //alternate constructor
    public Bus(float distance){
        this.distance = distance;
        carbonEmitted = distance * BUS_CARBON_EMISSIONS_PER_KM;
    }

    public void setDistance(float distance){
        this.distance = distance;
        carbonEmitted = distance * BUS_CARBON_EMISSIONS_PER_KM;
    }

    public float getDistance(){
        return distance;
    }

    public float getCarbonEmitted(){
        return this.carbonEmitted;
    }

    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.BUS;
    }
}