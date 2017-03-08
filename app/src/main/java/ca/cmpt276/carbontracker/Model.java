package ca.cmpt276.carbontracker;

import java.util.ArrayList;

/**
 * Created by Elvin Laptop on 2017-03-06.
 */

public class Model {
    static CarCollection currentCarCollection = new CarCollection();
    static CarCollection currentSearchCollection = new CarCollection();
    static RouteCollection routes = new RouteCollection();
    static CarCollection totalCarCollection = new CarCollection();
    static CarCollection currentSearchPreviousState = new CarCollection();
    static ArrayList<String> carMakeList;
    static public enum RetriveEntries{Current, Search};
    boolean fullDataLoaded = false;
    boolean makeDataLoaded = false;

    private static final Model instance = new Model();

    //private constructor to avoid client applications to use constructor
    private Model(){}
    public static Model getInstance(){
        return instance;
    }

    /*
     * getCarModelsOfMake, getCarYearsOfModels,
     * updateCurrentSearchByYear, addNewCarBasedOnDecsription
     * are logic functions for current search mode;
     * May need to edit to adapt different modes of searching later
     */
    public static ArrayList<String> getCarModelsOfMake(String make){
        if(currentSearchCollection.size()== 0){
            currentSearchCollection = totalCarCollection;
        }
        currentSearchCollection = totalCarCollection.findCarsWithMake(make);
        currentSearchCollection.searchUniqueModelName();
        ArrayList<String> currentModelList = currentSearchCollection.getUniqueModelNames();
        currentSearchPreviousState = currentSearchCollection.getDuplicate();
        return currentModelList;
    }
    public static ArrayList<String> getCarYearsOfModels(String model){
        if(currentSearchCollection.size()== 0){
            currentSearchCollection = totalCarCollection;
        }
        currentSearchCollection = currentSearchPreviousState;
        currentSearchCollection = currentSearchCollection.findCarsWithModel(model);
        currentSearchCollection.searchUniqueModelYears();
        ArrayList<String> currentYearList = currentSearchCollection.getUniqueModelYears();
        return currentYearList;
    }

    public static void updateCurrentSearchByYear(int year){
        currentSearchCollection = currentSearchCollection.findCarsWithYear(year);
    }

    public static void addNewCarBasedOnDecsription(String description){
        for(Car car: currentSearchCollection){
            if (car.getDescription().equals(description)){
                currentCarCollection.add(car);
            }
        }
    }

    public static ArrayList<String> getCarEntriesDescription(RetriveEntries mode){
        ArrayList<String> carEntriesDescription = new ArrayList<>();
        switch (mode){
            case Search:
                for(Car car: currentSearchCollection){
                    carEntriesDescription.add(car.getDescription());
                }
                break;
            case Current:
                for(Car car: currentCarCollection){
                    carEntriesDescription.add(car.getDescription());
                }
                break;
        }
        return carEntriesDescription;
    }

    public static void resetCurrentSearchCollection(){
        for(int i = 0; i < currentSearchCollection.size(); i++){
            currentSearchCollection.remove(i);
        }
    }

    public void setTotalCarCollection(CarCollection totalCarCollection){
        this.totalCarCollection = totalCarCollection;
    }

    public static ArrayList<String> getCarMakeList() {
        return carMakeList;
    }

    public void setCarMakeList(ArrayList<String> carMakeList) {
        this.carMakeList = carMakeList;
    }
    public CarCollection getAllCurrentCars(){
        return currentCarCollection;
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
