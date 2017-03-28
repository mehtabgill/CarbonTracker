package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;

/**
 *
 */

public class CarStorage {
    private CarCollection currentSearchCollection = new CarCollection();
    private CarCollection totalCarCollection = new CarCollection();
    private CarCollection currentSearchPreviousState = new CarCollection();
    private ArrayList<String> carMakeList;

    private static final CarStorage instance = new CarStorage();

    private CarStorage(){};

    public static CarStorage getInstance(){
        return instance;
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

    public ArrayList<String> getCarSearchList(){
        ArrayList<String> carEntriesDescription = new ArrayList<>();
        carEntriesDescription = currentSearchCollection.getDescriptionNoNickNameList();
        return carEntriesDescription;
    }

    public Car getCarFromSearchList(int index){
        return currentSearchCollection.getCar(index);
    }

    public void resetCurrentSearchCollection(){
        currentSearchCollection = new CarCollection();
    }

    public void setTotalCarCollection(CarCollection totalCarCollection){
        if(this.totalCarCollection.isEmpty()){
            this.totalCarCollection = totalCarCollection;
        }
    }

    public ArrayList<String> getCarMakeList() {
        return carMakeList;
    }

    public void setCarMakeList(ArrayList<String> carMakeList) {
        this.carMakeList = carMakeList;
    }

}
