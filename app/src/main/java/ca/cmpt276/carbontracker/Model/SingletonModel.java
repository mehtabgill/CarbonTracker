package ca.cmpt276.carbontracker.Model;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ca.cmpt276.carbontracker.UI.R;
import ca.cmpt276.carbontracker.UI.ViewJourneyActivity;

/**
 * Created by Elvin Laptop on 2017-03-06.
 */

public class SingletonModel {
    //TODO: Move all code relating to car search to their own class
    //Singleton should only be the middle ground between logic and UI

    Context context;

    private CarCollection currentCarCollection = new CarCollection();
    private RouteCollection routeCollection = new RouteCollection();
    private JourneyCollection journeyCollection = new JourneyCollection();
    private UtilitiesCollection utilitiesCollection = new UtilitiesCollection();

    private ArrayList<String> carMakeList;
    public enum RetrieveEntries {Current, Search, Total};

    private final boolean TESTING_MODE = true;

    private static final SingletonModel instance = new SingletonModel();

    private DBAdapter database;

    //private constructor to avoid client applications to use constructor
    private SingletonModel(){}

    public static SingletonModel getInstance(){
        return instance;
    }


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

    public boolean inTestingMode() {
        return TESTING_MODE;
    }

    public void deleteAllDataFromDB() {
        if(TESTING_MODE) {
            //database.deleteAllCar();
            database.deleteAllJourney();
            //database.deleteAllRoute();
            database.deleteAllUtilities();
            database.deleteAllTotal();
        }
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
        cursor.close();
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
        cursor.close();
    }

    private void loadJourneysFromDB() {
        Cursor cursor = database.getAllJourneyRows();
        Transportation transportation = new Car();
        if(cursor.moveToFirst()) {
            do {
                Car car = new Car();
                String type = cursor.getString(DBAdapter.COL_JOURNEY_TYPE);
                switch (type) {
                    case "CAR":
                        String name = cursor.getString(DBAdapter.COL_JOURNEY_NAME);
                        String model = cursor.getString(DBAdapter.COL_JOURNEY_MODEL);
                        String make = cursor.getString(DBAdapter.COL_JOURNEY_MAKE);
                        int carYear = cursor.getInt(DBAdapter.COL_JOURNEY_CAR_YEAR);
                        String displacement = cursor.getString(DBAdapter.COL_JOURNEY_DISPLACEMENT_VOL);
                        String transmission = cursor.getString(DBAdapter.COL_JOURNEY_TRANSMISSION_TYPE);
                        String fuelType = cursor.getString(DBAdapter.COL_JOURNEY_FUEL_TYPE);
                        float cityMPG = cursor.getFloat(DBAdapter.COL_JOURNEY_CITYMPG);
                        float hwyMPG = cursor.getFloat(DBAdapter.COL_JOURNEY_HWYMPG);

                        car = new Car(make, model, carYear, displacement, transmission);
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

                int year = cursor.getInt(DBAdapter.COL_JOURNEY_YEAR);
                int month = cursor.getInt(DBAdapter.COL_JOURNEY_MONTH);
                int day = cursor.getInt(DBAdapter.COL_JOURNEY_DAY);

                Calendar date = Calendar.getInstance();
                date.set(year, month, day);

                Journey journey = new Journey();
                if(type == Transportation.TRANSPORTATION_TYPE.CAR.toString()) {
                    journey = new Journey(car, route);
                }
                else {
                    journey = new Journey(transportation, route);
                }
                journey.setDate(date);
                journeyCollection.add(journey);

            } while(cursor.moveToNext());
        }
        cursor.close();
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
        cursor.close();
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

    public ArrayList<String> getCarEntriesDescription(){
        return currentCarCollection.getDescriptionList();
    }

    /*public void addNewCarWithNickname(String nickname, String description){
        for(Car car: currentSearchCollection){
            if (car.getDescriptionNoNickname().equals(description)){
                car.setNickname(nickname);
                currentCarCollection.add(car);
                addToCarDB(car);
            }
        }
    }*/
    /*
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
*/

    public Car getCar(String description){
        Car returnCar = new Car();
        String current;
        int compareValue;
        for (Car car : currentCarCollection) {
            current = car.getDescription();
            compareValue = description.compareTo(current);
            if (compareValue == 0) {
                returnCar = car;
            }
        }
        return returnCar;
    }

    public Car getCar(int index){
        Car returnCar = currentCarCollection.getCar(index);
        return returnCar;
    }

    //TODO: Rename this
    public int getCarIndex(Car car){
        return currentCarCollection.getIndex(car);
    }

    public CarCollection getAllCurrentCars(){
        return currentCarCollection;
    }

    //TODO: Rename this
    public void addCar(Car car){
        addToCarDB(car);
        currentCarCollection.add(car);
    }

    /*public void editCar(String description, Car newCar){
        int index;
        for(Car car: currentCarCollection){
            if(car.getDescription().equals(description)){
                index = currentCarCollection.getIndex(car);
                currentCarCollection.set(index, newCar);
                long id = database.findCar(car);
                database.updateCar(id, newCar);
            }
        }
    }*/

    public void editCar(int index, Car newCar){
        currentCarCollection.set(index, newCar);
        database.updateCar(index, newCar);
    }

   /* public void removeCar(String description){
        int index = 0;
        for(Car car: currentCarCollection){
            if(car.getDescription().equals(description)){
                index = currentCarCollection.getIndex(car);
                long id = database.findCar(car);
                database.deleteCarRow(id);
                break;
            }
        }
        currentCarCollection.remove(index);
    }*/

    public void removeCar(int index){
        Car car = currentCarCollection.getCar(index);
        long id = database.findCar(car);
        database.deleteCarRow(id);
        currentCarCollection.remove(car);
    }

    //TODO: Rename this
    public Route getRoute(String name){
        Route returnRoute = new Route();
        for (Route route: routeCollection){
            if(route.getName().toLowerCase().equals(name.toLowerCase())){
                returnRoute = route;
            }
        }
        return returnRoute;
    }
    public Route getRoute(int index) {
        return routeCollection.getRoute(index);
    }

    public RouteCollection getRouteCollection() {
        return routeCollection;
    }

    //TODO: Rename this
    public void addRoute(Route route){
        addToRouteDB(route);
        routeCollection.add(route);
    }

    public void removeRoute(int index){
        Route route = routeCollection.getRoute(index);
        long id = database.findRoute(route);
        database.deleteRouteRow(id);
        routeCollection.remove(index);
    }

    public void editRoute(int index, String newName, float newCity, float newHighway){
        Route route = new Route(newName, newCity, newHighway);
        Route oldRoute = routeCollection.getRoute(index);
        long id = database.findRoute(oldRoute);
        database.updateRoute(id, route);
        routeCollection.editRoute(index, route);
    }

    public void editRoute(int index, Route newRoute){
        Route oldRoute = routeCollection.getRoute(index);
        long id = database.findRoute(oldRoute);
        routeCollection.editRoute(index, newRoute);
        database.updateRoute(id, newRoute);
    }

    public ArrayList<String> getRouteCollectionNames(){
        ArrayList<String> routeNames = new ArrayList<>();
        for(Route route: routeCollection){
            routeNames.add(route.getName());
        }
        return routeNames;
    }

/*
    public void addNewJourney(String carDescription, String routeName){
        Car newCar = getCar(carDescription);
        Car car = new Car(newCar);
        Route newRoute = getRoute(routeName);
        Route route = new Route(newRoute);
        Journey newJourney = new Journey(car, route);
        journeyCollection.add(newJourney);
        addToJourneyDB(newJourney);
    }*/

    public void addNewJourney(Transportation newTransportation, Route newRoute){
        Journey newJourney = new Journey(newTransportation, newRoute);
        journeyCollection.add(newJourney);
        addToJourneyDB(newJourney);
    }

    public void removeJourney(int index) {
        long id = database.findJourney(journeyCollection.get(index));
        database.deleteJourneyRow(id);
        journeyCollection.remove(index);
    }

    public void setRouteOfJourneyAt(int index, Route route) {
        long id = database.findJourney(journeyCollection.get(index));
        journeyCollection.setRouteAt(index, route);
        database.updateJourney(id, journeyCollection.get(index));
    }

    public void setTransportationOfJourneyAt(int index, Transportation transportation) {
        Journey oldJourney = journeyCollection.get(index);
        long id = database.findJourney(oldJourney);
        journeyCollection.setTransportationAt(index, transportation);
        Journey newJourney = journeyCollection.get(index);
        database.updateJourney(id, newJourney);
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


    public void addNewUtilities(Utilities.BILL billType, float amount, Calendar startDate, Calendar endDate, int numberOfPeople){
        Utilities newUtilities = new Utilities(billType, amount, startDate, endDate, numberOfPeople);
        utilitiesCollection.add(newUtilities);
        addToUtilitiesDB(newUtilities);
    }

    public void addNewUtilities(Utilities newUtilities){
        utilitiesCollection.add(newUtilities);
        addToUtilitiesDB(newUtilities);
    }

    public void removeUtilities(String description){
        int index = 0;
        for(Utilities utilities : utilitiesCollection){
            if(utilities.toString().equals(description)){
                long id = database.findUtilities(utilities);
                database.deleteUtilitiesRow(id);
                index = utilitiesCollection.getIndex(utilities);
            }
        }
        utilitiesCollection.remove(index);
    }

    public void removeUtilities(int index){
        Utilities currentUtilities = utilitiesCollection.getUtilities(index);
        long id = database.findUtilities(currentUtilities);
        database.deleteUtilitiesRow(id);
        utilitiesCollection.remove(index);
    }

    public void editUtilities(String originalUtilities, Utilities editedUtilities){
        int index = 0;
        for(Utilities utilities : utilitiesCollection){
            if (utilities.toString().equals(originalUtilities)){
                index = utilitiesCollection.getIndex(utilities);
                utilitiesCollection.set(index, editedUtilities);
                long id = database.findUtilities(utilities);
                database.updateUtilities(id, editedUtilities);
            }
        }
    }

    public void editUtilities(int index, Utilities editedUtilities){
        Utilities currentUtilities = utilitiesCollection.getUtilities(index);
        long id = database.findUtilities(currentUtilities);
        utilitiesCollection.set(index, editedUtilities);
        database.updateUtilities(id, editedUtilities);
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

    public Utilities getRelativeUtilitiesValue(Calendar date, Utilities.BILL billType){
        Utilities relativeUtilities = new Utilities();
        Calendar dateForward = (Calendar) date.clone();
        Calendar dateBackward = (Calendar) date.clone();
        int maxDayDistance = 90;
        int dayDistance = 1;

        while (dayDistance <= maxDayDistance){
            dateForward.add(Calendar.DATE, dayDistance);
            dateBackward.add(Calendar.DATE, -dayDistance);
            for(Utilities utilities : utilitiesCollection){
                if ((utilities.dateIsInBillingPeriod(dateForward)) ||
                        (utilities.dateIsInBillingPeriod(dateBackward)) ){
                    if (utilities.getBill().equals(billType)){
                        relativeUtilities = utilities;
                    }
                }
            }
            dayDistance++;
        }
        return relativeUtilities;
    }

    public int getWalks(){
        return journeyCollection.getNumWalk();
    }

    public int getBike(){
        return journeyCollection.getNumBike();
    }

    public int getBus(){
        return journeyCollection.getNumBus();
    }

    public int getSkytrain() {
        return journeyCollection.getNumSkytrain();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String[]> getJourneyData() {
        ArrayList<String[]> list = new ArrayList<>();
        for(Journey journey : journeyCollection) {
            String[] temp = new String[ViewJourneyActivity.NUM_COLUMNS];
            Transportation transportation = journey.getTransportation();
            Transportation.TRANSPORTATION_TYPE type = transportation.getType();
            String transportationType = Transportation.getStringOfType(type);
            if(transportationType.equals(Transportation.TYPE[Transportation.TRANSPORTATION_TYPE.CAR.ordinal()])){
                Car car = (Car) transportation;
                String name = car.getNickname();
                transportationType += " - " + name;
            }
            //TODO: Implement image icon for vehicles
            //temp[ViewJourneyActivity.COL_IMAGE] = journey.getImageIcon();
            temp[ViewJourneyActivity.COL_TRANSPORTATION] = transportationType;
            temp[ViewJourneyActivity.COL_DISTANCE] = String.valueOf(journey.getDistance()) + context.getString(R.string.KM);
            temp[ViewJourneyActivity.COL_DATE] = journey.getStringDate();
            temp[ViewJourneyActivity.COL_CO2] = String.valueOf(journey.getCarbonEmissionValue()) + context.getString(R.string.KG);
            list.add(temp);
        }
        return list;
    }

    public Transportation getTransportationOfJourneyAt(int index) {
        return journeyCollection.get(index).getTransportation();
    }
    public Route getRouteOfJourneyAt(int index) {
        return journeyCollection.get(index).getRoute();
    }
}
