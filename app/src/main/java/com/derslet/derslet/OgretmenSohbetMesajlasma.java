package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

public class OgretmenSohbetMesajlasma extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton dosyaekle_buton;
    ImageButton gonder_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_sohbet_mesajlasma);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenSohbetMesajlasma.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        dosyaekle_buton = (ImageButton)findViewById(R.id.dosyaekle_buton);
        dosyaekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });

        gonder_buton = (ImageButton)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}