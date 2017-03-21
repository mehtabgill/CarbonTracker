package ca.cmpt276.carbontracker.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Utilities extends Emission {
    public enum BILL {ELECTRICITY, GAS};
    private Utilities.BILL billMode;
    private int numberOfPeople;
    private int billingDays;
    private float electricityAmount; //Unit: kWh
    private float gasAmount; //Unit: Gj
    private Calendar startDate;
    private Calendar endDate;
    private float carbonEmissionValue;
    private float dailyAverageEmission;
    private static final float C02_KG_PER_KWH = 0.009f;
    private static final float C02_KG_PER_GJ = 56.1f;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public Utilities(){}

    public Utilities(BILL bill, float amount, Calendar startDate, Calendar endDate, int numbOfPeople){
        this.billMode = bill;
        if(billMode.equals(BILL.ELECTRICITY)){
            this.electricityAmount = amount;
            this.gasAmount = 0;
        }
        else{
            this.gasAmount = amount;
            this.electricityAmount = 0;
        }

        sdf.setCalendar(new GregorianCalendar());
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfPeople = numbOfPeople;
        calculateBillingDays();
        calculateCarbonEmission();
    }

    @Override
    protected void calculateCarbonEmission() {
        switch (this.billMode){
            case ELECTRICITY:
                this.carbonEmissionValue = (this.electricityAmount * C02_KG_PER_KWH) / this.numberOfPeople;
                break;
            case GAS:
                this.carbonEmissionValue = (this.gasAmount * C02_KG_PER_GJ) / this.numberOfPeople;
                break;
        }
        this.dailyAverageEmission = this.carbonEmissionValue / billingDays;
    }

    private void calculateBillingDays(){
         if(startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)){
             billingDays = Math.abs( endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR) );
         }
         else{
             if(startDate.get(Calendar.YEAR) > endDate.get(Calendar.YEAR)){
                 Calendar temp = startDate;
                 startDate = endDate;
                 endDate = temp;
             }

             Calendar start = (Calendar) startDate.clone();
             Calendar end = (Calendar) endDate.clone();
             int daysLeftInStartYear = Math.abs(startDate.getActualMaximum(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR));
             int daysPassedInEndYear = endDate.get(Calendar.DAY_OF_YEAR);
             int daysInBetweenYears = 0;
             while(start.get(Calendar.YEAR) < end.get(Calendar.YEAR)){
                 start.add(Calendar.YEAR, 1);
                 daysInBetweenYears += start.getActualMaximum(Calendar.DAY_OF_YEAR);
             }
             billingDays = daysLeftInStartYear + daysInBetweenYears + daysPassedInEndYear;
         }
    }

    public boolean dateIsInBillingPeriod(Calendar date){
        Calendar currentCheckingDate = (Calendar) startDate.clone();
        Calendar endDate = (Calendar) this.endDate.clone();
        while(!currentCheckingDate.equals(endDate)){
            if(currentCheckingDate.equals(date)){
                return true;
            }

            else{
                currentCheckingDate.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return false;
    }

    public BILL getBillMode() {
        return billMode;
    }

    public void setBillMode(BILL billMode) {
        this.billMode = billMode;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getBillingDays() {
        return billingDays;
    }

    public float getElectricityAmount() {
        return electricityAmount;
    }

    public void setElectricityAmount(float electricityAmount) {
        this.electricityAmount = electricityAmount;
    }

    public float getGasAmount() {
        return gasAmount;
    }

    public void setGasAmount(float gasAmount) {
        this.gasAmount = gasAmount;
    }


    @Override
    public float getCarbonEmissionValue() {
        return carbonEmissionValue;
    }

    public float getDailyAverageEmission() {
        return dailyAverageEmission;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
        calculateBillingDays();
        calculateCarbonEmission();
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
        calculateBillingDays();
        calculateCarbonEmission();
    }

}
