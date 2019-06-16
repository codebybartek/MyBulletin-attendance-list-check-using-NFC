package com.example.mybulletin.objects;

import java.util.ArrayList;

public class Course {
    public String name;
    public Group group;
    public ArrayList<Exercise> exercises = new ArrayList<>();

    public Course(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise){
        this.exercises.add(exercise);
    }

}
