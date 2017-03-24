package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.carbontracker.UI.R;

/*
 * UI class of delete route activity
 */

public class DeleteRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_route);

        Intent intent = getIntent() ;
        final int index = intent.getIntExtra(SelectRouteActivity.SIGNAL_DELETING_INDEX, 0) ;

        Button btnDelete = (Button) findViewById(R.id.buttonDeleteitforMe) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent() ;
                intent1.putExtra(SelectRouteActivity.SIGNAL_DELETING_INDEX, index) ;
                setResult(Activity.RESULT_OK , intent1);
                finish();
            }
        });
        Button btnCancelDelete = (Button) findViewById(R.id.buttonCancelDelete) ;
        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }

}

