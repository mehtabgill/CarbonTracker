package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRoute extends AppCompatActivity {

    EditText editName ;
    EditText editCityDistance ;
    EditText editHighDistance ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        editName  = (EditText) findViewById(R.id.editText_RouteName) ;
        editCityDistance = ( EditText) findViewById(R.id.editText_cityDistance);
        editHighDistance = (EditText) findViewById(R.id.editText_highwayDistance) ;

        final String name = editName.getText().toString() ;
        String Cdistance = editCityDistance.getText().toString() ;
        String hdistance = editHighDistance.getText().toString() ;

        final float cityDis = Float.parseFloat(Cdistance) ;
        final float highwayDis = Float.parseFloat(hdistance) ;

        Button btnAddRoute = (Button)findViewById(R.id.ButtonAddRoute) ;
        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route newRoute = new Route(name, cityDis, highwayDis) ;
                Intent intent = SelectRouteActivity.intentmakerSelectRoute(AddRoute.this, newRoute) ;
                startActivity(intent);

            }
        });

        Button btnCancel = (Button) findViewById(R.id.buttonCancel) ;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
