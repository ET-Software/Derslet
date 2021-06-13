package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgretmenDerslerDegerlendirme extends AppCompatActivity {

    ImageButton geri_buton;
    Button degerlendirme0_buton;
    ImageButton buton1;
    ImageButton buton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_degerlendirme);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        degerlendirme0_buton = (Button)findViewById(R.id.degerlendirme0_buton);
        degerlendirme0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDegerlendirmeDetay.class);
                startActivity(intent);
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDerslerGonderi.class);
                startActivity(intent);
                finish();
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDerslerDevamsizlik.class);
                startActivity(intent);
                finish();
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}