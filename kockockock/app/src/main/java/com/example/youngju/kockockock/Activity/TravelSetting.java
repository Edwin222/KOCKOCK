package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.APIGetter;
import com.example.youngju.kockockock.System.TravelInfo;
import java.util.Date;

/**
 * Created by sleep on 2017-08-26.
 */

public class TravelSetting extends AppCompatActivity {

    String[] mainCityArr;
    String[] subCityArr;

    mainSpinnerListener mainListener;
    subSpinnerListener subListener;

    Spinner mainCitySpinner;
    Spinner subCitySpinner;

    TravelInfo travelInfo;
    Intent intent;
    CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_setting);

        travelInfo = new TravelInfo();

        mainListener = new mainSpinnerListener();
        subListener = new subSpinnerListener();
        mainCitySpinner = (Spinner) findViewById(R.id.MainCitySpinner);

        APIGetter apiGetter = null;
        try {
            apiGetter = new APIGetter(APIGetter.TOURAPI_METRO_N);
            apiGetter.start();
            apiGetter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainCityArr = (String[]) apiGetter.getResult();
        ArrayAdapter<CharSequence> mainAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, mainCityArr);
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCitySpinner.setAdapter(mainAdapter);

        mainCitySpinner.setOnItemSelectedListener(mainListener);

        subCitySpinner = (Spinner) findViewById(R.id.SubCitySpinner);
        subCitySpinner.setOnItemSelectedListener(subListener);

        intent = new Intent(TravelSetting.this, MapActivity.class);


        cal = (CalendarView) findViewById(R.id.calendar);

        Button prev = (Button) findViewById(R.id.prev_to_first);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelSetting.this, FirstPage.class);
                startActivity(intent);
            }
        });


        Button fin = (Button) findViewById(R.id.finish);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date =new Date(cal.getDate());
                travelInfo.setStartTime(date);
                travelInfo.setCity(mainCityArr[mainListener.getPosition()]);
                travelInfo.setLocal(subCityArr[subListener.getPosition()]);
                intent.putExtra("Travel Info", travelInfo);
                startActivity(intent);

            }
        });
    }

    class subSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;

        public int getPosition() {
            return position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
            this.position = pos;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class mainSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;

        public int getPosition() {
            return position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            this.position = pos;

            APIGetter apiGetter = null;
            try {
                APIGetter codeGetter = new APIGetter(APIGetter.TOURAPI_METRO_CODE);
                codeGetter.addParam(position);
                codeGetter.start();
                codeGetter.join();

                int code = (int) codeGetter.getResult();

                apiGetter = new APIGetter(APIGetter.TOURAPI_CITY_N);
                apiGetter.addParam(code);
                apiGetter.start();
                apiGetter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            subCityArr = (String[]) apiGetter.getResult();
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, subCityArr);
            subCitySpinner.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}