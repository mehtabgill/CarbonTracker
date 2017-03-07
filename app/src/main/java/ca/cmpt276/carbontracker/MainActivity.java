package ca.cmpt276.carbontracker;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/*
This class is for the welcome screen. It is the first screen that will appear. Some image
animations will play with the optino for the user to skip them. Either when the skip button is
pressed or when the animations finish, will the screen change to the main menu.
 */

public class MainActivity extends AppCompatActivity {

    private int fadeTimer = 5000; //first animation cars image fades out to be replaced
    private int slideTimer = 3000; // second animation save earth sign slides in from left
    private int dropTimer = 4000; // final animation recycle signs drops down spinning
    private int mainMenuDelay = 7000; //delay b4 main menu displays


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
                                        Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get some stuff off screen
        ImageView save = (ImageView) findViewById(R.id.save);

        save.setTranslationX(-1000f);

        //recycle sign
        ImageView recycle = (ImageView) findViewById(R.id.recycle);

        recycle.setTranslationY(-2000f);


    }
}
