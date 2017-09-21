package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.youngju.kockockock.CustomMarker.MapControl;
import com.example.youngju.kockockock.R;

import com.example.youngju.kockockock.System.DataUnit.Path;
import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.DataContainer.PathManager;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;


public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    PathManager pathManager;
    Intent intent;
    Path path;
    TravelInfo travelInfo;

    MapControl mapControl;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        intent = getIntent();
        pathManager = PathManager.getInstance();

        path = (Path) intent.getSerializableExtra("Path");
        travelInfo = path.getTravelInfo();

        Log.d("test","MapActivity\nmetro: "+ travelInfo.getMetro().getCode()+" \ncity: "+travelInfo.getCity().getCode());


        ImageButton prev = (ImageButton) findViewById(R.id.prev_to_day);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MapActivity.this, TravelSetting.class);
                startActivity(in);
            }
        });

        Button creat = (Button) findViewById(R.id.CreatePath);
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MapActivity.this, CompletePage.class);
                path.setList(mapControl.getSelectedRegion());
                in.putExtra("Path", path);
                startActivity(in);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        Button filter1 = (Button) findViewById(R.id.filter1);
        filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapControl.clearMarker();
                mapControl.setMaker(Region.T_ATTRACTION);
            }
        });

        Button filter2 = (Button) findViewById(R.id.filter2);
        filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapControl.clearMarker();
                mapControl.setMaker(Region.T_FESTIVAL);
            }
        });

        Button filter3 = (Button) findViewById(R.id.filter3);
        filter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapControl.clearMarker();
                mapControl.setMaker(Region.T_RESTAURANT);
            }
        });

        Button filter4 = (Button) findViewById(R.id.filter4);
        filter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapControl.clearMarker();
                mapControl.setMaker(Region.T_FACILITY);
            }
        });

    }




    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mapControl=new MapControl(this,mMap,travelInfo,Region.T_ATTRACTION);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return mapControl.onMarkerClick(marker);
    }

}

