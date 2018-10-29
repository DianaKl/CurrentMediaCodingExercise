package com.example.dklimovich.myplaylist.ui.callbacks;


import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

public interface PlaylistItemCallback {

    void onPlayButtonClicked(TrackEntity track);
}
