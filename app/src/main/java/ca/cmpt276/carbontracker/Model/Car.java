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
    private String displacementVol;
    private String transmissionType;
    private String fuelType;
    private String description;
    private String descriptionNoNickname;
    private float milesPerGallonCity;
    private float milesPerGallonHway;


    public Car(){
    };

    public Car(String make, String model, int year, String displacementVol, String transmissionType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.displacementVol = displacementVol;
        this.transmissionType = transmissionType;
        this.additionalInfo = displacementVol + " L " + transmissionType ;
        updateCarDescriptions();
    }

    public Car(Car car) {
        this.make = car.make;
        this.model = car.model;
        this.year = car.year;
        this.displacementVol = car.displacementVol;
        this.transmissionType = car.transmissionType;
        this.additionalInfo = car.displacementVol + " L " + car.transmissionType ;
        this.milesPerGallonCity = car.milesPerGallonCity;
        this.milesPerGallonHway = car.milesPerGallonHway;
        this.fuelType = car.fuelType;
        if((car.nickname != null) && (!car.nickname.isEmpty())){
            this.nickname = car.getNickname();
        }
        updateCarDescriptions();
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        updateCarDescriptions();
    }

    public String getModel() {
        return model;
    }


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
        updateCarDescriptions();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        updateCarDescriptions();
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String displacementVolume, String transmissionType) {
        this.additionalInfo = displacementVolume + " L " + transmissionType;
        updateCarDescriptions();
    }

    private void updateCarDescriptions() {
        this.description = this.nickname + " - " +
                this.make + " " +
                this.model + " " +
                Integer.toString(this.year) + " " +
                this.additionalInfo;
        this.descriptionNoNickname = this.make + " " +
                this.model + " " +
                Integer.toString(this.year) + " " +
                this.additionalInfo;
    }

    public float getMilesPerGallonCity() {
        return milesPerGallonCity;
    }

    public void setMilesPerGallonCity(float milesPerGallonCity) {
        this.milesPerGallonCity = milesPerGallonCity;
        updateCarDescriptions();
    }

    public float getMilesPerGallonHway() {
        return milesPerGallonHway;
    }

    public void setMilesPerGallonHway(float milesPerGallonHway) {
        this.milesPerGallonHway = milesPerGallonHway;
        updateCarDescriptions();
    }

    public void setDescription(String nickname, String make, String model, String year, String displacementVolume, String transmissionType){
        additionalInfo = displacementVolume + transmissionType;
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

    @Override
    public Transportation.TRANSPORTATION_TYPE getType(){
        return TRANSPORTATION_TYPE.CAR;
    }

    public String getDisplacementVol() {
        return displacementVol;
    }

    public String getTransmissionType() {
        return transmissionType;
    }
}
