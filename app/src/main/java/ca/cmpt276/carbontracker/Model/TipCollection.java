package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;

/**
 * Holds all tips
 */



public class TipCollection {
    final int INITIAL_CAPACITY = 15;

    ArrayList<Tip> tips = new ArrayList<>(INITIAL_CAPACITY);


    public void add(Tip tip){
        tips.add(tip);
    }

    public void add(String msg, Tip.ACTION_TYPE type)
    {
        tips.add(new Tip(msg, type));
    }

    public void remove(Tip tip)
    {
        tips.remove(tip);
    }

    public void remove(int i)
    {
        tips.remove(i);
    }

    public int size(){
        return tips.size();
    }

    public void incrementAll(){
        for(Tip tip: tips)
        {
            tip.increment();
        }
    }

    public Tip getNextBestTip(Tip.ACTION_TYPE type){
        Tip output = new Tip();
        for(Tip tip: tips)
        {
            if(tip.status() && (tip.type == type))
                output = tip;
        }

        if (output.getMsg()==null)
        {
            for(Tip tip: tips)
            {
                if(tip.status())
                    output = tip;
            }
        }

        return output;
    }

}
