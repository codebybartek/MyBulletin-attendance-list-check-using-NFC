


package com.example.mybulletin;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.join;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<Student> students = new ArrayList<>();
    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();

    private String courses_url = "http://vps669485.ovh.net/api/courses";
    private String login_url = "http://vps669485.ovh.net/api/loginMobileApp";
    private String subjects_url = "http://vps669485.ovh.net/api/subjects";
    private String attandanceListUrl = "http://vps669485.ovh.net/api/attandancelist";
    public static String token;
    public static Boolean isConnected = false;

    public static Professor professor1;

    public Group group;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    String[] studentsAttandance;
    String[] datesAttandance;
    String[] activitiesAttandance;

    // list of NFC technologies detected:
    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Professor", null);
        if(json !=null) {
            Type type = new TypeToken<Professor>() {
            }.getType();
            professor1 = gson.fromJson(json, type);
        }
        if(professor1 == null){
            Intent intentt = new Intent(this, RegisterActivity.class);
            startActivity(intentt);
        }else {
            professor1.courses = courses;
            if(isNetworkAvailable()){
                Toast.makeText(this, "Network available!", Toast.LENGTH_SHORT).show();
                isConnected = true;
                SharedPreferences sharedAttandance = getSharedPreferences("attandance", MODE_PRIVATE);
                Gson gson4 = new Gson();
                String jsonDates = sharedAttandance.getString("dates", null);
                Type type3 = new TypeToken<String[]>() {}.getType();
                datesAttandance = gson4.fromJson(jsonDates, type3);
                String jsonStudents = sharedAttandance.getString("students", null);
                studentsAttandance = gson4.fromJson(jsonStudents, type3);
                String jsonActivities = sharedAttandance.getString("activities", null);
                activitiesAttandance = gson4.fromJson(jsonActivities, type3);
                getToken();
            }else{
                Gson gson2 = new Gson();
                String jsonCourses = sharedPreferences.getString("courses", null);
                Type type1 = new TypeToken<ArrayList<Course>>() {}.getType();
                courses = gson2.fromJson(jsonCourses, type1);

                Gson gson3 = new Gson();
                String jsonSubjects = sharedPreferences.getString("subjects", null);
                Type type2 = new TypeToken<ArrayList<Subject>>() {}.getType();
                subjects = gson3.fromJson(jsonSubjects, type2);
            }
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

     boolean isNetworkAvailable() {
        // Checking internet connectivity
        ConnectivityManager connectivityMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connectivityMgr != null) {
            activeNetwork = connectivityMgr.getActiveNetworkInfo();
        }
        return activeNetwork != null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            String tag = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            if (tag.equals(professor1.getTagId())) {
                Intent intentt = new Intent(this, SelectSubject.class);
                startActivity(intentt);
            }
        }
    }

    public void addAttandance(final String[] datesPresence, final String[] studentsOnPresence, final String[] activitiesOnPresence) {
        Toast.makeText(this, String.valueOf(datesPresence.length), Toast.LENGTH_SHORT).show();
        for(int i = 0; i < datesPresence.length; i++) {
            final int finalI = i;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, attandanceListUrl,
                        new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject obj = new JSONObject(response);
                                    String token = obj.getString("created");
                                    clearData();

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
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("data_presence", datesPresence[finalI]);
                        params.put("student_id", studentsOnPresence[finalI]);
                        params.put("activity_id", activitiesOnPresence[finalI]);

                        return params;
                    }
                };


                // Access the RequestQueue through your singleton class.
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                //adding the string request to request queue
                requestQueue.add(stringRequest);
        }
    }

    private void clearData(){
        SharedPreferences sharedPreferences = getSharedPreferences("attandance", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonDates = gson.toJson(null);
        editor.putString("dates", jsonDates);
        String jsonStudents = gson.toJson(null);
        editor.putString("students", jsonStudents);
        String jsonActivities = gson.toJson(null);
        editor.putString("activities", jsonActivities);
        editor.apply();
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    private void getToken() {
        if(professor1 !=null) {
            Toast.makeText(this,  professor1.getMobileToken(), Toast.LENGTH_SHORT).show();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            token = obj.getString("userToken");
                            loadSubjects();
                            loadCourses();
                            if(datesAttandance != null){
                                addAttandance(datesAttandance, studentsAttandance, activitiesAttandance);
                            }


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
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("tagId", professor1.getTagId());
                params.put("mobile_token", professor1.getMobileToken());

                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void loadSubjects() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, subjects_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                Integer subjectId = ObjectData.getInt("subject_id");
                                String subjectName = ObjectData.getString("name");

                                JSONArray responseCourses = ObjectData.getJSONArray("courses");

                                ArrayList<Course> coursesSubject = new ArrayList<>();
                                //Create String out of the Parsed JSON

                                for (int j = 0; j < responseCourses.length(); j++) {
                                    JSONObject ObjectDataCourses = responseCourses.getJSONObject(j);
                                    String courseName = ObjectDataCourses.getString("name");

                                    Course course = new Course(courseName, null);

                                    coursesSubject.add(course);

                                }

                                Subject subject = new Subject(subjectName, coursesSubject);
                                subjects.add(subject);
                            }

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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void loadCourses() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, courses_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                Integer courseId = ObjectData.getInt("id");
                                String courseName = ObjectData.getString("name");

                                JSONArray responseArrayGroup = ObjectData.getJSONArray("group");

                                JSONObject ObjectDataGroup = responseArrayGroup.getJSONObject(0);
                                Integer groupId = ObjectDataGroup.getInt("group_id");
                                String groupName = ObjectDataGroup.getString("name");


                                JSONArray responseArrayActivity = ObjectData.getJSONArray("activities");

                                ArrayList<Exercise> exercises = new ArrayList<>();
                                //Create String out of the Parsed JSON

                                Exercise exercise = null;

                                for (int j = 0; j < responseArrayActivity.length(); j++) {
                                    JSONObject ObjectDataActivity = responseArrayActivity.getJSONObject(j);
                                    Integer activity_id = ObjectDataActivity.getInt("activity_id");
                                    String title = ObjectDataActivity.getString("title");
                                    String activityDate = ObjectDataActivity.getString("activityDate");
                                    String hour = ObjectDataActivity.getString("hour");
                                    Integer duration = ObjectDataActivity.getInt("duration");
                                    Integer checked = ObjectDataActivity.getInt("checked");

                                    exercise = new Exercise(activity_id, title, activityDate, hour, duration, checked);

                                    exercises.add(exercise);

                                }

                                addCourse(groupId, courseName, exercises);

                            }

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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void addCourse(int groupId, final String courseName, final ArrayList<Exercise> exercises) {
        String groups_url = "http://vps669485.ovh.net/api/groups/" + groupId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, groups_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                String groupName = ObjectData.getString("name");

                                JSONArray responseArrayStudent = ObjectData.getJSONArray("students");

                                ArrayList<Student> studentsInGroup = new ArrayList<>();

                                //Create String out of the Parsed JSON

                                for (int j = 0; j < responseArrayStudent.length(); j++) {
                                    JSONObject ObjectDataStudent = responseArrayStudent.getJSONObject(j);
                                    Integer studentId = ObjectDataStudent.getInt("student_id");
                                    String studentName = ObjectDataStudent.getString("name");
                                    String tagId = ObjectDataStudent.getString("tagId");

                                    Student student = new Student(studentName, studentId.toString(),tagId, false, "");
                                    studentsInGroup.add(student);
                                }

                                group = new Group(groupName, studentsInGroup);

                                Course course = new Course(courseName,  group);
                                course.setExercises(exercises);
                                courses.add(course);

                            }

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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
