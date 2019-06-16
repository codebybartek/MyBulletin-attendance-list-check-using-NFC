package com.example.mybulletin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void newAttandanceList( View v) {
        Intent intent = new Intent(this, StudentAttandanceActivity.class);
        startActivity(intent);

    }

    public void showAttandanceList( View v) {
        Intent intent = new Intent(this, ShowList.class);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



}
