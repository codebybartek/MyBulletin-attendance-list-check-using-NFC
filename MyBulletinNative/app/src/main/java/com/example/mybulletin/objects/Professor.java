package com.example.mybulletin.objects;

import java.util.ArrayList;

public class Professor {

    private int ID;
    private String name;
    private String tagId;
    private String mobileToken;
    public ArrayList<Course> courses;

    public Professor(int ID, String name, String tagId, String mobileToken, ArrayList<Course> courses) {
        this.ID = ID;
        this.name = name;
        this.tagId = tagId;
        this.mobileToken = mobileToken;
        this.courses = courses;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getTagId() {
        return tagId;
    }

    public String getMobileToken() {
        return mobileToken;
    }
    public void setMobileToken(String mobileToken) {
        this.mobileToken = mobileToken;
    }
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
