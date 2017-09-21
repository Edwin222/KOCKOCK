package com.example.youngju.kockockock.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class CompletePage extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    PathManager pathManager;
    Intent intent;
    Path path;
    TravelInfo travelInfo;
    Button menu;
    GoogleMap mMap;
    RegionContainer regionContainer;
    ArrayList<Marker> markerArrayList;
    CustomMarker customMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_page);
        intent=getIntent();
        pathManager=PathManager.getInstance();

        path=(Path)intent.getSerializableExtra("Path");
        travelInfo=path.getTravelInfo();
        Log.d("Kock","CompleteActivity: get Path "+path.toString());

        ImageButton prev=(ImageButton)findViewById(R.id.prev_to_map);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(CompletePage.this,MapActivity.class);
                in.putExtra("Path",path);
                Log.d("Kock","CompleteActivity: put Path "+path.toString());
                startActivity(in);
            }
        });

        menu=(Button)findViewById(R.id.final_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popMenu();
            }
        });

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        Button filter21 = (Button) findViewById(R.id.filter21);
        filter21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setRegionArr(1);
                setMaker();
                setCamera();
            }
        });

        Button filter22 = (Button) findViewById(R.id.filter22);
        filter22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMarker();
                setRegionArr(2);
                setMaker();
                setCamera();
            }
        });

    }

    public void popMenu(){
        PopupMenu p = new PopupMenu(CompletePage.this,menu);
        getMenuInflater().inflate(R.menu.menu, p.getMenu());
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuitem1:
                        AlertDialog.Builder alert = new AlertDialog.Builder(CompletePage.this);

                        alert.setTitle("Save Path");
                        alert.setMessage("New Path Name:");

                        final EditText name = new EditText(CompletePage.this);
                        alert.setView(name);
                        alert.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                        alert.setPositiveButton("save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Path newpath=new Path(name.getText().toString());
                                Log.d("Kock","travel:start Time:"+travelInfo.getStartTime());
                                newpath.setTravelInfo(travelInfo);
                                pathManager.add(newpath);
                                pathManager.saveData();
                                Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CompletePage.this,FirstPage.class);
                                startActivity(intent);
                            }
                        });
                        alert.show();
                        break;
                    case R.id.menuitem2:
                        //go to detailed list page
                        Intent intent2=new Intent(CompletePage.this,DetailedPathActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.menuitem3: // go to weather page
                        Intent intent3=new Intent(CompletePage.this,WeatherActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menuitem4: // go to first page
                        Intent intent4=new Intent(CompletePage.this,FirstPage.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
        p.show();
    }

    public void setRegionArr(int type){
        regionContainer=new RegionContainer();
        regionContainer.add(new Region(0,false,0,"37.02"+ type,"126.02"+type));
        regionContainer.add(new Region(0,false,0,"37.00"+type,"126.00"+type));
        regionContainer.add(new Region(0,false,0,"37.01"+type,"126.01"+type));
        regionContainer.add(new Region(0,false,0,"37.03"+type,"126.03"+type));
        regionContainer.add(new Region(0,false,0,"37.04"+type,"126.04"+type));
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


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        markerArrayList=new ArrayList<Marker>();
        customMarker=new CustomMarker(this,mMap);
        setRegionArr(1);
        setMaker();
        setCamera();
        mMap.setOnMarkerClickListener(this);
    }

    public void setMaker(){
        int cnt=0;
        for(Region region:regionContainer) {
            Marker marker=customMarker.addMarker(region);
            marker.setTag(region);
            markerArrayList.add(marker);
        }
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
