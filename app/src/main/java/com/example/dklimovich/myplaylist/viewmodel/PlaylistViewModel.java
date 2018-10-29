package com.example.dklimovich.myplaylist.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dklimovich.myplaylist.PlaylistRepository;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

import java.util.List;

public class PlaylistViewModel extends AndroidViewModel {

    private PlaylistRepository mRepo;
    private LiveData<List<TrackEntity>> mAllTracks;

    public PlaylistViewModel(@NonNull Application application) {
        super(application);
        mRepo = new PlaylistRepository(application);
        mAllTracks = mRepo.getAllTracks();
    }

    public void insert(TrackEntity track) {
        mRepo.insert(track);
    }

    public LiveData<List<TrackEntity>> getAllTracks() {
        return mAllTracks;
    }
}
