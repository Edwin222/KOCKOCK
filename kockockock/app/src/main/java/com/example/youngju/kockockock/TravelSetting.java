package com.example.youngju.kockockock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.youngju.kockockock.System.APIGetter;
import com.example.youngju.kockockock.System.TourAPIData;

/**
 * Created by sleep on 2017-08-26.
 */

public class TravelSetting extends AppCompatActivity {

    String[] mainCityArr;
    String[] subCityArr;

    Spinner mainCitySpinner;
    Spinner subCitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_setting);

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

        mainCitySpinner.setOnItemSelectedListener(new mainSpinnerListener());

        subCitySpinner = (Spinner) findViewById(R.id.SubCitySpinner);
    }

    class mainSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;

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
