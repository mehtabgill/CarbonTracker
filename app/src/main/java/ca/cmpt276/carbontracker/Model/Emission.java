package ca.cmpt276.carbontracker.Model;

public abstract class Emission {
    private float carbonEmissionValue;
    public float getCarbonEmissionValue(){
        return this.carbonEmissionValue;
    }
    protected abstract void calculateCarbonEmission();
}
