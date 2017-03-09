package ca.cmpt276.carbontracker.Model;

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
    static JourneyCollection currentJourneyCollection = new JourneyCollection();
    static public enum RetriveEntries{Current, Search, Total};
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
    public static boolean isCurrentCarAdded(String description){
        for(Car car : currentCarCollection){
            if (car.getDescription().equals(description)){
                return true;
            }
        }
        return false;
    }

    public static void addNewCarBasedOnDecsription(String nickname, String description){
        for(Car car: currentSearchCollection){
            if (car.getDescriptionNoNickame().equals(description)){
                car.setNickname(nickname);
                currentCarCollection.add(car);
            }
        }
    }

    public static void resetCurrentSearchCollection(){
        for(int i = 0; i < currentSearchCollection.size(); i++){
            currentSearchCollection.remove(i);
        }
    }

    public static ArrayList<String> getCarEntriesDescription(RetriveEntries mode){
        ArrayList<String> carEntriesDescription = new ArrayList<>();
        switch (mode){
            case Search:
                carEntriesDescription = currentSearchCollection.getDescriptionNoNickNameList();
                break;
            case Current:
                carEntriesDescription = currentCarCollection.getDescriptionList();
                break;
        }
        return carEntriesDescription;
    }

    public static void updateCurrentCarCollectionDescription(){
        ArrayList<String> updatedDescription = new ArrayList<>();
        ArrayList<String> updatedDescriptionNoNickname = new ArrayList<>();
        for(Car car: currentCarCollection){
            updatedDescription.add(car.getDescription());
            updatedDescriptionNoNickname.add(car.getDescriptionNoNickame());
        }
        currentCarCollection.setDescriptionList(updatedDescription);
        currentCarCollection.setDescriptionNoNicknameList(updatedDescriptionNoNickname);
    }

    public static void removeCarDescription(String description){
        for(int i = 0; i < currentCarCollection.size(); i++){
            if(currentCarCollection.getCar(i).getDescription().equals(description)){
                currentCarCollection.getDescriptionList().remove(i);
                currentCarCollection.getDescriptionNoNickNameList().remove(i);
            }
        }
    }


    public void setTotalCarCollection(CarCollection totalCarCollection){
        this.totalCarCollection = totalCarCollection;
    }

    /*
     * TODO: edit this later
     */

    public static Car findCarBasedOnDescription(String description, RetriveEntries mode){
        Car returnCar = new Car();
        String current;
        int compareValue;
        switch (mode) {
            case Current:
                for (Car car : currentCarCollection) {
                    current = car.getDescription();
                    compareValue = description.compareTo(current);
                    if (compareValue == 0) {
                        returnCar = car;
                    }
                }
                break;
            case Total:
                for (Car car : totalCarCollection) {
                    current = car.getDescriptionNoNickame();
                    compareValue = description.compareTo(current);
                    if (compareValue == 0) {
                        returnCar = car;
                    }
                }
                break;
            case Search:
                for (Car car : currentSearchCollection) {
                    current = car.getDescriptionNoNickame();
                    compareValue = description.compareTo(current);
                    if (compareValue == 0) {
                        returnCar = car;
                    }
                }
                break;

        }
        return returnCar;
    }

    public static void setCurrentCarAtIndex(Car car, int index){
        currentCarCollection.setCarAtIndex(car, index);
    }

    public static int getIndexOfCar(Car car){
        return currentCarCollection.getIndex(car);
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
