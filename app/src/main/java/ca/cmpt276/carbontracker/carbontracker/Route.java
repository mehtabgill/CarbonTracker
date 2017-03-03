package ca.cmpt276.carbontracker.carbontracker;

public class Route {
    private String name;
    private float cityDriveDistance;
    private float highwayDriveDistance;

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
    }

    public float getHighwayDriveDistance() {
        return highwayDriveDistance;
    }

    public void setHighwayDriveDistance(float highwayDriveDistance) {
        this.highwayDriveDistance = highwayDriveDistance;
    }


}
