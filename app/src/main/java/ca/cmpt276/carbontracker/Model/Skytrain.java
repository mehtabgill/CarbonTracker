package ca.cmpt276.carbontracker.Model;

/**
 * Represents Skytrain Travel
 * Carbon emissions are calculated based on distance travelled.
 */

public class Skytrain extends Transportation {

    private final float SKYTRAIN_CARBON_EMISSIONS_PER_KM = 15f; //89g per km per person

    //default constructor
    public Skytrain() {

    }

    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.SKYTRAIN;
    }

}