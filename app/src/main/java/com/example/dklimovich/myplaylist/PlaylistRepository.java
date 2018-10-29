package com.example.dklimovich.myplaylist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;

import com.example.dklimovich.myplaylist.db.PlaylistDatatabase;
import com.example.dklimovich.myplaylist.db.dao.ArtistDao;
import com.example.dklimovich.myplaylist.db.dao.TrackArtistJoinDao;
import com.example.dklimovich.myplaylist.db.dao.TrackDao;
import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackArtistJoinEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;
import com.example.dklimovich.myplaylist.utils.Utils;

import java.util.List;

public class PlaylistRepository {

    private TrackDao mTrackDao;
    private ArtistDao mArtistDao;
    private TrackArtistJoinDao mTrackArtistJoinDao;
    private LiveData<List<TrackEntity>> mAllTracks;

    public PlaylistRepository(Application application) {
        PlaylistDatatabase db = PlaylistDatatabase.getDatabase(application);
        mTrackDao = db.trackDao();
        mArtistDao = db.artistDao();
        mTrackArtistJoinDao = db.trackArtistJoinDao();
        mAllTracks = Transformations.switchMap(mTrackDao.loadAllTracks(), newData -> updateArtistForTrack(newData));
    }


    private LiveData<List<TrackEntity>> updateArtistForTrack(List<TrackEntity> tracks) {

        GetArtistsForTrackAsyncTask task = new GetArtistsForTrackAsyncTask(mTrackArtistJoinDao);
        task.execute(tracks);

        LiveData liveData = new MutableLiveData<>();
        ((MutableLiveData) liveData).postValue(tracks);
        return liveData;
    }


    private static class GetArtistsForTrackAsyncTask extends AsyncTask<List<TrackEntity>, Void, List<TrackEntity>> {

        private TrackArtistJoinDao mTrackArtistJoinDao;

        GetArtistsForTrackAsyncTask(TrackArtistJoinDao dao) {
            mTrackArtistJoinDao = dao;
        }

        @Override
        protected List<TrackEntity> doInBackground(List<TrackEntity>... tracks) {

            for (TrackEntity track : tracks[0]) {
                track.setArtists(mTrackArtistJoinDao.getArtistsForTrack(track.getId()));
            }
            return tracks[0];
        }
    }

    public LiveData<List<TrackEntity>> getAllTracks() {
        return mAllTracks;
    }

    public void insert(TrackEntity playlistItem) {
        new InsertAsyncTask(mTrackDao, mArtistDao, mTrackArtistJoinDao).execute(playlistItem);
    }

    private static class InsertAsyncTask extends AsyncTask<TrackEntity, Void, Void> {

        private TrackDao mAsyncTaskDao;
        private ArtistDao mAsyncArtistDao;
        private TrackArtistJoinDao mTrackArtistJoinDao;

        InsertAsyncTask(TrackDao trackDao, ArtistDao artistDao, TrackArtistJoinDao trackArtistJoinDao) {
            mAsyncTaskDao = trackDao;
            mAsyncArtistDao = artistDao;
            mTrackArtistJoinDao = trackArtistJoinDao;
        }

        @Override
        protected Void doInBackground(final TrackEntity... params) {
            TrackEntity playlistItem = params[0];

            long trackId = mAsyncTaskDao.insert(playlistItem);
            if (playlistItem.getArtists() != null && playlistItem.getArtists().size() > 0) {

                for (ArtistEntity artist : playlistItem.getArtists()) {
                    long artistId = mAsyncArtistDao.insert(artist);
                    mTrackArtistJoinDao.insert(new TrackArtistJoinEntity(Utils.longToInt(trackId), Utils.longToInt(artistId)));
                }
            }

            return null;
        }
    }
}
