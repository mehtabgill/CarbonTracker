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


public class MainActivity extends AppCompatActivity {



    public void fade(View view)
    {

        //some fading animations replaced by stuff
        ImageView cars = (ImageView) findViewById(R.id.cars);
        cars.animate().alpha(0f).setDuration(5000);

        ImageView earth = (ImageView) findViewById(R.id.earth);
        earth.animate().alpha(1f).setDuration(5000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //save earth
                ImageView save = (ImageView) findViewById(R.id.save);
                save.animate().translationXBy(1000f).setDuration(3000).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ImageView recycle = (ImageView) findViewById(R.id.recycle);
                        recycle.animate().translationYBy(2000f).rotationBy(7200).setDuration(4000);

                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                        startActivity(intent);
                                    }
                                },
                                7000
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

        msg1.animate().alpha(0f).setDuration(5000);
        msg2.animate().alpha(0f).setDuration(5000);





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
