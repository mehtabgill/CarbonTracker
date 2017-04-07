package ca.cmpt276.carbontracker.UI;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.cmpt276.carbontracker.Model.SingletonModel;

public class OptionsActivity extends AppCompatActivity {

    SingletonModel model = SingletonModel.getInstance();

    private String[] choices;
    private float[] conversionFactors;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.back) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        choices = getResources().getStringArray(R.array.units);
        readConversionFactors();
        setupRadioButtons();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.settings));
    }

    private void setupRadioButtons() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_units);
        for(int i = 0; i < choices.length; i++) {
            RadioButton btn = new RadioButton(this);
            btn.setText(choices[i]);
            btn.setTypeface(Typeface.DEFAULT_BOLD);

            final int index = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.setUnitsSettings(choices[index], conversionFactors[index]);
                }
            });
            radioGroup.addView(btn);

            if(choices[i].equals(model.getUnit())) {
                btn.setChecked(true);
            }
        }
    }

    private void readConversionFactors() {
        String[] temp = getResources().getStringArray(R.array.unit_conversion);
        conversionFactors = new float[temp.length];
        for(int i = 0; i < temp.length; i++) {
            conversionFactors[i] = Float.parseFloat(temp[i]);
        }
    }
}
