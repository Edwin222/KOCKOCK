package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.youngju.kockockock.CustomMarker.CustomMarker;
import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.Path;
import com.example.youngju.kockockock.System.PathManager;
import com.example.youngju.kockockock.System.Region;
import com.example.youngju.kockockock.System.RegionContainer;
import com.example.youngju.kockockock.System.TravelInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;


public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    PathManager pathManager;
    Intent intent;
    Path path;
    TravelInfo travelInfo;

    GoogleMap mMap;
    RegionContainer regionContainer;
    ArrayList<Marker> markerArrayList;
    CustomMarker customMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        intent = getIntent();
        pathManager = PathManager.getInstance();

        path = (Path) intent.getSerializableExtra("Path");
        travelInfo = path.getTravelInfo();
        Log.d("Kock", "MapActivity: get Path " + path.toString());

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
                in.putExtra("Path", path);
                Log.d("Kock", "MapActivity: put Path " + path.toString());
                startActivity(in);
            }
        });

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        Button filter1 = (Button) findViewById(R.id.filter1);
        filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setMaker(1);
            }
        });

        Button filter2 = (Button) findViewById(R.id.filter2);
        filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setMaker(2);
            }
        });

        Button filter3 = (Button) findViewById(R.id.filter3);
        filter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setMaker(3);
            }
        });

        Button filter4 = (Button) findViewById(R.id.filter4);
        filter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setMaker(4);
            }
        });


    }


    public void clearMarker(){
        for(Marker m:markerArrayList){
            m.remove();
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        markerArrayList=new ArrayList<Marker>();
        customMarker=new CustomMarker(this,mMap);
        setMaker(1);
        mMap.setOnMarkerClickListener(this);
    }

    public void setMaker(int type){
        int cnt=0;
        regionContainer=new RegionContainer();
        regionContainer.add(new Region(0,false,0,"37.02"+ type,"126.02"+type));
        regionContainer.add(new Region(0,false,0,"37.00"+type,"126.00"+type));
        regionContainer.add(new Region(0,false,0,"37.01"+type,"126.01"+type));
        regionContainer.add(new Region(0,false,0,"37.03"+type,"126.03"+type));
        regionContainer.add(new Region(0,false,0,"37.04"+type,"126.04"+type));

        for(Region region:regionContainer) {
            Marker marker=customMarker.addMarker(region);
            marker.setTag(region);
            markerArrayList.add(marker);
        }

        double x= Double.parseDouble(regionContainer.get(0).getX()) + 1.0;
        double y= Double.parseDouble(regionContainer.get(0).getY()) + 1.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(x,y)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Region region=(Region) marker.getTag();

        if(region.getChosenStatus()==0) region.setChoice(1);
        else region.setChoice(0);

        Marker marker1=customMarker.addMarker(region);
        marker1.setTag(region);
        markerArrayList.add(marker1);
        markerArrayList.remove(marker);
        marker.remove();

        return false;
    }

}

