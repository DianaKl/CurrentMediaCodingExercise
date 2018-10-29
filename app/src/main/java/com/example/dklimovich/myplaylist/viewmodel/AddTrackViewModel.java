package com.example.dklimovich.myplaylist.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

public class AddTrackViewModel extends AndroidViewModel {

    private TrackEntity mTrack;
    private ArtistEntity mArtist;

    public AddTrackViewModel(@NonNull Application application) {
        super(application);
        mTrack = new TrackEntity();
        mArtist = new ArtistEntity();
    }

    public TrackEntity getTrack() {
        return mTrack;
    }

    public void setTrack(TrackEntity mTrack) {
        this.mTrack = mTrack;
    }

    public ArtistEntity getArtist() {
        return mArtist;
    }

    public void setArtist(ArtistEntity mArtist) {
        this.mArtist = mArtist;
    }
}
