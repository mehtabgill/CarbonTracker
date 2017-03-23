package ca.cmpt276.carbontracker.Model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;

public class SingletonModel {
    //TODO: Move all code relating to car search to their own class
    //Singleton should only be the middle ground between logic and UI

    private CarCollection currentCarCollection = new CarCollection();
    private CarCollection currentSearchCollection = new CarCollection();
    private CarCollection totalCarCollection = new CarCollection();
    private CarCollection currentSearchPreviousState = new CarCollection();
    private RouteCollection routeCollection = new RouteCollection();

    private JourneyCollection journeyCollection = new JourneyCollection();
    private UtilitiesCollection utilitiesCollection = new UtilitiesCollection();

    private ArrayList<String> carMakeList;
    public enum RetrieveEntries {Current, Search, Total};

    private static final SingletonModel instance = new SingletonModel();

    private DBAdapter database;

    //private constructor to avoid client applications to use constructor
    private SingletonModel(){}

    public static SingletonModel getInstance(){
        return instance;
    }

    /*
     * getCarModelsOfMake, getCarYearsOfModels,
     * updateCurrentSearchByYear, addNewCarWithNickname
     * are logic functions for current search mode;
     * May need to edit to adapt different modes of searching later
     */

    public void openDB(Context context) {
        database = new DBAdapter(context);
        database.open();
    }

    public void closeDB() {
        database.close();
    }

    public void loadDataFromDB() {
        loadCarsFromDB();
        loadRoutesFromDB();
        loadJourneysFromDB();
        loadUtilitiesFromDB();
    }

    private void addToCarDB(Car car) {
        database.insertCar(car);
    }

    private void addToRouteDB(Route route) {
        database.insertRoute(route);
    }

    private void addToJourneyDB(Journey journey) {
        database.insertJourney(journey);
    }

    private void addToUtilitiesDB(Utilities utilities) {
        database.insertUtility(utilities);
    }

    private void loadCarsFromDB() {
        Cursor cursor = database.getAllCarRows();
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(DBAdapter.COL_CAR_NAME);
                String model = cursor.getString(DBAdapter.COL_CAR_MODEL);
                String make = cursor.getString(DBAdapter.COL_CAR_MAKE);
                int year = cursor.getInt(DBAdapter.COL_CAR_YEAR);
                String displacement = cursor.getString(DBAdapter.COL_CAR_DISPLACEMENT_VOL);
                String transmission = cursor.getString(DBAdapter.COL_CAR_TRANSMISSION_TYPE);
                String fuelType = cursor.getString(DBAdapter.COL_CAR_FUEL_TYPE);
                float cityMPG = cursor.getFloat(DBAdapter.COL_CAR_CITYMPG);
                float hwyMPG = cursor.getFloat(DBAdapter.COL_CAR_HWYMPG);

                Car car = new Car(make, model, year, displacement, transmission);
                car.setNickname(name);
                car.setFuelType(fuelType);
                car.setMilesPerGallonCity(cityMPG);
                car.setMilesPerGallonHway(hwyMPG);

                currentCarCollection.add(car);
            } while(cursor.moveToNext());
        }
    }

    private void loadRoutesFromDB() {
        Cursor cursor = database.getAllRouteRows();
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(DBAdapter.COL_ROUTE_NAME);
                float cityDistance = cursor.getFloat(DBAdapter.COL_ROUTE_CITYDISTANCE);
                float hwyDistance = cursor.getFloat(DBAdapter.COL_ROUTE_HWYDISTANCE);

                Route route = new Route(name, cityDistance, hwyDistance);
                routeCollection.add(route);
            } while(cursor.moveToNext());
        }
    }

    private void loadJourneysFromDB() {
        Cursor cursor = database.getAllJourneyRows();
        Transportation transportation = new Car();
        if(cursor.moveToFirst()) {
            do {
                String type = cursor.getString(DBAdapter.COL_JOURNEY_TYPE);
                switch (type) {
                    case "CAR":
                        String name = cursor.getString(DBAdapter.COL_JOURNEY_NAME);
                        String model = cursor.getString(DBAdapter.COL_JOURNEY_MODEL);
                        String make = cursor.getString(DBAdapter.COL_JOURNEY_MAKE);
                        int year = cursor.getInt(DBAdapter.COL_JOURNEY_YEAR);
                        String displacement = cursor.getString(DBAdapter.COL_JOURNEY_DISPLACEMENT_VOL);
                        String transmission = cursor.getString(DBAdapter.COL_JOURNEY_TRANSMISSION_TYPE);
                        String fuelType = cursor.getString(DBAdapter.COL_JOURNEY_FUEL_TYPE);
                        float cityMPG = cursor.getFloat(DBAdapter.COL_JOURNEY_CITYMPG);
                        float hwyMPG = cursor.getFloat(DBAdapter.COL_JOURNEY_HWYMPG);

                        Car car = new Car(make, model, year, displacement, transmission);
                        car.setNickname(name);
                        car.setFuelType(fuelType);
                        car.setMilesPerGallonCity(cityMPG);
                        car.setMilesPerGallonHway(hwyMPG);
                        transportation = car;
                        break;
                    case "BUS":
                        transportation = new Bus();
                        break;
                    case "SKYTRAIN":
                        transportation = new Skytrain();
                        break;
                    case "BIKE":
                        transportation = new Bike();
                        break;
                    case "WALK":
                        transportation = new Walk();
                        break;
                }

                String name = cursor.getString(DBAdapter.COL_JOURNEY_ROUTE_NAME);
                float cityDistance = cursor.getFloat(DBAdapter.COL_JOURNEY_CITYDISTANCE);
                float hwyDistance = cursor.getFloat(DBAdapter.COL_JOURNEY_HWYDISTANCE);

                Route route = new Route(name, cityDistance, hwyDistance);

                Journey journey = new Journey(transportation, route);
                journeyCollection.add(journey);

            } while(cursor.moveToNext());
        }
    }

    private void loadUtilitiesFromDB() {
        Cursor cursor = database.getAllUtilitiesRows();
        if(cursor.moveToFirst()) {
            do {
                String bill = cursor.getString(DBAdapter.COL_UTILITIES_BILL_TYPE);
                Utilities.BILL billType = Utilities.BILL.ELECTRICITY;
                if(bill.equals("GAS")) {
                    billType = Utilities.BILL.GAS;
                }
                float amount = cursor.getFloat(DBAdapter.COL_UTILITIES_AMOUNT);
                int startYear = cursor.getInt(DBAdapter.COL_UTILITIES_START_YEAR);
                int startMonth = cursor.getInt(DBAdapter.COL_UTILITIES_START_MONTH);
                int startDay = cursor.getInt(DBAdapter.COL_UTILITIES_START_DAY);
                int endYear = cursor.getInt(DBAdapter.COL_UTILITIES_END_YEAR);
                int endMonth = cursor.getInt(DBAdapter.COL_UTILITIES_END_MONTH);
                int endDay = cursor.getInt(DBAdapter.COL_UTILITIES_END_DAY);
                int numPpl = cursor.getInt(DBAdapter.COL_UTILITIES_NUM_PPL);

                Calendar start = Calendar.getInstance();
                start.set(startYear, startMonth, startDay);
                Calendar end = Calendar.getInstance();
                end.set(endYear, endMonth, endDay);

                Utilities utilities = new Utilities(billType, amount, start, end, numPpl);
                utilitiesCollection.add(utilities);

            } while(cursor.moveToNext());
        }
    }

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

    public boolean isCurrentCarAdded(String nickname, String description){
        for(Car car : currentCarCollection){
            if (car.getDescriptionNoNickname().equals(description)){
                return true;
            }
            if(car.getNickname().equals(nickname)){
                return true;
            }
        }
        return false;
    }

    public void addNewCarWithNickname(String nickname, String description){
        for(Car car: currentSearchCollection){
            if (car.getDescriptionNoNickname().equals(description)){
                car.setNickname(nickname);
                currentCarCollection.add(car);
                addToCarDB(car);
            }
        }
    }

    public void resetCurrentSearchCollection(){
        for(int i = 0; i < currentSearchCollection.size(); i++){
            currentSearchCollection.remove(i);
        }
    }

    public ArrayList<String> getCarEntriesDescription(RetrieveEntries mode){
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

    public void removeCar(String description){
        int index = 0;
        for(Car car: currentCarCollection){
            if(car.getDescription().equals(description)){
                index = currentCarCollection.getIndex(car);
                break;
            }
        }
        currentCarCollection.remove(index);
    }


    public void setTotalCarCollection(CarCollection totalCarCollection){
        this.totalCarCollection = totalCarCollection;

    }

    public Car getCarFromCollection(String description, RetrieveEntries mode){

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
        addToCarDB(car);
        currentCarCollection.add(car);
    }

    public void editCar(String description, Car newCar){
        int index = 0;
        for(Car car: currentCarCollection){
            if(car.getDescription().equals(description)){
                index = currentCarCollection.getIndex(car);
            }
        }
        if(currentCarCollection.isEmpty()){
            currentCarCollection.add(newCar);
        }
        else{
            currentCarCollection.add(index, newCar);
            currentCarCollection.remove(index+1);
        }
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
        addToRouteDB(route);
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

    //Choose one
    public void addNewJourney(String carDescription, String routeName){
        Car newCar = getCarFromCollection(carDescription, RetrieveEntries.Current);
        Car car = new Car(newCar);
        Route newRoute = getRouteByName(routeName);
        Route route = new Route(newRoute);
        Journey newJourney = new Journey(car, route);
        journeyCollection.add(newJourney);
        addToJourneyDB(newJourney);
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
        addToUtilitiesDB(newBill);
    }

    public void deleteUtilities(String description){
        int index = 0;
        for(Utilities utilities : utilitiesCollection){
            if(utilities.toString().equals(description)){
                index = utilitiesCollection.getIndex(utilities);
            }
        }
        utilitiesCollection.remove(index);
    }

    public void editUtilities(String originalUtilities, Utilities editedUtilities){
        int index = 0;
        for(Utilities utilities : utilitiesCollection){
            if (utilities.toString().equals(originalUtilities)){
                index = utilitiesCollection.getIndex(utilities);
                break;
            }
        }
        if(utilitiesCollection.size() == 0){
            utilitiesCollection.add(editedUtilities);
        }
        else{
            utilitiesCollection.add(index, editedUtilities);
            utilitiesCollection.remove(index+1);
        }
    }

    public ArrayList<Emission> getEmissionListOnDay(Calendar date){
        ArrayList<Emission> emissionArrayList = new ArrayList<>();
        for (Journey journey : journeyCollection) {
            int year = journey.getDate().get(Calendar.YEAR);
            int dayOfYear = journey.getDate().get(Calendar.DAY_OF_YEAR);
            if ( (date.get(Calendar.YEAR) == year) &&
                (date.get(Calendar.DAY_OF_YEAR) == dayOfYear) ){
                emissionArrayList.add(journey);
            }
        }

        for(Utilities utilities : utilitiesCollection){
            if(utilities.dateIsInBillingPeriod(date)){
                emissionArrayList.add(utilities);
            }
        }
        return emissionArrayList;
    }



    public void editRoute(String originalName, String newName, float newCity, float newHighway){
        routeCollection.editRoute(originalName, newName, newCity, newHighway);
    }

}
