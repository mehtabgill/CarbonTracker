package ca.cmpt276.carbontracker;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Elvin Laptop on 2017-03-06.
 */

public class Model {
    CarCollection currentCars = new CarCollection();

    RouteCollection routes = new RouteCollection();
    CarCollection totalCarList;
    ArrayList<String> carMakeList;

    boolean fullDataLoaded = false;
    boolean makeDataLoaded = false;

    private static final Model instance = new Model();

    //private constructor to avoid client applications to use constructor
    private Model(){}

    public static Model getInstance(){
        return instance;
    }

    public CarCollection getTotalCarList(){
        return totalCarList;
    }

    public void setTotalCarList(CarCollection totalCarList){
        this.totalCarList = totalCarList;
    }

    public ArrayList<String> getCarMakeList() {
        return carMakeList;
    }

    public void setCarMakeList(ArrayList<String> carMakeList) {
        this.carMakeList = carMakeList;
    }
    public CarCollection getAllCurrentCars(){
        return currentCars;
    }

    public RouteCollection getRoutes() {
        return routes;
    }

    public boolean isLoaded() {
        return makeDataLoaded && fullDataLoaded;
    }

    public void setFullDataLoaded() {
        this.fullDataLoaded = true;
    }
    public void setMakeDataLoaded() {
        this.makeDataLoaded = true;
    }
}
