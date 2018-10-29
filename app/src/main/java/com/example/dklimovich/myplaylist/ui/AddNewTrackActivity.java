package com.example.dklimovich.myplaylist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.dklimovich.myplaylist.R;

public class AddNewTrackActivity extends AppCompatActivity {

    public static final String TAG = AddNewTrackActivity.class.getSimpleName();

    public static final String EXTRA_TRACK = "EXTRA_TRACK";
    public static final String EXTRA_SHARED_TEXT = "EXTRA_SHARED_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_track);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
