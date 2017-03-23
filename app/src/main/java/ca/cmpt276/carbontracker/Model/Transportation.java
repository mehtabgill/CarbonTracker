package ca.cmpt276.carbontracker.Model;

/**
 * This class is the parent class for Car, bus, skytrain, walk, bike classes
 */

public abstract class Transportation {

    public enum TRANSPORTATION_TYPE {CAR, BUS, SKYTRAIN, BIKE, WALK}
    public static final String[] TYPE = new String[] {"CAR", "BUS", "SKYTRAIN", "BIKE", "WALK"};

    //default constructor
    public Transportation() {

    }

    //These methods will be overriden by Car
    public String getNickname() {
        return "";
    }

    public float getMilesPerGallonCity() {
        return 0;
    }

    public float getMilesPerGallonHway() {
        return 0;
    }

    public String getFuelType() {
        return "";
    }


    public abstract TRANSPORTATION_TYPE getType();
}