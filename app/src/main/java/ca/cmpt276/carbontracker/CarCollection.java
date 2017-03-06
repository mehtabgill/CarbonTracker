package ca.cmpt276.carbontracker;

import java.util.ArrayList;

public class CarCollection {
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<String> uniqueModelName = new ArrayList<>();
    public void add(Car car) {
        cars.add(car);
    }

    public void add(String model, String make, int year) {
        cars.add(new Car(model, make, year));
    }

    public void remove(Car car) {
        cars.remove(car);
    }

    public ArrayList<Car> getAllCars() {
        return cars;
    }

    public int size(){
        return cars.size();
    }

    public Car getCar(int index){
        return cars.get(index);
    }

    public CarCollection findCarsWithNickname(String name) {
        CarCollection temp = new CarCollection();
        for(Car car : cars) {
            if(car.getNickname().toLowerCase().equals(name.toLowerCase())) {
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

    public CarCollection findCarsWithModel(String model) {
        CarCollection temp = new CarCollection();
        for(Car car : this.cars) {
            if(car.getModel().toLowerCase().equals(model.toLowerCase())) {
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
        uniqueModelName.add(this.getCar(0).getModel());
        for(Car car: this.cars){
            boolean isUniqueModelName = true;
            for(int i = 0; i < this.uniqueModelName.size(); i++ ){
                if (car.getModel().equals(this.uniqueModelName.get(i))){
                    isUniqueModelName = false;
                }
            }
            if(isUniqueModelName){
                uniqueModelName.add(car.getModel());
            }
        }
    }

    public ArrayList<String> getUniqueModelName(){
        return this.uniqueModelName;
    }


}
