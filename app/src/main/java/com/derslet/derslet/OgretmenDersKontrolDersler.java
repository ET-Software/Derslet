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

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDersKontrolDersler.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ders0_isim = (Button)findViewById(R.id.ders0_isim);
        ders0_isim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDersKontrolDersler.this, OgretmenDersKontrol.class);
                startActivity(intent);
                OgretmenDersKontrolDersler.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}