package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;

/**
 * Created by Elvin Laptop on 2017-03-06.
 */

public class SingletonModel {
    static CarCollection currentCarCollection = new CarCollection();
    static CarCollection currentSearchCollection = new CarCollection();
    static CarCollection totalCarCollection = new CarCollection();
    static CarCollection currentSearchPreviousState = new CarCollection();
    static RouteCollection routeCollection = new RouteCollection();
    static JourneyCollection journeyCollection = new JourneyCollection();
    static ArrayList<String> carMakeList;
    static ArrayList<Emission> emissionArrayList = new ArrayList<Emission>();
    static public enum RetriveEntries{Current, Search, Total};
    boolean fullDataLoaded = false;
    boolean makeDataLoaded = false;

    private static final SingletonModel instance = new SingletonModel();

    //private constructor to avoid client applications to use constructor
    private SingletonModel(){}
    public static SingletonModel getInstance(){
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
            if (car.getDescriptionNoNickname().equals(description)){
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
            updatedDescriptionNoNickname.add(car.getDescriptionNoNickname());
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

    public static Car getCarFromCollection(String description, RetriveEntries mode){
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
                    current = car.getDescriptionNoNickname();
                    compareValue = description.compareTo(current);
                    if (compareValue == 0) {
                        returnCar = car;
                    }
                }
                break;
            case Search:
                for (Car car : currentSearchCollection) {
                    current = car.getDescriptionNoNickname();
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

    public void addToCarCollection(Car car){
        currentCarCollection.add(car);
    }

    public void removeFromCarCollection(String nickname){
        Car car = currentCarCollection.findCarsWithNickname(nickname);
        currentCarCollection.remove(car);
    }

    public static Route getRouteByName(String name){
        Route returnRoute = new Route();
        for (Route route: routeCollection){
            if(route.getName().toLowerCase().equals(name.toLowerCase())){
                returnRoute = route;
            }
        }
        return returnRoute;
    }
    public static RouteCollection getRouteCollection() {
        return routeCollection;
    }

    public static void addNewRoute(Route route){
        routeCollection.add(route);
    }

    public static void removeFromRouteCollection(String routeName){
        routeCollection.remove(routeName);
    }

    public static ArrayList<String> getRouteCollectionNames(){
        ArrayList<String> routeNames = new ArrayList<>();
        for(Route route: routeCollection){
            routeNames.add(route.getName());
        }
        return routeNames;
    }

    public static void addNewJourney(String carDescription, String routeName){
        Car newCar = getCarFromCollection(carDescription, RetriveEntries.Current);
        Route newRoute = getRouteByName(routeName);
        Journey newJourney = new Journey(newCar, newRoute);
        journeyCollection.add(newJourney);
    }

    public static int getJourneyCollectionSize(){
        return journeyCollection.size();
    }

    public static ArrayList<String> getJourneysDates(){
        return journeyCollection.getAllJourneyDates();
    }
    public static ArrayList<String> getJourneysCarList(){
        return journeyCollection.getCarsNameList();
    }
    public static ArrayList<String> getJourneysTotalDistanceList(){
        ArrayList<String> totalDistance = new ArrayList<>();
        for(Float distance : journeyCollection.getTotalDistanceList()){
            totalDistance.add(Float.toString(distance));
        }
        return totalDistance;
    }
    public static ArrayList<String> getJourneysCarbonEmissionList(){
        ArrayList<String> carbonEmissionList = new ArrayList<>();
        for(Float value: journeyCollection.getCarbonEmissionList()){
            carbonEmissionList.add(Float.toString(value));
        }
        return carbonEmissionList;
    }

    public static void editRoute(String originalName, String newName, float newCity, float newHighway){
        routeCollection.editRoute(originalName, newName, newCity, newHighway);
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
