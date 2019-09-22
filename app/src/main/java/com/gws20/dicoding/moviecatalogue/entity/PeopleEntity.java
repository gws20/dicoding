package com.gws20.dicoding.moviecatalogue.entity;

public class PeopleEntity {
    public final static String ID = "id";
    public final static String IMG = "profile_path";
    public final static String NAME = "name";
    public final static String JOB = "job";

    private int id;
    private String name;
    private String profile_path;
    private String job;

    public PeopleEntity(int id, String name, String profile_path, String job) {
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getJob() {
        return job;
    }
}
