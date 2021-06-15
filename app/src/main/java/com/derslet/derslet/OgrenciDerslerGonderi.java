package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OgrenciDerslerGonderi extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton2;
    ImageButton buton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_gonderi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerGonderi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerGonderi.this, OgrenciDerslerDegerlendirme.class);
                startActivity(intent);
                finish();
                OgrenciDerslerGonderi.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerGonderi.this, OgrenciDerslerDevamsizlik.class);
                startActivity(intent);
                finish();
                OgrenciDerslerGonderi.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}