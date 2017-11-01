package com.example.youngju.kockockock.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.youngju.kockockock.Activity.FirstPage;
import com.example.youngju.kockockock.R;

public class Intro3Fragment extends Fragment {
    public Intro3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_intro3, container, false);
        Button start=(Button)view.findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), FirstPage.class);
                startActivity(intent);
                try {
                    getActivity().finish();
                }catch (Exception e) {}
            }
        });
        return view;
    }
}
