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

import com.example.youngju.kockockock.CustomMarker.MapControl;
import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.DataContainer.PathManager;
import com.example.youngju.kockockock.System.DataUnit.Path;
import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class CompletePage extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    PathManager pathManager;
    Intent intent;
    Path path;
    TravelInfo travelInfo;

    Button menu;

    GoogleMap mMap;
    MapControl mapControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_page);
        intent=getIntent();
        pathManager=PathManager.getInstance();

        path=(Path)intent.getSerializableExtra("Path");
        travelInfo=path.getTravelInfo();
        path.getList().setRegionSequence();

        for(Region r: path.getList())
            Log.d("test","CompletePage: region:"+r.getName());

        ImageButton prev=(ImageButton)findViewById(R.id.prev_to_map);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","prev Button Listener");
                Intent in=new Intent(CompletePage.this,MapActivity.class);
                in.putExtra("Path",path);
                startActivity(in);
            }
        });

        menu=(Button)findViewById(R.id.final_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","menu Button Listener");
                popMenu();
            }
        });

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        Log.d("test","mapFragment.getMapAsync");

        Button filter21 = (Button) findViewById(R.id.filter21);
        filter21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","Button1 Listener");
                mapControl.clearMarker();
                mapControl.setMaker(Region.T_FACILITY);
            }
        });

        Button filter22 = (Button) findViewById(R.id.filter22);
        filter22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","Button2 Listener");
                mapControl.clearMarker();
                mapControl.setMaker(Region.T_STATION);
            }
        });

    }

    public void popMenu(){
        Log.d("test","popMenu");
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

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("test","onMapReady");
        mMap = map;
        mapControl=new MapControl(this, mMap, travelInfo, Region.T_FACILITY);
        mapControl.setSelectedRegion(path.getList());
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("test","onMarkerClick");
        return mapControl.onMarkerClick(marker);
    }

}
