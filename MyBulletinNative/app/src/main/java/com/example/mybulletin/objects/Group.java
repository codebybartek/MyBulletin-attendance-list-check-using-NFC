package com.example.mybulletin.objects;

import java.util.ArrayList;

public class Group {

    public String name;
    public ArrayList<Student> students = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }


    public Group(String name, ArrayList<Student> students) {
        this.name = name;
        this.students = students;
    }
}
