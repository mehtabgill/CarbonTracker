package ca.cmpt276.carbontracker.Model;

/**
 * This class represents a Tip that's shown to the user when certain actions occur
 */

public class Tip {

    public enum ACTION_TYPE {GENERAL, ADDCAR, ADDPUBLIC}

    private String msg;
    private boolean canShow;
    private int counter; // can show when counter = 8, starts at 0 to cycle 7 tips
    ACTION_TYPE type;


    public Tip(){

    }

    public Tip(String msg, ACTION_TYPE type) {
        this.msg = msg;
        canShow = true;
        counter = 8;
        this.type = type;
    }

    public void show(){
        canShow = true;
        counter = 8;
    }

    public void hide(){
        canShow = false;
        counter = 0;
    }

    public void increment(){
        counter++;
        if (counter==8)
            canShow = true;
    }

    public boolean status(){
        return this.canShow;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }

}
