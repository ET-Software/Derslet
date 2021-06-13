package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciDerslerDersListesi extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_ders_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ders0_buton = (Button)findViewById(R.id.ders0_buton);
        ders0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDersListesi.this, OgrenciDerslerGonderi.class);
                startActivity(intent);
                OgrenciDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}