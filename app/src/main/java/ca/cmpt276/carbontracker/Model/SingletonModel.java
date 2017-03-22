package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Elvin Laptop on 2017-03-06.
 */

public class SingletonModel {
    //TODO: Move all code relating to car search to their own class
    //Singleton should only be the middle ground between logic and UI

    CarCollection currentCarCollection = new CarCollection();
    CarCollection currentSearchCollection = new CarCollection();
    CarCollection totalCarCollection = new CarCollection();
    CarCollection currentSearchPreviousState = new CarCollection();
    RouteCollection routeCollection = new RouteCollection();
    ArrayList<Emission> emissionArrayList = new ArrayList<Emission>();
    JourneyCollection journeyCollection = new JourneyCollection();
    UtilitiesCollection utilitiesCollection = new UtilitiesCollection();
    ArrayList<String> carMakeList;
    public enum RetriveEntries{Current, Search, Total};

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

    public ArrayList<String> getCarModelsOfMake(String make){
        if(currentSearchCollection.size()== 0){
            currentSearchCollection = totalCarCollection;
        }
        currentSearchCollection = totalCarCollection.findCarsWithMake(make);
        currentSearchCollection.searchUniqueModelName();
        ArrayList<String> currentModelList = currentSearchCollection.getUniqueModelNames();
        currentSearchPreviousState = currentSearchCollection.getDuplicate();
        return currentModelList;
    }
    public ArrayList<String> getCarYearsOfModels(String model){
        if(currentSearchCollection.size()== 0){
            currentSearchCollection = totalCarCollection;
        }
        currentSearchCollection = currentSearchPreviousState;
        currentSearchCollection = currentSearchCollection.findCarsWithModel(model);
        currentSearchCollection.searchUniqueModelYears();
        ArrayList<String> currentYearList = currentSearchCollection.getUniqueModelYears();
        return currentYearList;
    }
    public void updateCurrentSearchByYear(int year){
        currentSearchCollection = currentSearchCollection.findCarsWithYear(year);
    }

    public boolean isCurrentCarAdded(String description){
        for(Car car : currentCarCollection){
            if (car.getDescription().equals(description)){
                return true;
            }
        }
        return false;
    }

    public void addNewCarBasedOnDecsription(String nickname, String description){
        for(Car car: currentSearchCollection){
            if (car.getDescriptionNoNickname().equals(description)){
                car.setNickname(nickname);
                currentCarCollection.add(car);
            }
        }
    }

    public void resetCurrentSearchCollection(){
        for(int i = 0; i < currentSearchCollection.size(); i++){
            currentSearchCollection.remove(i);
        }
    }

    public ArrayList<String> getCarEntriesDescription(RetriveEntries mode){
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

    public void updateCurrentCarCollectionDescription(){
        ArrayList<String> updatedDescription = new ArrayList<>();
        ArrayList<String> updatedDescriptionNoNickname = new ArrayList<>();
        for(Car car: currentCarCollection){
            updatedDescription.add(car.getDescription());
            updatedDescriptionNoNickname.add(car.getDescriptionNoNickname());
        }
        currentCarCollection.setDescriptionList(updatedDescription);
        currentCarCollection.setDescriptionNoNicknameList(updatedDescriptionNoNickname);
    }

    public void removeCarDescription(String description){
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

    public Car getCarFromCollection(String description, RetriveEntries mode){

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

    public void setCurrentCarAtIndex(Car car, int index){
        currentCarCollection.setCar(car, index);
    }

    public int getIndexOfCar(Car car){
        return currentCarCollection.getIndex(car);
    }

    public ArrayList<String> getCarMakeList() {
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

    public Route getRouteByName(String name){
        Route returnRoute = new Route();
        for (Route route: routeCollection){
            if(route.getName().toLowerCase().equals(name.toLowerCase())){
                returnRoute = route;
            }
        }
        return returnRoute;
    }
    public RouteCollection getRouteCollection() {
        return routeCollection;
    }

    public void addNewRoute(Route route){
        routeCollection.add(route);
    }

    public void removeFromRouteCollection(String routeName){
        routeCollection.remove(routeName);
    }

    public ArrayList<String> getRouteCollectionNames(){
        ArrayList<String> routeNames = new ArrayList<>();
        for(Route route: routeCollection){
            routeNames.add(route.getName());
        }
        return routeNames;
    }

    public void addNewJourney(String carDescription, String routeName){
        Car newCar = getCarFromCollection(carDescription, RetriveEntries.Current);
        Route newRoute = getRouteByName(routeName);
        Journey newJourney = new Journey(newCar, newRoute);
        journeyCollection.add(newJourney);
    }

    public void addNewJourney(Transportation newTransportation, Route newRoute){
        Journey newJourney = new Journey(newTransportation, newRoute);
        journeyCollection.add(newJourney);
    }

    public int getJourneyCollectionSize(){
        return journeyCollection.size();
    }

    public ArrayList<String> getJourneysDates(){
        return journeyCollection.getAllJourneyDates();
    }

    public ArrayList<String> getJourneyTransportationName(){
        return journeyCollection.getTransportationNameList();
    }

    public ArrayList<String> getJourneysDistanceList(){
        ArrayList<String> totalDistance = new ArrayList<>();
        for(Float distance : journeyCollection.getTotalDistanceList()){
            totalDistance.add(Float.toString(distance));
        }
        return totalDistance;
    }

    public ArrayList<String> getJourneysCarbonEmissionList(){
        ArrayList<String> carbonEmissionList = new ArrayList<>();
        for(Float value: journeyCollection.getJourneyCarbonEmissionList()){
            carbonEmissionList.add(Float.toString(value));
        }
        return carbonEmissionList;
    }

    public ArrayList<String> getUtilitiesDescriptionList(){
        ArrayList<String> utilitiesDescriptionList = new ArrayList<>();
        for (Utilities utilities : utilitiesCollection){
            utilitiesDescriptionList.add(utilities.toString());
        }
        return utilitiesDescriptionList;
    }

    public void addNewUtilitiesBill(Utilities.BILL billType, float amount, Calendar startDate, Calendar endDate, int numberOfPeople){
        Utilities newBill = new Utilities(billType, amount, startDate, endDate, numberOfPeople);
        utilitiesCollection.add(newBill);
    }


    public void deleteUtilities(String description){
        for(Utilities utilities : utilitiesCollection){
            if(utilities.toString().equals(description)){
                utilitiesCollection.remove(utilities);
            }
        }
    }

    public void editUtilities(String originalUtilities, Utilities editedUtilities){
        for(Utilities utilities : utilitiesCollection){
            if (utilities.toString().equals(originalUtilities)){
                int index = utilitiesCollection.getIndex(utilities);
                utilitiesCollection.remove(utilities);
                utilitiesCollection.add(index, editedUtilities);
            }
        }
    }

    public void editRoute(String originalName, String newName, float newCity, float newHighway){
        routeCollection.editRoute(originalName, newName, newCity, newHighway);
    }

}
