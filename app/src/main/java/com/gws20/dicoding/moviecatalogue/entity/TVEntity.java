package com.gws20.dicoding.moviecatalogue.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TVEntity implements Parcelable {
    public final static String ID = "id";
    public final static String SUBJECT = "subject";
    public final static String DESC = "desc";
    public final static String IMG = "img";
    public final static String TIME_BEGIN = "timeBegin";
    public final static String TIME_END = "timeEnd";
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
    private long timeBegin;
    private long timeEnd;
    private String kategori;
    private String produser;
    private String produksi;
    private String sutradara;
    private String penulis;
    private List<String> jenis;
    private List<CastEntity> cast;

    public TVEntity(int id, String subject, String desc, String img, long timeBegin,
                    long timeEnd, String kategori, String produser, String produksi,
                    String sutradara, String penulis, List<String> jenis, List<CastEntity> cast) {
        this.id = id;
        this.subject = subject;
        this.desc = desc;
        this.img = img;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.kategori = kategori;
        this.produser = produser;
        this.produksi = produksi;
        this.sutradara = sutradara;
        this.penulis = penulis;
        this.jenis = jenis;
        this.cast = cast;
    }

    protected TVEntity(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        desc = in.readString();
        img = in.readString();
        timeBegin = in.readLong();
        timeEnd = in.readLong();
        kategori = in.readString();
        produser = in.readString();
        produksi = in.readString();
        sutradara = in.readString();
        penulis = in.readString();
        jenis = in.createStringArrayList();
        cast = in.createTypedArrayList(CastEntity.CREATOR);
    }

    public static final Creator<TVEntity> CREATOR = new Creator<TVEntity>() {
        @Override
        public TVEntity createFromParcel(Parcel in) {
            return new TVEntity(in);
        }

        @Override
        public TVEntity[] newArray(int size) {
            return new TVEntity[size];
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

    public long getTimeBegin() {
        return timeBegin;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public String getKategori() {
        return kategori;
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
        dest.writeLong(timeBegin);
        dest.writeLong(timeEnd);
        dest.writeString(kategori);
        dest.writeString(produser);
        dest.writeString(produksi);
        dest.writeString(sutradara);
        dest.writeString(penulis);
        dest.writeStringList(jenis);
        dest.writeTypedList(cast);
    }
}
