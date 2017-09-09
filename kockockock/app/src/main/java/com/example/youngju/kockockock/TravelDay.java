package com.example.youngju.kockockock;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;

public class TravelDay extends AppCompatActivity {
    Button city;
    Intent intent;
    boolean daycheck=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_day);

        intent=new Intent(TravelDay.this,MapActivity.class);
        intent.putExtra("year",0);
        intent.putExtra("month",0);
        intent.putExtra("day",0);

        ImageButton prev=(ImageButton)findViewById(R.id.previous);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_intent=new Intent(TravelDay.this,FirstPage.class);
                startActivity(back_intent);
            }
        });
        Button fin=(Button)findViewById(R.id.finish);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(daycheck) startActivity(intent);
                else Toast.makeText(getApplicationContext(),"select day for traveling",Toast.LENGTH_LONG).show();
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


        CalendarView cal=(CalendarView)findViewById(R.id.calendar);
        long time=cal.getDate();

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Log.d("Cal","y:"+year+" m:"+month+" d:"+day);
                daycheck=true;
                intent.putExtra("year",year);
                intent.putExtra("month",month+1);
                intent.putExtra("day",day);
            }
        });

    }

}
