package ca.cmpt276.carbontracker.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Keeps all information about a journey,
 * including type of car, distance traveled, and carbon emission amount on the journey
 */

public class Journey extends Emission {

    private Transportation car; // Car object, use getter methods to get required info if needed
    private Route route; //route object, use getter methods to get required info
    private float carbonEmissionValue;


    Calendar date;
    Calendar calendar = new GregorianCalendar();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static final float GAS_CO2_CONSTANT = 8.89f;
    public static final float DIESEL_CO2_CONSTANT = 10.16f;
    public static final float MILES_TO_KILOMETERS_CONSTANT = 0.621371f;
    private static String GASOLINE = "Gasoline";
    private static String DIESEL = "Diesel";

    public Journey(Car car, Route route){
        this.car = car;
        this.route = route;
        calculateCarbonEmission();

        sdf.setCalendar(calendar);
        date = Calendar.getInstance();
    }

    @Override
    protected void calculateCarbonEmission(){
        int cityMilesPerGallon = car.getMilesPerGallonCity();
        int hwyMilesPerGallon = car.getMilesPerGallonHway();
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

        this.carbonEmissionValue = (cityMilesPerGallon * cityDistanceInMiles
                + hwyMilesPerGallon * hwyDistanceInMiles)
                * fuelConstant;
    }

    //alternate constructor
    public Journey(Transportation car, Route route, float carbonEmissionValue)
    {
        this.car = car;
        this.route = route;
        this.carbonEmissionValue = carbonEmissionValue;
        dateCreated = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public void setCar(Transportation car){
        this.car = car;
        calculateCarbonEmission();
    }

    public void setRoute(Route route){
        this.route = route;
        calculateCarbonEmission();
    }

    public Transportation getCar(){
        return this.car;
    }

    public Route getRoute(){
        return this.route; 
    }

    public float getCarbonEmissionValue(){
        return super.getCarbonEmissionValue();
    }


    public void setDate(Calendar newDate){
        date = newDate;
    }

    public Calendar getDate(){
        return date;
    }
}
