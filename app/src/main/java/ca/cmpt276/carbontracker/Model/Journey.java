package ca.cmpt276.carbontracker.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Keeps all information about a journey,
 * including type of car, distance traveled, and carbon emission amount on the journey
 */

public class Journey extends Emission {

    private Car car; // Car object, use getter methods to get required info if needed
    private Route route; //route object, use getter methods to get required info
    private float carbonEmissionValue;
    String dateCreated;

    public Journey(Car car, Route route){
        this.car = car;
        this.route = route;
        carbonEmissionValue = EmissionCalculator.calculate(car, route);
        dateCreated = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public void setCar(Car car){
        this.car = car;
        carbonEmissionValue = EmissionCalculator.calculate(car, route);

    }

    public void setRoute(Route route){
        this.route = route;
        carbonEmissionValue = EmissionCalculator.calculate(car, route);
    }

    public Car getCar(){
        return this.car;
    }

    public Route getRoute(){
        return this.route; 
    }

    public float getCarbonEmissionValue(){
        return super.getCarbonEmissionValue();
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String day, String month, String year){
        this.dateCreated = day + "-" + month + "-" + year;
    }
}
