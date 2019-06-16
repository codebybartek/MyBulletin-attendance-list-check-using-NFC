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

import java.util.ArrayList;

public class SelectExercise extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    static Professor professor = MainActivity.professor1;

    static int courseSelected = SelectYourCourse.courseSelected;
    public static ArrayList<Course> courses = SelectYourCourse.coursesTemp;

    public static ArrayList<Exercise> exercises = courses.get(courseSelected).getExercises();
    static int exerciseSelected;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        exerciseSelected = -1;

        //Toast.makeText(this, courses.get(courseSelected).exercises.get(0).studentsSimpleActivity.get(0).getName(), Toast.LENGTH_SHORT).show();
        int countExercises = exercises.size();

        if(countExercises == 0){
            intent = new Intent(this, Menu.class);
            startActivity(intent);
        }

        String[] exerciseName = new String[countExercises];

        for (int i = 0; i < countExercises; i++)    {
            exerciseName[i] = exercises.get(i).activityDate;
        }

        final String[] paths = exerciseName;

        spinner = (Spinner) findViewById(R.id.spinnerExercise);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectExercise.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        exerciseSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onContinueClicked( View v) {
        intent = new Intent(this, Menu.class);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

