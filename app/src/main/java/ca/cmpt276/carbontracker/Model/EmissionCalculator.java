package ca.cmpt276.carbontracker.Model;

/**
 * Utility class that calculate Emission based on car and route object
 */

public class EmissionCalculator {
    public static final float GAS_CO2_CONSTANT = 8.89f;
    public static final float DIESEL_CO2_CONSTANT = 10.16f;
    public static final float MILES_TO_KILOMETERS_CONSTANT = 0.621371f;
    private static String GASOLINE = "Gasoline";
    private static String DIESEL = "Diesel";

    public static float calculate(Transportation car, Route route) {
        float cityMilesPerGallon = car.getMilesPerGallonCity();
        float hwyMilesPerGallon = car.getMilesPerGallonHway();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        float cityDistanceInMiles = cityDistance * MILES_TO_KILOMETERS_CONSTANT;
        float hwyDistanceInMiles = hwyDistance * MILES_TO_KILOMETERS_CONSTANT;

        String fuelType = car.getFuelType();
        float fuelConstant = 0;
        if(fuelType.equals(GASOLINE)) {
            fuelConstant = GAS_CO2_CONSTANT;
        }
        else if(fuelType.equals(DIESEL)){
            fuelConstant = DIESEL_CO2_CONSTANT;
        }

        return (cityMilesPerGallon * cityDistanceInMiles + hwyMilesPerGallon * hwyDistanceInMiles) * fuelConstant;
    }
}
