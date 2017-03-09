package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;
import java.util.Iterator;

public class CarCollection implements Iterable<Car> {
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<String> uniqueModelNames = new ArrayList<>();
    private ArrayList<String> uniqueModelYears = new ArrayList<>();
    private ArrayList<String> descriptionList = new ArrayList<>();
    private ArrayList<String> descriptionNoNickNameList = new ArrayList<>();
    private ArrayList<String> uniqueYears = new ArrayList<>();
    private boolean isEmpty;

    public CarCollection() {
        isEmpty = true;
    }

    public void add(Car car) {
        if (cars.isEmpty()) {
            isEmpty = false;
        }
        cars.add(car);
        descriptionList.add(car.getDescription());
        descriptionNoNickNameList.add(car.getDescriptionNoNickame());
    }

    public void remove(Car car) {
        cars.remove(car);
    }

    public void remove(int index) {
        cars.remove(index);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int size() {
        return cars.size();
    }

    public Car getCar(int index) {
        return cars.get(index);
    }

    public void setCarAtIndex(Car car, int index){
        cars.remove(index);
        cars.add(index, car);
    }

    public int getIndex(Car car){
        for(int i = 0; i < cars.size(); i++){
            if (cars.get(i).equals(car)){
                return i;
            }
        }
        return -1;
    }

    public Car findCarsWithNickname(String name) {
        Car car = new Car();
        for (Car currentCar : cars) {
            if (currentCar.getNickname().toLowerCase().equals(name.toLowerCase())) {
                car = currentCar;
            }
        }
        return car;
    }

    public CarCollection findCarsWithModel(String model) {
        CarCollection temp = new CarCollection();
        for(Car car : this.cars) {
            if(car.getModel().toLowerCase().equals(model.toLowerCase())) {
                temp.add(car);
            }
        }
        return temp;
    }

    public CarCollection findCarsWithYear(int year) {
        CarCollection temp = new CarCollection();
        for(Car car : this.cars) {
            if(car.getYear() == year) {
                temp.add(car);
            }
        }
        return temp;
    }

    public CarCollection findCarsWithMake(String make) {
        CarCollection temp = new CarCollection();
        for(Car car : cars) {
            if(car.getMake().toLowerCase().equals(make.toLowerCase())) {
                temp.add(car);
            }
        }
        return temp;
    }

    /*
     * Look in current CarCollection and remove any duplicate model name,
     * storing them into UniqueModelName string arraylist
     */
    public void searchUniqueModelName(){
        if(!(this.size() == 0)){
            uniqueModelNames.add(this.getCar(0).getModel());
            for(Car car: this.cars){
                boolean isUniqueModelName = true;
                for(String uniqueModel: uniqueModelNames){
                    if (car.getModel().equals(uniqueModel)){
                        isUniqueModelName = false;
                    }
                }
                if(isUniqueModelName){
                    uniqueModelNames.add(car.getModel());
                }
            }
        }
    }


    public void searchUniqueModelYears(){
        uniqueModelYears.add(Integer.toString(this.getCar(0).getYear()));
        for(Car car: this.cars){
            boolean isUniqueModelYear = true;
            for(String uniqueYear: uniqueModelYears){
                if (Integer.toString(car.getYear()).equals(uniqueYear)){
                    isUniqueModelYear = false;
                }
            }
            if(isUniqueModelYear){
                uniqueModelYears.add(Integer.toString(car.getYear()));
            }
        }
    }

    public CarCollection getDuplicate(){
        CarCollection temp = new CarCollection();
        for(Car car : this.cars){
            temp.add(car);
        }
        return temp;
    }

    public ArrayList<String> getUniqueModelNames(){
        return this.uniqueModelNames;
    }

    public ArrayList<String> getUniqueModelYears(){
        return this.uniqueModelYears;
    }

    public ArrayList<String> getDescriptionList(){
        return this.descriptionList;
    }

    public ArrayList<String> getDescriptionNoNickNameList() {
        return descriptionNoNickNameList;
    }

    public void setDescriptionList(ArrayList<String> descriptionList){
        this.descriptionList = descriptionList;
    }
    public void setDescriptionNoNicknameList(ArrayList<String> descriptionNoNicknameList){
        this.descriptionNoNickNameList = descriptionNoNicknameList;
    }

    @Override
    public Iterator<Car> iterator() {
        return cars.iterator();
    }
}
