package ca.cmpt276.carbontracker.Model;

/*
 * Store information of a Route object
 */
public class Route {
    private String name;
    private float cityDriveDistance;
    private float highwayDriveDistance;
    private float totalDistance;

    //default blank constructor
    public Route(){

    }
    public Route(float totalDistance){
        name ="";
        cityDriveDistance = 0;
        highwayDriveDistance=0;
        this.totalDistance = getTotalDistance();
    };

    public Route(Route route) {
        this.name = route.name;
        this.cityDriveDistance = route.cityDriveDistance;
        this.highwayDriveDistance = route.highwayDriveDistance;
        this.totalDistance = route.totalDistance;
    }

    public Route(String name, float cityDriveDistance, float highwayDriveDistance) {
        this.name = name;
        this.cityDriveDistance = cityDriveDistance;
        this.highwayDriveDistance = highwayDriveDistance;
        this.totalDistance = this.cityDriveDistance + this.highwayDriveDistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCityDriveDistance() {
        return cityDriveDistance;
    }

    public void setCityDriveDistance(float cityDriveDistance) {
        this.cityDriveDistance = cityDriveDistance;
        this.totalDistance = this.cityDriveDistance + this.highwayDriveDistance;

    }



    public float getHighwayDriveDistance() {
        return highwayDriveDistance;
    }

    public void setHighwayDriveDistance(float highwayDriveDistance) {
        this.highwayDriveDistance = highwayDriveDistance;
        this.totalDistance = this.cityDriveDistance + this.highwayDriveDistance;
    }

    public float getTotalDistance(){
        return this.totalDistance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
