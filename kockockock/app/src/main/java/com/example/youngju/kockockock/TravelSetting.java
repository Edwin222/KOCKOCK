package com.example.youngju.kockockock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.youngju.kockockock.System.TourAPIData;

/**
 * Created by sleep on 2017-08-26.
 */

public class TravelSetting extends AppCompatActivity {

    Spinner mainCitySpinner;
    Spinner subCitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_setting);

        mainCitySpinner = (Spinner) findViewById(R.id.MainCitySpinner);
        ArrayAdapter<CharSequence> mainAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,  TourAPIData.getInstance().getMetroName());
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCitySpinner.setAdapter(mainAdapter);

        mainCitySpinner.setOnItemSelectedListener(new mainSpinnerListener());

        subCitySpinner = (Spinner) findViewById(R.id.SubCitySpinner);
    }
}

class mainSpinnerListener implements AdapterView.OnItemSelectedListener {
    private int position;



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
