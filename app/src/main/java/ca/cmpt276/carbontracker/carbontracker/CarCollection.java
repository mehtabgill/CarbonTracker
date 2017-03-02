package ca.cmpt276.carbontracker.carbontracker;

import java.util.ArrayList;

public class CarCollection {
    private ArrayList<Car> cars = new ArrayList<>();

    public void add(Car car) {
        cars.add(car);
    }

    public void add(String name, String model, String make, int year) {
        cars.add(new Car(name, model, make, year));
    }

    public void remove(Car car) {
        cars.remove(car);
    }

    public ArrayList<Car> getAllCars() {
        return cars;
    }

    public ArrayList<Car> findCarsWithName(String name) {
        ArrayList<Car> temp = new ArrayList<>();
        for(Car car : cars) {
            if(car.getName().toLowerCase().equals(name.toLowerCase())) {
                temp.add(car);
            }
        }
        return temp;
    }

    public ArrayList<Car> findCarsWithModel(String model) {
        ArrayList<Car> temp = new ArrayList<>();
        for(Car car : cars) {
            if(car.getModel().toLowerCase().equals(model.toLowerCase())) {
                temp.add(car);
            }
        }
        return temp;
    }

    public ArrayList<Car> findCarsWithMake(String make) {
        ArrayList<Car> temp = new ArrayList<>();
        for(Car car : cars) {
            if(car.getMake().toLowerCase().equals(make.toLowerCase())) {
                temp.add(car);
            }
        }
        return temp;
    }
}
