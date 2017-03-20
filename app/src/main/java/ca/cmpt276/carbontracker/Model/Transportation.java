package ca.cmpt276.carbontracker.Model;

/**
 * This class is the parent class for Car, bus, skytrain, walk, bike classes
 */

public class Transportation {

    //default constructor
    public Transportation() {

    }

    //These methods will be overriden by Car
    public String getNickname() {
        return "";
    }

    public int getMilesPerGallonCity() {
        return 0;
    }

    public int getMilesPerGallonHway() {
        return 0;
    }

    public String getFuelType() {
        return "";
    }

    //skytrain
    public float getCarbonEmitted(){
        return 0;
    }



}
