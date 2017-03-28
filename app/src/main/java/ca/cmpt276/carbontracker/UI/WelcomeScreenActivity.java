package ca.cmpt276.carbontracker.UI;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import java.io.InputStream;
import java.util.ArrayList;

import ca.cmpt276.carbontracker.Model.CarCollection;
import ca.cmpt276.carbontracker.Model.CarStorage;
import ca.cmpt276.carbontracker.Model.DataReader;
import ca.cmpt276.carbontracker.Model.SingletonModel;
/*
 * UI class for the welcome screen. It is the first screen that will appear. Some image
 * animations will play with the optino for the user to skip them. Either when the skip button is
 * pressed or when the animations finish, will the screen change to the main menu.
 */

public class WelcomeScreenActivity extends AppCompatActivity {

    private int fadeTimer = 5000; //first animation cars image fades out to be replaced
    private int slideTimer = 3000; // second animation save earth sign slides in from left
    private int dropTimer = 4000; // final animation recycle signs drops down spinning
    private int mainMenuDelay = 7000; //delay b4 main menu displays
    private static Context context;

    public void fade(View view)
    {

        //some fading animations replaced by stuff
        ImageView cars = (ImageView) findViewById(R.id.cars);
        cars.animate().alpha(0f).setDuration(fadeTimer);

        ImageView earth = (ImageView) findViewById(R.id.earth);
        earth.animate().alpha(1f).setDuration(fadeTimer).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //save earth
                ImageView save = (ImageView) findViewById(R.id.save);
                save.animate().translationXBy(1000f).setDuration(slideTimer).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ImageView recycle = (ImageView) findViewById(R.id.recycle);
                        recycle.animate().translationYBy(2000f).rotationBy(7200).setDuration(dropTimer);

                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(WelcomeScreenActivity.this, MainMenuActivity.class);
                                        startActivity(intent);
                                    }
                                },
                                mainMenuDelay
                        );
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        TextView msg1 = (TextView) findViewById(R.id.msg1);
        TextView msg2 = (TextView) findViewById(R.id.msg2);

        msg1.animate().alpha(0f).setDuration(fadeTimer);
        msg2.animate().alpha(0f).setDuration(fadeTimer);

    }



    public void skip(View view){

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    private SingletonModel model = SingletonModel.getInstance();
    private CarStorage carStorage = CarStorage.getInstance();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.closeDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        context = getApplicationContext();
        new LoadCarMakeTask().execute(getResources().openRawResource(R.raw.make_list_data));
        new LoadCarListTask().execute(getResources().openRawResource(R.raw.data));

        //get some stuff off screen
        ImageView save = (ImageView) findViewById(R.id.save);

        save.setTranslationX(-1000f);

        //recycle sign
        ImageView recycle = (ImageView) findViewById(R.id.recycle);

        recycle.setTranslationY(-2000f);
        model.openDB(context);
        model.loadDataFromDB();
    }

    public static Context getContext(){
        return context;
    }

    /*
     * LoadCarListTask and LoadCarMakeTask class read data in background
     */
    private class LoadCarMakeTask extends AsyncTask<InputStream, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(InputStream... is) {
            return DataReader.getCarMakeList(is[0]);
        }

        protected void onPostExecute(ArrayList<String> carMakeList) {
            Toast.makeText(WelcomeScreenActivity.getContext(),
                    getString(R.string.load_make_completed), Toast.LENGTH_SHORT).show();
            carStorage.setCarMakeList(carMakeList);
            DataReader.setMakeDataLoaded();
        }
    }

    private class LoadCarListTask extends AsyncTask<InputStream, Integer, CarCollection>
    {
        @Override
        protected CarCollection doInBackground(InputStream... is) {
            return DataReader.getCarList(is[0]);
        }

        protected void onPostExecute(CarCollection carCollection){
            Toast.makeText(WelcomeScreenActivity.getContext(),
                    getString(R.string.load_car_completed), Toast.LENGTH_SHORT).show();
            carStorage.setTotalCarCollection(carCollection);
            DataReader.setFullDataLoaded();

        }
    }
}