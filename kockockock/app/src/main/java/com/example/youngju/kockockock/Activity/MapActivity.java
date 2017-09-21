package com.example.youngju.kockockock.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    PathManager pathManager;
    Intent intent;
    Path path;
    TravelInfo travelInfo;
    GoogleMap mMap;
    RegionContainer regionContainer;
    ArrayList<Marker> markerArrayList;
    View marker_root_view;
    TextView tv_marker;

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
                setRegionArr(1);
                setMaker();
                setCamera();
            }
        });

        Button filter2 = (Button) findViewById(R.id.filter2);
        filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setRegionArr(2);
                setMaker();
                setCamera();
            }
        });

        Button filter3 = (Button) findViewById(R.id.filter3);
        filter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setRegionArr(3);
                setMaker();
                setCamera();
            }
        });

        Button filter4 = (Button) findViewById(R.id.filter4);
        filter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setRegionArr(4);
                setMaker();
                setCamera();
            }
        });


    }

    public void setRegionArr(int type){
        regionContainer=new RegionContainer();
        regionContainer.add(new Region(0,false,0,"37.02"+ type,"126.02"+type));
        regionContainer.add(new Region(0,false,0,"37.00"+type,"126.00"+type));
        regionContainer.add(new Region(0,false,0,"37.01"+type,"126.01"+type));
        regionContainer.add(new Region(0,false,0,"37.03"+type,"126.03"+type));
        regionContainer.add(new Region(0,false,0,"37.04"+type,"126.04"+type));
    }
    private Marker addMarker(Region region) {
        LatLng newRegion = new LatLng(Double.parseDouble(region.getX()) + 1.0, Double.parseDouble(region.getY()) + 1.0);
        String formatted = "title";

        tv_marker.setText(formatted);

        if (region.getChosenStatus()==1) {
            tv_marker.setBackgroundResource(R.drawable.colorbackground);
            tv_marker.setTextColor(Color.BLACK);
        } else {
            tv_marker.setBackgroundResource(R.drawable.uncolorbackground);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("title");
        markerOptions.position(newRegion);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }

    public void setMaker(){
        int cnt=0;
        for(Region region:regionContainer) {
            Marker marker=addMarker(region);
            marker.setTag(region);
            markerArrayList.add(marker);
        }
    }
    public void setCamera(){
        double x= Double.parseDouble(regionContainer.get(0).getX()) + 1.0;
        double y= Double.parseDouble(regionContainer.get(0).getY()) + 1.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(x,y)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
    public void clearMarker(){
        for(Marker m:markerArrayList){
            m.remove();
        }
    }

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        setCustomMarkerView();
        markerArrayList=new ArrayList<Marker>();
        setRegionArr(1);
        setMaker();
        setCamera();
        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Region region=(Region) marker.getTag();

        if(region.getChosenStatus()==0) region.setChoice(1);
        else region.setChoice(0);

        Marker marker1=addMarker(region);
        marker1.setTag(region);
        markerArrayList.add(marker1);
        markerArrayList.remove(marker);
        marker.remove();

        return false;
    }
    private void setCustomMarkerView() {
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }


}

