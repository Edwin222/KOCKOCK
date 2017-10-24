package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.DataContainer.RegionContainer;
import com.example.youngju.kockockock.System.DataUnit.Path;

import java.util.ArrayList;

public class DetailedPathActivity extends AppCompatActivity {

    ArrayList<Path_StartDest> regionInfo;
    ListView pathListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_path);

        Path path = (Path) getIntent().getSerializableExtra("Path");
        RegionContainer regionList = path.getList();
        pathListView = (ListView) findViewById(R.id.PathListView);

        int size = regionList.size();
        ArrayList<CharSequence> detailList = new ArrayList<CharSequence>();
        regionInfo = new ArrayList<Path_StartDest>();

        for(int i=0;i <size-1;i++){
            detailList.add(regionList.get(i).getName() + "→" + regionList.get(i+1).getName());
            regionInfo.add(new Path_StartDest(regionList.get(i).getLatitude(), regionList.get(i).getLongitude(),
                    regionList.get(i+1).getLatitude(), regionList.get(i+1).getLongitude()));
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_list_item_1, detailList);
        pathListView.setAdapter(adapter);

        Button back=(Button)findViewById(R.id.back_to_com_from_detailed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailedPathActivity.this,CompletePage.class);
                startActivity(intent);
            }
        });

        pathListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String uri = "daummaps://route?sp="+regionInfo.get(pos).startLat+","+regionInfo.get(pos).startLon+
                        "&ep="+regionInfo.get(pos).destLat+","+regionInfo.get(pos).destLon+"&by=PUBLICTRANSIT";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

    }
};

class Path_StartDest {
    public String startLat;
    public String startLon;
    public String destLat;
    public String destLon;

    public Path_StartDest(String sat, String son, String dat, String don){
        startLat = sat;
        startLon = son;
        destLat = dat;
        destLon = don;
    }
};