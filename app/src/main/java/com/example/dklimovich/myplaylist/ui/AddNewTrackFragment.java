package com.example.dklimovich.myplaylist.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dklimovich.myplaylist.R;
import com.example.dklimovich.myplaylist.databinding.FragmentAddNewTrackBinding;
import com.example.dklimovich.myplaylist.db.entity.ArtistEntity;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;
import com.example.dklimovich.myplaylist.ui.callbacks.NewTrackCallback;
import com.example.dklimovich.myplaylist.viewmodel.AddTrackViewModel;


public class AddNewTrackFragment extends Fragment implements NewTrackCallback {

    public static final String TAG = AddNewTrackFragment.class.getSimpleName();

    private FragmentAddNewTrackBinding mBinding;

    public AddNewTrackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_track, container, false);
        mBinding.setCallback(this);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AddTrackViewModel viewModel =
                ViewModelProviders.of(this).get(AddTrackViewModel.class);
        mBinding.setTrack(viewModel.getTrack());
        if(getActivity().getIntent().hasExtra(AddNewTrackActivity.EXTRA_SHARED_TEXT)){
            mBinding.getTrack().setUrl(getActivity().getIntent().getStringExtra(AddNewTrackActivity.EXTRA_SHARED_TEXT));
        }
        mBinding.setArtist(viewModel.getArtist());

    }

    @Override
    public void onAddButtonClicked(TrackEntity track, ArtistEntity artist) {
        Intent replyIntent = new Intent();
        if (track != null && track.isValid()) {
            if(artist.isValid()) {
                track.addArtist(artist);
            }

            replyIntent.putExtra(AddNewTrackActivity.EXTRA_TRACK, track);
            getActivity().setResult(getActivity().RESULT_OK, replyIntent);
        } else {
            getActivity().setResult(getActivity().RESULT_CANCELED, replyIntent);
        }
        getActivity().finish();

    }
}
