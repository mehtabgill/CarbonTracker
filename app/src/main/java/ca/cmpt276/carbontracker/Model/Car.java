package ca.cmpt276.carbontracker.Model;


/*
 * Store all car information
 */
public class Car extends Transportation{
    private String nickname;
    private String model;
    private String make;
    private int year;
    private String additionalInfo;
    private String fuelType;
    private String description;
    private String descriptionNoNickname;
    private int milesPerGallonCity;
    private int milesPerGallonHway;

    public Car(){
    };

    public Car(String make, String model, int year, String additionalInfo) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.additionalInfo = additionalInfo;
        this.descriptionNoNickname = make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
        description = nickname + " - " +
                make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        this.description = nickname + " - " +
                make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
        this.descriptionNoNickname = make + " " +
                model + " " +
                year + " " +
                additionalInfo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
        this.description = nickname + " - " +
                make + " " +
                model + " " +
                year + " " +
                additionalInfo;
        this.descriptionNoNickname = make + " " +
                model + " " +
                year + " " +
                additionalInfo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
        this.description = nickname + " - " +
                make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
        this.descriptionNoNickname = make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        this.description = nickname + " - " +
                make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
        this.descriptionNoNickname = make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        this.description = nickname + " - " +
                make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
        this.descriptionNoNickname = make + " " +
                model + " " +
                Integer.toString(year) + " " +
                additionalInfo;
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

    public void setDescription(String nickname, String make, String model, String year, String additionalInfo){
        this.description = nickname + " - " +
                make + " " +
                model + " " +
                year + " " +
                additionalInfo;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescriptionNoNickName(String make, String model, String year, String additionalInfo){
        this.descriptionNoNickname = make + " " +
                model + " " +
                year + " " +
                additionalInfo;
    }

    public String getDescriptionNoNickname(){
        return descriptionNoNickname;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }


}
