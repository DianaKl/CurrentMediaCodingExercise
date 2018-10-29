package com.example.dklimovich.myplaylist.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.dklimovich.myplaylist.utils.Utils;
import com.example.dklimovich.myplaylist.db.dao.ArtistDao;
import com.example.dklimovich.myplaylist.db.dao.TrackArtistJoinDao;
import com.example.dklimovich.myplaylist.db.dao.TrackDao;
import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackArtistJoinEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;

@Database(entities = {ArtistEntity.class, TrackEntity.class, TrackArtistJoinEntity.class}, version = 1)
public abstract class PlaylistDatatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "playlist-db";

    public abstract ArtistDao artistDao();

    public abstract TrackDao trackDao();

    public abstract TrackArtistJoinDao trackArtistJoinDao();

    private static volatile PlaylistDatatabase INSTANCE;

    public static PlaylistDatatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaylistDatatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlaylistDatatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TrackDao mDao;
        private final ArtistDao mArtistDao;
        private final TrackArtistJoinDao mTrackArtistJoinDao;

        PopulateDbAsync(PlaylistDatatabase db) {
            mDao = db.trackDao();
            mArtistDao = db.artistDao();
            mTrackArtistJoinDao = db.trackArtistJoinDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            mArtistDao.deleteAll();

            TrackEntity track;
            ArtistEntity artist;

            track = new TrackEntity("Remember The Time" ,  "https://open.spotify.com/track/4jnFqNWeJCeCRHc4HCdxfd?si=zWji7HpcTUy6FpVSl-VHBg");
            artist = new ArtistEntity("Michael Jackson");
            updateDaos(track, artist);
            track = new TrackEntity("Time",  "https://youtu.be/5ItKS8bUUTA");
            artist = new ArtistEntity("Jungle");
            updateDaos(track, artist);
            track = new TrackEntity("Part Time Love",  "https://soundcloud.com/mrjacobbanks/part-time-love");
            artist = new ArtistEntity("Jacob Banks");
            updateDaos(track, artist);

//            for(int i = 0; i < 10; i++) {
//                track = new TrackEntity("New track " + i,  "url " + i);
//                artist = new ArtistEntity("New artist " + i);
//                updateDaos(track, artist);
//                track = new TrackEntity("Another track " + i,  "url " + i);
//                artist = new ArtistEntity("Another artist " + i);
//                updateDaos(track, artist);
//            }
            return null;
        }

        private void updateDaos(TrackEntity track, ArtistEntity artist){
            long trackId = mDao.insert(track);
            long artistId = mArtistDao.insert(artist);
            mTrackArtistJoinDao.insert(new TrackArtistJoinEntity(Utils.longToInt(trackId), Utils.longToInt(artistId)));
        }
    }
}
