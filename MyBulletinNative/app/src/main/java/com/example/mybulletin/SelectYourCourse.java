package com.example.mybulletin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybulletin.objects.Course;
import com.example.mybulletin.objects.Exercise;
import com.example.mybulletin.objects.Group;
import com.example.mybulletin.objects.Professor;
import com.example.mybulletin.objects.Student;
import com.example.mybulletin.objects.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectYourCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    static Professor professor = MainActivity.professor1;
    static ArrayList<Course> courses = MainActivity.courses;
    static ArrayList<Subject> subjects = MainActivity.subjects;
    static int subjectSelected = SelectSubject.subjectSelected;
    static ArrayList<Course> coursesTemp = new ArrayList<>();

    private String activities_free_url = "http://vps669485.ovh.net/api/activitiesfree";

    public static int courseSelected;
    Intent intent;

    int countCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selct_your_course);

        courseSelected = -1;

        Subject subject = subjects.get(subjectSelected);
        for(int i = 0; i < subject.courses.size(); i++ ){
            for(int j = 0; j < courses.size(); j++ ){
                if(subject.courses.get(i).name.equals(courses.get(j).name)){
                    coursesTemp.add(courses.get(j));
                }
            }
        }
        subjectSelected = -1;
        countCourses = coursesTemp.size();

        String[] courseName = new String[countCourses+1];

        for (int i = 0; i < countCourses; i++) {
            courseName[i]= coursesTemp.get(i).name;
        }

        courseName[countCourses] = "Simple Activities";

        final String[] paths = courseName;

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectYourCourse.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(position == countCourses && MainActivity.isConnected){
                StringRequest stringRequest = new StringRequest(Request.Method.GET, activities_free_url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject obj = new JSONObject(response);
                                    JSONArray responseArray = obj.getJSONArray("data");

                                    Course courseFreeActivity = new Course("Free activity", null);

                                    ArrayList<Exercise> exercises = new ArrayList<>();
                                    Group group;

                                    for (int i = 0; i < responseArray.length(); i++) {
                                        JSONObject ObjectData = responseArray.getJSONObject(i);

                                        //Create String out of the Parsed JSON

                                        Integer activity_id = ObjectData.getInt("id");
                                        String title = ObjectData.getString("title");
                                        String activityDate = ObjectData.getString("activityDate");
                                        String hour = ObjectData.getString("hour");
                                        Integer duration = ObjectData.getInt("duration");
                                        Integer checked = ObjectData.getInt("checked");

                                        JSONArray responseArrayGroup = ObjectData.getJSONArray("group");
                                            JSONObject ObjectGroupData = responseArrayGroup.getJSONObject(0);

                                            ArrayList<Student> studentsInGroup = new ArrayList<>();

                                            JSONArray responseArrayStudent = ObjectGroupData.getJSONArray("students");

                                            for (int j = 0; j < responseArrayStudent.length(); j++) {
                                                JSONObject ObjectDataStudent = responseArrayStudent.getJSONObject(j);
                                                Integer studentId = ObjectDataStudent.getInt("student_id");
                                                String studentName = ObjectDataStudent.getString("name");
                                                String tagId = ObjectDataStudent.getString("tagId");

                                                Student student = new Student(studentName, studentId.toString(),tagId, false, "");
                                                studentsInGroup.add(student);
                                            }


                                        Exercise exercise = null;
                                        exercise = new Exercise(activity_id, title, activityDate, hour, duration, checked);
                                        exercise.setStudentsSimpleActivity(studentsInGroup);

                                        exercises.add(exercise);

                                    }
                                    courseFreeActivity.setExercises(exercises);
                                    courses.add(courseFreeActivity);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })

                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + MainActivity.token);
                        return headers;
                    }
                };

                // Access the RequestQueue through your singleton class.
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                //adding the string request to request queue
                requestQueue.add(stringRequest);
            }

        courseSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onContinueClicked( View v) {
        intent = new Intent(this, SelectExercise.class);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
