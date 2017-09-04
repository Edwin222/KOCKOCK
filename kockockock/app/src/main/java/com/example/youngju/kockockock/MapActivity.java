package com.example.youngju.kockockock;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    boolean created=false;
    View include1;
    View include2;
    Button creat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        include1=(View)findViewById(R.id.include1);
        include2=(View)findViewById(R.id.include2);
        include2.setVisibility(View.GONE);

        creat=(Button)findViewById(R.id.CreatePath);
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(created){//menu button listener
                    popMenu();
                } else{
                    creat.setText("Menu");
                    include2.setVisibility(View.VISIBLE);
                    include1.setVisibility(View.GONE);
                    created=!created;
                }
            }
        });

        ImageButton prev=(ImageButton)findViewById(R.id.prev_to_day);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(created) {
                    creat.setText("Create");
                    include1.setVisibility(View.VISIBLE);
                    include2.setVisibility(View.GONE);
                    FragmentManager fragmentManager = getFragmentManager();
                    MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map2);
                    mapFragment.getMapAsync(MapActivity.this);

                    created=!created;
                } else {
                    Intent intent=new Intent(MapActivity.this,TravelDay.class);
                    startActivity(intent);
                }
            }
        });

    }


    public void popMenu(){
        PopupMenu p = new PopupMenu(MapActivity.this,creat);
        getMenuInflater().inflate(R.menu.menu, p.getMenu());
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuitem1:
                        AlertDialog.Builder alert = new AlertDialog.Builder(MapActivity.this);

                        alert.setTitle("Save Path");
                        alert.setMessage("New Path Name:");

                        final EditText name = new EditText(MapActivity.this);
                        alert.setView(name);
                        alert.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                        alert.setPositiveButton("save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String pathname = name.getText().toString();

                                Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MapActivity.this,FirstPage.class);

                                intent.putExtra("pathName",pathname); ////////TO-DO : change code to save name in file

                                startActivity(intent);
                            }
                        });
                        alert.show();
                        break;
                    case R.id.menuitem2: //go to detailed list page
                        break;
                    case R.id.menuitem3: // go to weather page
                        break;
                }
                return false;
            }
        });
        p.show();
    }

  @Override
    public void onMapReady(final GoogleMap map) {

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

