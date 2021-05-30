package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgretmenDersKontrolDersler extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_isim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_ders_kontrol_dersler);

        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDersKontrolDersler.this, OgretmenAnamenu.class);
                startActivity(ıntent);
            }
        });

        ders0_isim = (Button)findViewById(R.id.ders0_isim);
        ders0_isim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDersKontrolDersler.this, OgretmenDersKontrol.class);
                startActivity(ıntent);
            }
        });
    }
}