package com.example.botanic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.botanic.MainActivity;
import com.example.botanic.R;

public class Start_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_fragment);
    }
    public void toMainMenu (View view) {
        Intent toMainMenu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(toMainMenu);
    }
}