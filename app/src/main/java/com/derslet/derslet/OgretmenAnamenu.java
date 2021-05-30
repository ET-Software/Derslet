package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgretmenAnamenu extends AppCompatActivity {

    ImageButton ayar_buton;
    Button duyuru_buton;
    Button derskontrol_buton;
    Button dersler_buton;
    Button sohbet_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_anamenu);

        ayar_buton = (ImageButton)findViewById(R.id.ayar_buton);
        ayar_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenAnamenu.this, OgretmenAyarlar.class);
                startActivity(ıntent);
            }
        });

        duyuru_buton = (Button)findViewById(R.id.duyuru_buton);
        duyuru_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenAnamenu.this, OgretmenDuyuru.class);
                startActivity(ıntent);
            }
        });

        derskontrol_buton = (Button)findViewById(R.id.derskontrol_buton);
        derskontrol_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenAnamenu.this, OgretmenDersKontrolDersler.class);
                startActivity(ıntent);
            }
        });

        dersler_buton = (Button)findViewById(R.id.dersler_buton);
        dersler_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenAnamenu.this, OgretmenDerslerDersListesi.class);
                startActivity(ıntent);
            }
        });

        sohbet_buton = (Button)findViewById(R.id.sohbet_buton);
        sohbet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenAnamenu.this, OgretmenSohbetListesi.class);
                startActivity(ıntent);
            }
        });
    }
}