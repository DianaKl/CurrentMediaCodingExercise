package com.example.dklimovich.myplaylist.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.dklimovich.myplaylist.BR;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "tracks")
public class TrackEntity extends BaseObservable implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "url")
    private String mUrl;

    @Ignore
    private List<ArtistEntity> mArtists;


    @Ignore
    public TrackEntity() {
    }

    public TrackEntity(String name, String url) {
        this.mName = name;
        this.mUrl = url;
    }


    protected TrackEntity(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mName = in.readString();
        mUrl = in.readString();
        mArtists = in.createTypedArrayList(ArtistEntity.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mId);
        }
        dest.writeString(mName);
        dest.writeString(mUrl);
        dest.writeTypedList(mArtists);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrackEntity> CREATOR = new Creator<TrackEntity>() {
        @Override
        public TrackEntity createFromParcel(Parcel in) {
            return new TrackEntity(in);
        }

        @Override
        public TrackEntity[] newArray(int size) {
            return new TrackEntity[size];
        }
    };

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    @Bindable
    public String getUrl() {
        return mUrl;
    }

    public void setName(String name) {
        this.mName = name;
        notifyPropertyChanged(BR.name);
    }

    public void setUrl(String url) {
        this.mUrl = url;
        notifyPropertyChanged(BR.url);
    }

    public List<ArtistEntity> getArtists() {
        return mArtists;
    }

    public void setArtists(List<ArtistEntity> mArtists) {
        this.mArtists = mArtists;
    }

    public void addArtist(ArtistEntity artist){
        if(mArtists == null){
            mArtists = new ArrayList<>();
        }
        mArtists.add(artist);
    }

    public String getArtistDisplayName(String defaultName) {
        if(mArtists == null || mArtists.size() < 1) {
            return defaultName;
        }

        StringBuilder builder = new StringBuilder();
        for (ArtistEntity artist : mArtists) {
            builder.append(artist.getName() + ", ");
        }
        return builder.delete(builder.length() - 2, builder.length()).toString();
    }

    @Bindable({"name", "url"})
    public boolean isValid() {
        return !TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackEntity that = (TrackEntity) o;
        return  Objects.equals(mName, that.mName) &&
                Objects.equals(mUrl, that.mUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash( mName, mUrl);
    }

    @Override
    public String toString() {
        return "TrackEntity{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
