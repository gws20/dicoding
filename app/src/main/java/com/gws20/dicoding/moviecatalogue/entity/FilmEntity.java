package com.gws20.dicoding.moviecatalogue.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FilmEntity implements Parcelable{
    public final static String ID = "id";
    public final static String SUBJECT = "subject";
    public final static String DESC = "desc";
    public final static String IMG = "img";
    public final static String TIME = "time";
    public final static String KATEGORI = "kategori";
    public final static String PRODUSER = "produser";
    public final static String PRODUKSI = "produksi";
    public final static String SUTRADARA = "sutradara";
    public final static String PENULIS = "penulis";
    public final static String JENIS = "jenis";
    public final static String CAST = "cast";

    private int id;
    private String subject;
    private String desc;
    private String img;
    private String time;
    private String kategori;
    private String produser;
    private String produksi;
    private String sutradara;
    private String penulis;
    private List<String> jenis;
    private List<CastEntity> cast;

    public FilmEntity(int id, String subject, String desc, String img, String time, String kategori, String produser, String produksi, String sutradara, String penulis, List<String> jenis, List<CastEntity> cast) {
        this.id = id;
        this.subject = subject;
        this.desc = desc;
        this.img = img;
        this.time = time;
        this.kategori = kategori;
        this.produser = produser;
        this.produksi = produksi;
        this.sutradara = sutradara;
        this.penulis = penulis;
        this.jenis = jenis;
        this.cast = cast;
    }

    private FilmEntity(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        desc = in.readString();
        img = in.readString();
        time = in.readString();
        kategori = in.readString();
        produser = in.readString();
        produksi = in.readString();
        sutradara = in.readString();
        penulis = in.readString();
        jenis = in.createStringArrayList();
        cast = in.createTypedArrayList(CastEntity.CREATOR);
    }

    public static final Creator<FilmEntity> CREATOR = new Creator<FilmEntity>() {
        @Override
        public FilmEntity createFromParcel(Parcel in) {
            return new FilmEntity(in);
        }

        @Override
        public FilmEntity[] newArray(int size) {
            return new FilmEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return img;
    }

    public String getProduser() {
        return produser;
    }

    public String getProduksi() {
        return produksi;
    }

    public String getSutradara() {
        return sutradara;
    }

    public String getPenulis() {
        return penulis;
    }

    public List<String> getJenis() {
        return jenis;
    }

    public List<CastEntity> getCast() {
        return cast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subject);
        dest.writeString(desc);
        dest.writeString(img);
        dest.writeString(time);
        dest.writeString(kategori);
        dest.writeString(produser);
        dest.writeString(produksi);
        dest.writeString(sutradara);
        dest.writeString(penulis);
        dest.writeStringList(jenis);
        dest.writeTypedList(cast);
    }
}
