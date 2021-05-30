package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

public class OgretmenDersKontrol extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton ogrenci_ekle_buton;
    Button qrkod_ac_buton;
    Button ders_bitir_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_ders_kontrol);

        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDersKontrol.this, OgretmenDersKontrolDersler.class);
                startActivity(ıntent);
            }
        });

        ogrenci_ekle_buton = (ImageButton)findViewById(R.id.ogrenci_ekle_buton);
        ogrenci_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });

        qrkod_ac_buton = (Button)findViewById(R.id.qrkod_ac_buton);
        qrkod_ac_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDersKontrol.this, OgretmenQrKod.class);
                startActivity(ıntent);
            }
        });

        ders_bitir_buton = (Button)findViewById(R.id.ders_bitir_buton);
        ders_bitir_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), OgretmenAnamenu.class));
                    }
                }, 1000); // 1sn bekliyor.

                // Toast ile ekrana bilgi yazısı yazdırılacak.
            }
        });
    }
}