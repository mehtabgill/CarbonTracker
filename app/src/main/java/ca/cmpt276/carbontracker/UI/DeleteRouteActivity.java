package ca.cmpt276.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.carbontracker.UI.R;

public class DeleteRouteActivity extends AppCompatActivity {

    String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_route);
        ExtractdataFRomIntent();

        Button btnDelete = (Button) findViewById(R.id.buttonDeleteitforMe) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent() ;
                intent1.putExtra("signalDeletingName", name) ;
                setResult(Activity.RESULT_OK , intent1);
                finish();
            }
        });
        Button btnCancelDelete = (Button) findViewById(R.id.buttonCancelDelete) ;
        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void ExtractdataFRomIntent() {
        Intent intent = getIntent() ;
        intent.hasExtra("signaldeleteName11");
        name = intent.getStringExtra("signaldeleteName11") ;
    }

    public static Intent intentmakerDeleteRoute(Context context, String name){
        Intent intent  =  new Intent(context, DeleteRouteActivity.class);
        intent.putExtra("signaldeleteName11" , name) ;
        return intent ;
    }
}

