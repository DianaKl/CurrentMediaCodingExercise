package com.example.dklimovich.myplaylist.ui.callbacks;

import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

public interface NewTrackCallback {

    void onAddButtonClicked(TrackEntity track, ArtistEntity artist);
}
