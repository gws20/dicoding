package com.gws20.dicoding.moviecatalogue.entity;

public class CompanyEntity {
    public final static String ID = "id";
    public final static String LOGO_PATH = "logo_path";
    public final static String NAME = "name";
    public final static String ORIGINAL_COUNTRY = "origin_country";

    private int id;
    private String logo_path;
    private String name;
    private String origin_country;

    public CompanyEntity(int id, String logo_path, String name, String origin_country) {
        this.id = id;
        this.logo_path = logo_path;
        this.name = name;
        this.origin_country = origin_country;
    }

    public int getId() {
        return id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public String getName() {
        return name;
    }

    public String getOrigin_country() {
        return origin_country;
    }
}
