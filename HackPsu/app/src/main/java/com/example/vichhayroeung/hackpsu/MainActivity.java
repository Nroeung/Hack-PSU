package com.example.vichhayroeung.hackpsu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPic(View view) {
        Log.d("Self", "This is my messagePic");
        Intent intent = new Intent(MainActivity.this, datapage.class);
        startActivity(intent);
    }

    public void onLights(View view) {
        Log.d("Self", "This is my light page");
        Intent intent = new Intent(MainActivity.this, lights.class);
        startActivity(intent);
    }

}
