package com.example.dklimovich.myplaylist.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dklimovich.myplaylist.R;
import com.example.dklimovich.myplaylist.databinding.FragmentPlaylistBinding;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;
import com.example.dklimovich.myplaylist.ui.adapters.PlaylistAdapter;
import com.example.dklimovich.myplaylist.ui.callbacks.PlaylistItemCallback;
import com.example.dklimovich.myplaylist.viewmodel.PlaylistViewModel;

import java.util.List;


public class PlaylistActivityFragment extends Fragment implements PlaylistItemCallback {

    public static final String TAG = PlaylistActivityFragment.class.getSimpleName();

    private PlaylistAdapter mAdapter;
    private FragmentPlaylistBinding mBinding;

    public PlaylistActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlist, container, false);
        mAdapter = new PlaylistAdapter(this);
        mBinding.rvPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvPlaylist.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final PlaylistViewModel viewModel =
                ViewModelProviders.of(this).get(PlaylistViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(PlaylistViewModel viewModel) {
        mBinding.setIsLoading(true);
        // Update the list when the data changes
        viewModel.getAllTracks().observe(this, new Observer<List<TrackEntity>>() {
            @Override
            public void onChanged(@Nullable List<TrackEntity> tracks) {
                Log.i(TAG, "viewModel.getAllTracks:onChanged: " + tracks );
                if (tracks != null) {
                    mBinding.setIsLoading(false);
                    mAdapter.setData(tracks);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }

    @Override
    public void onPlayButtonClicked(TrackEntity track) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((PlaylistActivity) getActivity()).play(track);
        }
    }
}
