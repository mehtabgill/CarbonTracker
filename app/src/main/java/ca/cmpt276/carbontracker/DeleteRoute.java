package ca.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteRoute extends AppCompatActivity {

    String name ;
    Route route ;
    EditText RouteName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_route);

        RouteName = (EditText) findViewById(R.id.editTextDeleteRoute) ;


        RouteName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = RouteName.getText().toString() ;


            }
        });

        route = new Route(name, -1, -1 ) ;
        Button btnDelete = (Button) findViewById(R.id.buttonDeleteit) ;
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

