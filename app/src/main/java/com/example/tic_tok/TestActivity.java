package com.example.tic_tok;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        Log.d("INFO =>","onCreate");
        //Получаем переданный возраст
        String name = getIntent().getStringExtra("name");
        //Получение возраста
        int age = getIntent().getIntExtra("age",0);
        boolean isStudent = getIntent()
                .getBooleanExtra("is_student", false);
        Toast.makeText(
                getApplicationContext(),
                String.format("name is %s, age is %d, is student %b", name, age, isStudent),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("INFO =>","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INFO =>","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("INFO =>","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("INFO =>","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("INFO =>","onDestroy");
    }
}