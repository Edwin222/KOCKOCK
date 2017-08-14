package com.example.youngju.kockockock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class TravelDay extends AppCompatActivity {
    Button city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_day);
        ImageButton prev=(ImageButton)findViewById(R.id.previous);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TravelDay.this,FirstPage.class);
                startActivity(intent);
            }
        });
        Button fin=(Button)findViewById(R.id.finish);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TravelDay.this,MapActivity.class);
                startActivity(intent);
            }
        });

        Spinner citySpinner =(Spinner)findViewById(R.id.citySpinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.citynames,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        Spinner smallCitySpinner =(Spinner)findViewById(R.id.smallCitySpinner);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this,R.array.smallcitynames,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallCitySpinner.setAdapter(adapter2);

    }

}
