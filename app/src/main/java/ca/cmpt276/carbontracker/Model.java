package ca.cmpt276.carbontracker;

import android.support.v7.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Elvin Laptop on 2017-03-06.
 */

public class Model extends AppCompatActivity {
    CarCollection cars = new CarCollection();
    RouteCollection routes = new RouteCollection();
    InputStream is = getResources().openRawResource(R.raw.make_list_data);
    CarCollection carList = DataReader.getCarList(is);
    InputStream is1 = getResources().openRawResource(R.raw.data);
    ArrayList<String> carMakeList = DataReader.getCarMakeList(is1);

    private static final Model instance = new Model();

    //private constructor to avoid client applications to use constructor
    private Model(){}

    public static Model getInstance(){
        return instance;
    }

    public CarCollection getCarList(){
        return carList;
    }

    public ArrayList<String> getCarMakeList() {
        return carMakeList;
    }

    public CarCollection getCars(){
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCar(String make, String model, int year) {
        cars.add(make, model, year);
    }

    public Car getCar(int index) {
        return cars.getCar(index);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public void removeCar(int index) {
        cars.remove(index);
    }

    public CarCollection findCarsWithNickname(String name) {
        return cars.findCarsWithNickname(name);
    }

    public CarCollection findCarsWithModel(String model) {
        return cars.findCarsWithModel(model);
    }

    public CarCollection findCarsWithMake(String make) {
        return cars.findCarsWithMake(make);
    }

    public CarCollection findCarsWithYear(int year) {
        return cars.findCarsWithYear(year);
    }

    public int getNumCars() {
        return cars.size();
    }

    public ArrayList<String> getUniqueModelNames() {
        return cars.getUniqueModelName();
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addRoute(String name, float cityDriveDistance, float highwayDriveDistance) {
        routes.add(name, cityDriveDistance, highwayDriveDistance);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public void removeRoute(int index) {
        routes.remove(index);
    }

    public Route getRoute(int index) {
        return routes.getRoute(index);
    }

    public RouteCollection findRoutesWithName(String name) {
        return routes.findRouteWithName(name);
    }

    public RouteCollection findRoutesWithCityDistance(float cityDistance) {
        return routes.findRouteWithCityDistance(cityDistance);
    }

    public RouteCollection findRoutesWithHighwayDistance(float highwayDistance) {
        return routes.findRouteWithHighwayDistance(highwayDistance);
    }
}
