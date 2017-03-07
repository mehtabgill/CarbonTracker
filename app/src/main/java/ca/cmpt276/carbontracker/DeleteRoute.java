package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteRoute extends AppCompatActivity {

    String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_route);

        EditText RouteName = (EditText) findViewById(R.id.editTextDeleteRoute) ;
         name = RouteName.getText().toString() ;

        final Route route = new Route(name, -1, -1 ) ;
        Button btnDelete = (Button) findViewById(R.id.buttonDeleteRoute) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.intentmakerSelectRoute(DeleteRoute.this, route) ;
                startActivity(intent);
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
}

