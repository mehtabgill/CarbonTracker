package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 *
 */

public class UtilitiesCollection implements Iterable<Utilities> {
    private ArrayList<Utilities> utilityBillsCollection = new ArrayList<>();
    public void add(Utilities utilities){
        utilityBillsCollection.add(utilities);
    }
    public void add(int index, Utilities utilities){
        utilityBillsCollection.add(index, utilities);
    }
    public void remove(int index){
        utilityBillsCollection.remove(index);
    }
    public void remove(Utilities utilities){
        utilityBillsCollection.remove(utilities);
    }

    public void set(int index, Utilities utilities){
        utilityBillsCollection.set(index, utilities);
    }

    public int size(){
        return utilityBillsCollection.size();
    }
    public ArrayList<Utilities> getAllUtilityBills(){
        return utilityBillsCollection;
    }
    public UtilitiesCollection getUtilityBillsByDate(Calendar date){
        UtilitiesCollection tempCollection = new UtilitiesCollection();
        for(Utilities utilities : utilityBillsCollection){
            if(utilities.dateIsInBillingPeriod(date)){
                tempCollection.add(utilities);
            }
        }
        return tempCollection;
    }

    //function for finding emission value of one specific day,
    //journeyCollection probably would need one too
    public float getCarbonEmissionValue(Calendar date){
        float emissionValue = 0;
        UtilitiesCollection tempCollection = this.getUtilityBillsByDate(date);
        for(Utilities utilities : tempCollection) {
            emissionValue += utilities.getDailyAverageEmission();
        }
        return emissionValue;
    }

    public void set(int index, Utilities utilities) {
        utilityBillsCollection.set(index, utilities);
    }

    public int getIndex(Utilities utilities){
        int index = -1;
        for(int i = 0; i < utilityBillsCollection.size(); i++){
            if (utilityBillsCollection.get(i).equals(utilities)){
                index = i;
            }
        }
        return index;
    }

    @Override
    public Iterator<Utilities> iterator(){
        return utilityBillsCollection.iterator();
    }
}
