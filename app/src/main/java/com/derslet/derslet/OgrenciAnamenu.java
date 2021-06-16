package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciAnamenu extends AppCompatActivity {

    ImageButton ayar_buton;
    Button duyuru_buton;
    Button qrkod_buton;
    Button dersler_buton;
    Button sohbet_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_anamenu);

        //Butonlar
        ayar_buton = (ImageButton)findViewById(R.id.ayar_buton);
        ayar_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciAyarlar.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        qrkod_buton = (Button)findViewById(R.id.qrkod_buton);
        qrkod_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciQrKod.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        duyuru_buton = (Button)findViewById(R.id.duyuru_buton);
        duyuru_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciDuyuru.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        dersler_buton = (Button)findViewById(R.id.dersler_buton);
        dersler_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciDerslerDersListesi.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        sohbet_buton = (Button)findViewById(R.id.sohbet_buton);
        sohbet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciSohbetDers.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}