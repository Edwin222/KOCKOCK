package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.youngju.kockockock.R;

public class DetailedPathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_path);

        Button back=(Button)findViewById(R.id.back_to_com_from_detailed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailedPathActivity.this,CompletePage.class);
                startActivity(intent);
            }
        });

    }
}
