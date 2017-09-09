package com.example.youngju.kockockock;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.youngju.kockockock.System.TourAPIData;

import java.util.ArrayList;


public class FirstPage extends AppCompatActivity {
    boolean editmode = false;
    ListView myTravelList;
    ArrayList<String> travelNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Button del = (Button) findViewById(R.id.delButton);
        del.setVisibility(View.GONE);


        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstPage.this, TravelDay.class);
                startActivity(intent);
                finish();
            }
        });

        travelNameList = new ArrayList<String>();
        myTravelList = (ListView) findViewById(R.id.myTravelList);

        /// TO-DO : change code to load path data////////////////////////////////////////////////

        travelNameList.add("travel 1");
        travelNameList.add("travel 2");

        try {
            Intent intent = getIntent();
            String pathname = (String) intent.getStringExtra("pathName");
            if (!pathname.equals(""))
                travelNameList.add(pathname);
        } catch (Exception e) {
            Log.d("test","intent exception");
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, travelNameList);
        myTravelList.setAdapter(adapter);

        myTravelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        final Button edit = (Button) findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editmode) {
                    setNotEditmode();
                } else {
                    setEditmode();
                }
            }
        });
    }

    public void setNotEditmode() {
        Button edit = (Button) findViewById(R.id.editButton);
        edit.setText("FINISH");
        ArrayAdapter ad = new ArrayAdapter(FirstPage.this, android.R.layout.simple_list_item_multiple_choice, travelNameList);
        myTravelList.setAdapter(ad);
        Button del = (Button) findViewById(R.id.delButton);
        del.setVisibility(View.VISIBLE);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItems = myTravelList.getCheckedItemPositions();
                int count = myTravelList.getAdapter().getCount();

                for (int i = count - 1; i >= 0; i--)
                    if (checkedItems.get(i)) travelNameList.remove(i);

                myTravelList.clearChoices();
                ArrayAdapter ad = (ArrayAdapter) myTravelList.getAdapter();
                ad.notifyDataSetChanged();
            }
        });
        editmode = !editmode;
    }

    public void setEditmode() {
        Button edit = (Button) findViewById(R.id.editButton);
        edit.setText("EDIT");
        ArrayAdapter ad = new ArrayAdapter(FirstPage.this, android.R.layout.simple_list_item_1, travelNameList);
        myTravelList.setAdapter(ad);
        Button del = (Button) findViewById(R.id.delButton);
        del.setVisibility(View.GONE);
        editmode = !editmode;
    }
}
