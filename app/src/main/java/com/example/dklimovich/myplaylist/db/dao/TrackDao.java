package com.example.dklimovich.myplaylist.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("SELECT * FROM tracks")
    LiveData<List<TrackEntity>> loadAllTracks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TrackEntity> tracks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TrackEntity track);

    @Query("select * from tracks where id = :trackId")
    LiveData<TrackEntity> loadTrack(int trackId);

    @Query("select * from tracks where id = :trackId")
    TrackEntity loadTrackSync(int trackId);

    @Query("DELETE FROM tracks")
    void deleteAll();
}
