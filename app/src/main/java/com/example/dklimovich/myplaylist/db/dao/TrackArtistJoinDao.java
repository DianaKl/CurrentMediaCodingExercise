package com.example.dklimovich.myplaylist.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackArtistJoinEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

import java.util.List;

@Dao
public interface TrackArtistJoinDao {

    @Insert
    void insert(TrackArtistJoinEntity trackArtistJoinEntity);

    @Query("SELECT * FROM tracks INNER JOIN track_artist_join " +
            "ON tracks.id = track_artist_join.track_id " +
            "WHERE track_artist_join.artist_id = :artistId")
    List<TrackEntity> getTracksForArtist(final int artistId);

    @Query("SELECT * FROM artists INNER JOIN track_artist_join " +
            "ON artists.id = track_artist_join.artist_id " +
            "WHERE track_artist_join.track_id = :userId")
    List<ArtistEntity> getArtistsForTrack(final int userId);
}
