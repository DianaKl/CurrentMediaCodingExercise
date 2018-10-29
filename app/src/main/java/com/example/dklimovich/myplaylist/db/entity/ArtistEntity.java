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


@Entity(tableName = "artists")
public class ArtistEntity extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer mId;
    @ColumnInfo(name = "name")
    private String mName;

    @Ignore
    public ArtistEntity() {
    }

    public ArtistEntity(String name) {
        this.mName = name;
    }

    @Ignore
    protected ArtistEntity(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mName = in.readString();
    }

    public static final Creator<ArtistEntity> CREATOR = new Creator<ArtistEntity>() {
        @Override
        public ArtistEntity createFromParcel(Parcel in) {
            return new ArtistEntity(in);
        }

        @Override
        public ArtistEntity[] newArray(int size) {
            return new ArtistEntity[size];
        }
    };

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable({"name"})
    public boolean isValid(){
        return !TextUtils.isEmpty(mName);
    }

    @Override
    public int describeContents() {
        return 0;
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
    }
}
