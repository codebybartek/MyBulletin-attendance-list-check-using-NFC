package com.example.mybulletin.objects;

import java.util.ArrayList;

public class Subject {
    public String name;
    public ArrayList<Course> courses = new ArrayList<>();

    public Subject(String name,  ArrayList<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
