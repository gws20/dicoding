package com.gws20.dicoding.moviecatalogue.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CastEntity implements Parcelable {
    public final static String ID = "id";
    public final static String IMG = "img";
    public final static String NAME = "name";

    private int id;
    private String name;
    private String img;

    public CastEntity(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    private CastEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        img = in.readString();
    }

    public static final Creator<CastEntity> CREATOR = new Creator<CastEntity>() {
        @Override
        public CastEntity createFromParcel(Parcel in) {
            return new CastEntity(in);
        }

        @Override
        public CastEntity[] newArray(int size) {
            return new CastEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(img);
    }
}
