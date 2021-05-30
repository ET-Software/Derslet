package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Acilis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Giris.class));
            }
        }, 1000); // 1sn bekliyor.
    }
}