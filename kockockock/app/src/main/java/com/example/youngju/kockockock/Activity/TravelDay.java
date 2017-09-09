package com.example.youngju.kockockock;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.youngju.kockockock.System.City;
import com.example.youngju.kockockock.System.TourAPIData;

import java.util.ArrayList;
import java.text.DateFormat;

public class TravelDay extends AppCompatActivity {
    Spinner citySpinner;
    ArrayList<String> cityName;

    Spinner smallCitySpinner;
    ArrayList<String> smallCityName;

    TravelDay self;
    int selectedPos;
    Button city;
    Intent intent;
    boolean daycheck=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_day);
        self = this;

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

        citySpinner =(Spinner)findViewById(R.id.citySpinner);

        cityName = new ArrayList<String>();

        new Thread(){
            public void run(){
                City[] cityList = TourAPIData.getInstance().getMetro();
                for(int i=0; i<cityList.length; i++) {
                    cityName.add(cityList[i].getName());
                }
            }
        }.start();

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, cityName);
        citySpinner.setAdapter(adapter);
        citySpinner.setPrompt("시/도");

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                System.out.println("YYYYYY");
                smallCityName = new ArrayList<String>();
                selectedPos = pos;

                System.out.println("YYYYYY");
                new Thread(){
                    public void run(){
                        int areaCode = TourAPIData.getInstance().getMetroCode(selectedPos);
                        City[] smallCityList = TourAPIData.getInstance().getCity(areaCode);
                        smallCityName.removeAll(null);

                        for(int i=0; i<smallCityList.length; i++){
                            smallCityName .add(smallCityList[i].getName());
                            System.out.println(smallCityList[i].getName());
                        }
                    }
                }.start();
                System.out.println("YYYYYY");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(self, android.R.layout.simple_spinner_dropdown_item, smallCityName);
                smallCitySpinner.setAdapter(adapter);
                System.out.println("YYYYYY");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        smallCitySpinner =(Spinner)findViewById(R.id.smallCitySpinner);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(getApplicationContext(),R.array.smallcitynames,android.R.layout.simple_spinner_item);
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
