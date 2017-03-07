package ca.cmpt276.carbontracker;

/**
 * Keeps a collection of Journey objects
 */

public class JourneyCollection {

    final int NUMBER_OF_JOURNEYS = 10;
    private int top; //current number of journeys
    private Journey journeyCollection[];

    public JourneyCollection(){
        top = 0;
        journeyCollection = new Journey[NUMBER_OF_JOURNEYS];
    }

    public int getLength(){
        return top;
    }

    public boolean isEmpty(){
        if (top==0)
        {
            return true;
        }
        else
            return false;
    }

    //if run out of space extends the array
    private void extendCollection(){

            //double the array
            Journey[] temp;

            temp = new Journey[journeyCollection.length*2];

            for(int i = 0; i<getLength(); i++)
            {
                temp[i] = journeyCollection[i];
            }

            journeyCollection = temp;

    }

    public void addJourney(Journey journey){
        if(top == journeyCollection.length)
        {
            extendCollection();
        }

        journeyCollection[top] = journey;
        top++;

    }

    public  void removeJourney(){
        if (!isEmpty())
        {
            journeyCollection[top] = null;
            top--;
        }
    }



}
