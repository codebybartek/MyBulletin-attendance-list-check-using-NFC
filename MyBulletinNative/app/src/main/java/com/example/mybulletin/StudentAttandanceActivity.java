package com.example.mybulletin;

import android.app.PendingIntent;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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
import com.example.mybulletin.objects.Student;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentAttandanceActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    AttandanceListAdapter adapter;

    public static ArrayList<Student> presentStudents = new ArrayList<>();
    int courseSelected = SelectYourCourse.courseSelected;
    int exerciseSelected = SelectExercise.exerciseSelected;

    private String attandanceListUrl = "http://vps669485.ovh.net/api/attandancelist";

    Group group = MainActivity.courses.get(courseSelected).getGroup();

    public ArrayList<Exercise> exercises = courses.get(courseSelected).getExercises();
    ArrayList<Student> students;
    public static ArrayList<Course> courses = SelectYourCourse.coursesTemp;


    String[] datesPresence;
    String[] studentsOnPresence;
    String[] activitiesOnPresence;


    //public static Exercise exercise1;

    Intent intent;
    ListView mListView;

    private final String[][] techList = new String[][]{
            new String[]{
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
        setContentView(R.layout.addandance_list);

        if(courses.get(courseSelected).name == "Free activity"){
            students = courses.get(courseSelected).exercises.get(exerciseSelected).studentsSimpleActivity;
        }else{
            students = group.getStudents();
        }

        //presentStudents.clear();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        if(strDate.equals(exercises.get(exerciseSelected).activityDate.toString())){
            Toast.makeText(this,  "Tak", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,  "You could only check attandance list on the same day", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, Menu.class);
            startActivity(intent);
        }
        if(exercises.get(exerciseSelected).checked == 1){
            Toast.makeText(this,  "This exercise was checked before", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, Menu.class);
            startActivity(intent);
        }




        //exercise1 = new Exercise(1, "1", "2019-04-22", "12:30", 120, 0);

        mListView = (ListView) findViewById(R.id.listView);

        adapter = new AttandanceListAdapter(this, R.layout.adapter_view_layout, presentStudents);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    @Override
    protected void onPause() {
        super.onPause();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            String tag = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
            int searchListLength = students.size();
            for (int i = 0; i < searchListLength; i++) {
                if (students.get(i).getTagId().contains(tag)) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = dateFormat.format(date);
                    students.get(i).setTagData(strDate);
                    presentStudents.add(students.get(i));
                    mListView.setAdapter(adapter);
                }
            }
        }
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    public void addAttandanceList( View v) {
        exercises.get(exerciseSelected).setStudentsPresence(presentStudents);
        final ArrayList<Student> studentsPresence = exercises.get(exerciseSelected).studentsPresence;
        studentsOnPresence = new String[studentsPresence.size()];
        activitiesOnPresence = new String[studentsPresence.size()];
        datesPresence = new String[studentsPresence.size()];
        for(int i = 0; i < studentsPresence.size(); i++) {
            final int finalI = i;
            if (!isNetworkAvailable()) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = dateFormat.format(date);
                studentsOnPresence[i] = String.valueOf(presentStudents.get(finalI).getID());
                activitiesOnPresence[i] = String.valueOf(exercises.get(exerciseSelected).activity_id);
                datesPresence[i] = strDate;
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, attandanceListUrl,
                        new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject obj = new JSONObject(response);
                                    String token = obj.getString("created");

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
                        headers.put("Authorization", "Bearer " + MainActivity.token);
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Date date = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String strDate = dateFormat.format(date);

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("data_presence", strDate);
                        params.put("student_id", String.valueOf(presentStudents.get(finalI).getID()));
                        params.put("activity_id", String.valueOf(exercises.get(exerciseSelected).activity_id));

                        return params;
                    }
                };


                // Access the RequestQueue through your singleton class.
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                //adding the string request to request queue
                requestQueue.add(stringRequest);
            }
        }

        if(!isNetworkAvailable()){
            saveAttandance(datesPresence, studentsOnPresence, activitiesOnPresence);
        }

        MainActivity.courses.get(courseSelected).addExercise(exercises.get(exerciseSelected));
        saveData();
        Toast.makeText(this, "Attandancelist was added!", Toast.LENGTH_SHORT).show();
        intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    private void saveData() {
        // courses.clear();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonCourses = gson.toJson(courses);
        editor.putString("courses", jsonCourses);
        String jsonSubjects = gson.toJson(MainActivity.subjects);
        editor.putString("subjects", jsonSubjects);
        editor.apply();
    }

    private void saveAttandance(String[] dates, String[] students, String[] activities) {
        // courses.clear();
        SharedPreferences sharedPreferences = getSharedPreferences("attandance", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonDates = gson.toJson(dates);
        editor.putString("dates", jsonDates);
        String jsonStudents = gson.toJson(students);
        editor.putString("students", jsonStudents);
        String jsonActivities = gson.toJson(activities);
        editor.putString("activities", jsonActivities);
        editor.apply();
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
}