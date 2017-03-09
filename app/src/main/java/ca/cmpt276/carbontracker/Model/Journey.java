package ca.cmpt276.carbontracker.Model;

/**
 * Journey class keeps all information about a journey
 */

public class Journey {

    private Car car; // Car object, use getter methods to get required info if needed
    private Route route; //route object, use getter methods to get required info
    private float carbonEmissionValue;
    public Journey(Car car, Route route){
        this.car = car;
        this.route = route;
        carbonEmissionValue = EmissionCalculator.calculate(car, route);
    }

    public void addCar(Car car){
        this.car = car;
    }

    public void addRoute(Route route){
        this.route = route;
    }

    public Car getCar(){
        return this.car;
    }

    public Route getRoute(){
        return this.route; 
    }

    public float getCarbonEmissionValue(){
        return carbonEmissionValue;
    }


}
