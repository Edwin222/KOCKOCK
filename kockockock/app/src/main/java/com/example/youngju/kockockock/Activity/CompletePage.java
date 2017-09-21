package com.example.youngju.kockockock.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.Path;
import com.example.youngju.kockockock.System.PathManager;
import com.example.youngju.kockockock.System.Region;
import com.example.youngju.kockockock.System.TravelInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CompletePage extends AppCompatActivity implements OnMapReadyCallback {

    PathManager pathManager;
    Intent intent;
    Path path;
    TravelInfo travelInfo;
    Button menu;

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

    @Override
    public void onMapReady(final GoogleMap map) {

        //Region newregion = new Region(Region.ATTRACTION, true, Region.SELECTED, "0", "0");

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

}
