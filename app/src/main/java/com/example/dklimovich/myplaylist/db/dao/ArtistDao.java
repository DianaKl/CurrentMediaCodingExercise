package com.example.dklimovich.myplaylist.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM artists where id = :artistId")
    LiveData<List<ArtistEntity>> loadArtist(int artistId);

    @Query("SELECT * FROM artists where id = :artistId")
    List<ArtistEntity> loadArtistSync(int artistId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ArtistEntity> artists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ArtistEntity artist);

    @Query("DELETE FROM tracks")
    void deleteAll();
}
