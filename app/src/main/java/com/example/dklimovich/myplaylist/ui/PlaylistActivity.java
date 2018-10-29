package com.example.dklimovich.myplaylist.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dklimovich.myplaylist.R;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;
import com.example.dklimovich.myplaylist.viewmodel.PlaylistViewModel;

public class PlaylistActivity extends AppCompatActivity {

    public static final String TAG = PlaylistActivity.class.getSimpleName();

    public static final int REQUEST_CODE_NEW_TRACK = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNewTrackActivity("");
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            openAddNewTrackActivity(sharedText);
            Toast.makeText(this, " " + sharedText, Toast.LENGTH_SHORT).show();
            // Update UI to reflect text being shared
        }
    }

    void play(TrackEntity track){
        openUrlInBrowser(track.getUrl());
    }

    private void openUrlInBrowser(String url){
        Log.i(TAG, TAG + "open webview url: " + url);

        try {
            if(TextUtils.isEmpty(url)){
                throw new IllegalStateException();
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(this, getString(R.string.incorrect_url, url), Toast.LENGTH_SHORT).show();
        }catch (IllegalStateException ex){
            Toast.makeText(this, getString(R.string.incorrect_url, url), Toast.LENGTH_SHORT).show();
        }
    }

    private void openAddNewTrackActivity(String text){
        Intent intent = new Intent(PlaylistActivity.this, AddNewTrackActivity.class);
        intent.putExtra(AddNewTrackActivity.EXTRA_SHARED_TEXT, text);
        startActivityForResult(intent, REQUEST_CODE_NEW_TRACK);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW_TRACK && resultCode == RESULT_OK) {
            TrackEntity track = data.getParcelableExtra(AddNewTrackActivity.EXTRA_TRACK);
            ViewModelProviders.of(this).get(PlaylistViewModel.class).insert(track);
        }
    }
}
