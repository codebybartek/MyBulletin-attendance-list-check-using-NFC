package com.example.mybulletin.objects;

import java.util.ArrayList;

public class Exercise {
    public int activity_id;
    public String title;
    public String activityDate;
    public String hour;
    public  int duration;
    public int checked;
    public ArrayList<Student> studentsPresence = new ArrayList<>();
    public ArrayList<Student> studentsSimpleActivity = new ArrayList<>();

    public Exercise(int activity_id, String title, String activityDate, String hour, int duration, int checked) {
        this.activity_id = activity_id;
        this.title = title;
        this.activityDate = activityDate;
        this.hour = hour;
        this.duration = duration;
        this.checked = checked;
        this.studentsSimpleActivity = null;
    }

    public void setStudentsPresence(ArrayList<Student> studentsPresence) {
        this.studentsPresence = studentsPresence;
    }
    public void setStudentsSimpleActivity(ArrayList<Student> studentsSimpleActivity) {
        this.studentsSimpleActivity = studentsSimpleActivity;
    }

}
