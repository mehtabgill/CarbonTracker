package ca.cmpt276.carbontracker.carbontracker;


public class Car {
    private String name;
    private String model;
    private String make;
    private int year;

    public Car(String name, String model, String make, int year) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
