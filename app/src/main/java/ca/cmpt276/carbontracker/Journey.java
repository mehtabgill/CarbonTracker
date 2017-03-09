package ca.cmpt276.carbontracker;

/**
 * Journey class keeps all information about a journey
 */

public class Journey {

    private Car car; // Car object, use getter methods to get required info if needed
    private Route route; //route object, use getter methods to get required info

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


}