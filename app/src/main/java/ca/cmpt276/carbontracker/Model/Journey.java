package ca.cmpt276.carbontracker.Model;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ca.cmpt276.carbontracker.UI.WelcomeScreenActivity;

/**
 * Keeps all information about a journey,
 * including transportationType of transportation, distance traveled, and carbon emission amount on the journey
 */

public class Journey extends Emission {

    private String[] MONTH = new String[] {"January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December"};

    private Transportation transportation; // Car object, use getter methods to get required info if needed
    private Route route; //route object, use getter methods to get required info
    private float carbonEmissionValue;

    Calendar date;
    Calendar calendar = new GregorianCalendar();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private static final float CAR_GAS_CO2_CONSTANT = 8.89f;
    private static final float CAR_DIESEL_CO2_CONSTANT = 10.16f;
    private static final float CAR_MILES_TO_KILOMETERS_CONSTANT = 0.621371f;

    private final float SKYTRAIN_CARBON_EMISSIONS_PER_KM_CONSTANT = 0.015f; //89g per km per person
    private final float BUS_CARBON_EMISSIONS_PER_KM_CONSTANT = 0.089f; //89g per km

    private static String GASOLINE = "Gasoline";
    private static String DIESEL = "Diesel";

    private Transportation.TRANSPORTATION_TYPE transportationType;

    public Journey(Transportation transportation, Route route){
        this.transportation = transportation;
        transportationType = transportation.getType();
        this.route = new Route(route);
        calculateCarbonEmission();
        date = Calendar.getInstance();
    }

    public Journey() {

    }

    @Override
    protected void calculateCarbonEmission(){
        switch (transportationType){
            case CAR:
                float cityMilesPerGallon = transportation.getMilesPerGallonCity();
                float hwyMilesPerGallon = transportation.getMilesPerGallonHway();
                float cityDistance = route.getCityDriveDistance();
                float hwyDistance = route.getHighwayDriveDistance();

                float cityDistanceInMiles = cityDistance * CAR_MILES_TO_KILOMETERS_CONSTANT;
                float hwyDistanceInMiles = hwyDistance * CAR_MILES_TO_KILOMETERS_CONSTANT;

                String fuelType = transportation.getFuelType();
                float fuelConstant = 0;
                if(fuelType.equals(GASOLINE)) {
                    fuelConstant = CAR_GAS_CO2_CONSTANT;
                }
                else if(fuelType.equals(DIESEL)){
                    fuelConstant = CAR_DIESEL_CO2_CONSTANT;
                }
                this.carbonEmissionValue = (cityDistanceInMiles / cityMilesPerGallon
                        + hwyDistanceInMiles / hwyMilesPerGallon)
                        * fuelConstant;

                break;
            case SKYTRAIN:
                this.carbonEmissionValue = route.getTotalDistance() * SKYTRAIN_CARBON_EMISSIONS_PER_KM_CONSTANT;
                break;
            case BUS:
                this.carbonEmissionValue = route.getTotalDistance() * BUS_CARBON_EMISSIONS_PER_KM_CONSTANT;
                break;
            case WALK:
                this.carbonEmissionValue = 0;
                break;
            case BIKE:
                this.carbonEmissionValue = 0;
        }
        this.carbonEmissionValue /= SingletonModel.getInstance().getUnitConversionFactor();
    }

    public String getUnit() {
        return SingletonModel.getInstance().getUnit();
    }

    public void setTransportation(Transportation transportation){
        this.transportation = transportation;
        this.transportationType = transportation.getType();
        calculateCarbonEmission();
    }

    public void setRoute(Route route){
        this.route = new Route(route);
        calculateCarbonEmission();
    }

    public Transportation getTransportation(){
        return this.transportation;
    }

    public String getTransportationName(){
        switch (transportation.getType()){
            case CAR:
                return ((Car)transportation).getNickname();
            case BUS:
                return Transportation.TRANSPORTATION_TYPE.BUS.toString();
            case BIKE:
                return Transportation.TRANSPORTATION_TYPE.BIKE.toString();
            case SKYTRAIN:
                return Transportation.TRANSPORTATION_TYPE.SKYTRAIN.toString();
            case WALK:
                return Transportation.TRANSPORTATION_TYPE.WALK.toString();
            default:
                return " ";
        }
    }

    public Route getRoute()
    {
        return this.route; 
    }

    public float getCarbonEmissionValue() {
        calculateCarbonEmission();
        return carbonEmissionValue;
    }

    public float getDistance() {
        return route.getTotalDistance();
    }

    public void setDate(Calendar newDate){
        date = newDate;
    }

    public Calendar getDate(){
        return date;
    }

    public String getStringDate() {

        String temp = MONTH[date.get(Calendar.MONTH)] + " "
                    + date.get(Calendar.DAY_OF_MONTH) + ", "
                    + date.get(Calendar.YEAR);
        return temp;
    }
}
