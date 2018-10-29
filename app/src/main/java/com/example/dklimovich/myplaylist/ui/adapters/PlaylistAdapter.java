package com.example.dklimovich.myplaylist.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dklimovich.myplaylist.R;
import com.example.dklimovich.myplaylist.databinding.PlaylistItemBinding;
import com.example.dklimovich.myplaylist.db.entity.TrackEntity;
import com.example.dklimovich.myplaylist.ui.callbacks.PlaylistItemCallback;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistItemViewHolder> {

    List<? extends TrackEntity> mTrackList;
    PlaylistItemCallback mPlaylistItemCallback;

    public PlaylistAdapter(PlaylistItemCallback callback) {
        mPlaylistItemCallback = callback;
    }

    public void setData(List<? extends TrackEntity> list){
        this.mTrackList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        PlaylistItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.playlist_item,
                        parent, false);
        binding.setCallback(mPlaylistItemCallback);
        return new PlaylistItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistItemViewHolder holder, int position) {
        holder.binding.setTrack(mTrackList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTrackList == null ? 0 : mTrackList.size();
    }

    static class PlaylistItemViewHolder extends RecyclerView.ViewHolder {
        final PlaylistItemBinding binding;

        public PlaylistItemViewHolder(PlaylistItemBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
