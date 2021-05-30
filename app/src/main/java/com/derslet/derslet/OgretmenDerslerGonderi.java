package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OgretmenDerslerGonderi extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton gonderi_ekle_buton;
    ImageButton buton2;
    ImageButton buton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_gonderi);

        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDerslerGonderi.this, OgretmenDerslerDersListesi.class);
                startActivity(ıntent);
            }
        });

        gonderi_ekle_buton = (ImageButton)findViewById(R.id.gonderi_ekle_buton);
        gonderi_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDerslerGonderi.this, OgretmenKisaSinavEkle.class);
                startActivity(ıntent);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDerslerGonderi.this, OgretmenDerslerDegerlendirme.class);
                startActivity(ıntent);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDerslerGonderi.this, OgretmenDerslerDevamsizlik.class);
                startActivity(ıntent);
            }
        });
    }
}