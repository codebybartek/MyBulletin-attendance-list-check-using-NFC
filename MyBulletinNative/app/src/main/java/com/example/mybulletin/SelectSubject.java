package com.example.mybulletin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybulletin.objects.Course;
import com.example.mybulletin.objects.Exercise;
import com.example.mybulletin.objects.Professor;
import com.example.mybulletin.objects.Subject;

import java.util.ArrayList;

public class SelectSubject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    public static ArrayList<Subject> subjects = MainActivity.subjects;

    static int subjectSelected;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        subjectSelected = -1;

        //Toast.makeText(this, courses.get(courseSelected).exercises.get(0).studentsSimpleActivity.get(0).getName(), Toast.LENGTH_SHORT).show();
        int countSubjects = subjects.size();

        String[] subjectName = new String[countSubjects];

        for (int i = 0; i < countSubjects; i++)    {
            subjectName[i] = subjects.get(i).getName();
        }

        final String[] paths = subjectName;

        spinner = (Spinner) findViewById(R.id.spinnerSubject);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectSubject.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        subjectSelected = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onContinueClicked( View v) {
        intent = new Intent(this, SelectYourCourse.class);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


