package com.gws20.dicoding.favoritemovieapp.entity;

public class LanguageEntity {
    public final static String ID = "iso_639_1";
    public final static String NAME = "name";

    private String iso_639_1;
    private String name;

    public LanguageEntity(String iso_639_1, String name) {
        this.iso_639_1 = iso_639_1;
        this.name = name;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getName() {
        return name;
    }
}
