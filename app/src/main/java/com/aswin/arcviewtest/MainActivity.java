package com.aswin.arcviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aswin.CustomArc.ArcView;

public class MainActivity extends AppCompatActivity {

    private ArcView arcView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arcView=findViewById(R.id.arc);
        arcView.setProgress(65);
    }
}
