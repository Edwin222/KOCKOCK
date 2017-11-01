package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.youngju.kockockock.Fragment.Intro1Fragment;
import com.example.youngju.kockockock.Fragment.Intro2Fragment;
import com.example.youngju.kockockock.Fragment.Intro3Fragment;
import com.example.youngju.kockockock.R;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager = (ViewPager)findViewById(R.id.introViewpager);
        viewPager.setAdapter(new pageAdapter(getSupportFragmentManager()));

        Button skip=(Button)findViewById(R.id.skipButton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntroActivity.this,FirstPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

class pageAdapter extends FragmentStatePagerAdapter {

    public pageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0: return new Intro1Fragment();
            case 1: return new Intro2Fragment();
            case 2: return new Intro3Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}