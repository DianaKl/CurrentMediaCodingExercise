package com.example.dklimovich.myplaylist.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "track_artist_join",
        primaryKeys = {"track_id", "artist_id"},
        foreignKeys = {
                @ForeignKey(entity = TrackEntity.class,
                        parentColumns = "id",
                        childColumns = "track_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = ArtistEntity.class,
                        parentColumns = "id",
                        childColumns = "artist_id",
                        onDelete = CASCADE)
        })
public class TrackArtistJoinEntity {

    @ColumnInfo(name = "track_id")
    @NonNull
    public final Integer mTrackId;
    @ColumnInfo(name = "artist_id")
    @NonNull
    public final Integer mArtistId;

    public TrackArtistJoinEntity(final int trackId, final int mArtistId) {
        this.mTrackId = trackId;
        this.mArtistId = mArtistId;
    }
}
