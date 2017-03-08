package ca.cmpt276.carbontracker;

import java.util.ArrayList;
import java.util.Iterator;

public class CarCollection implements Iterable<Car> {
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<String> uniqueModelNames = new ArrayList<>();
    private ArrayList<String> uniqueModelYears = new ArrayList<>();
    ;
    private ArrayList<String> uniqueYears = new ArrayList<>();
    private boolean isEmpty;

    public CarCollection() {
        isEmpty = true;
    }

    public void add(String make, String model, int year) {
        if (cars.isEmpty()) {
            isEmpty = false;
        }
        cars.add(new Car(make, model, year));
    }

    public void add(Car car) {
        if (cars.isEmpty()) {
            isEmpty = false;
        }
        cars.add(car);
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

    public CarCollection findCarsWithNickname(String name) {
        CarCollection temp = new CarCollection();
        for (Car car : cars) {
            if (car.getNickname().toLowerCase().equals(name.toLowerCase())) {
                temp.add(car);
            }
        }
        return temp;
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

    public ArrayList<String> getUniqueModelNames(){
        return this.uniqueModelNames;
    }

    public void searchUniqueModelYears(){
        if(this.size() == 0){

        }
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

    public ArrayList<String> getUniqueModelYears(){
        return this.uniqueModelYears;
    }

    @Override
    public Iterator<Car> iterator() {
        return cars.iterator();
    }
}
