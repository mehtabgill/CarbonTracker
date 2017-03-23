package ca.cmpt276.carbontracker.Model;

/**
 * Represents Bus Travel
 * Carbon emissions are calculated based on distance travelled.
 */

public class Bus extends Transportation {

    private final float BUS_CARBON_EMISSIONS_PER_KM = 89f; //89g per km

    //default constructor
    public Bus() {
    }

    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.BUS;
    }
}