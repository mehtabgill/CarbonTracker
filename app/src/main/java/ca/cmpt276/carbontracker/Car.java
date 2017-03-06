package ca.cmpt276.carbontracker;


public class Car {
    private String nickname;
    private String model;
    private String make;
    private int year;
    private String additionalInfo;
    private String fuelType;

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    private int milesPerGallonCity;
    private int milesPerGallonHway;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getMilesPerGallonCity() {
        return milesPerGallonCity;
    }

    public void setMilesPerGallonCity(int milesPerGallonCity) {
        this.milesPerGallonCity = milesPerGallonCity;
    }

    public int getMilesPerGallonHway() {
        return milesPerGallonHway;
    }

    public void setMilesPerGallonHway(int milesPerGallonHway) {
        this.milesPerGallonHway = milesPerGallonHway;
    }
}
