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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), Giris.class));
                        finish();
                        Acilis.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        timer.cancel();
                    }
                });

            }
        }, 1000);
    }
}