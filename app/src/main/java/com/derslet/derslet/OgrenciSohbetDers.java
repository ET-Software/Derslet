package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciSohbetDers extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_buton;
    ImageButton buton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_sohbet_ders);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciSohbetDers.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ders0_buton = (Button)findViewById(R.id.ders0_buton);
        ders0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciSohbetDers.this, OgrenciSohbetDersMesajlasma.class);
                startActivity(intent);
                OgrenciSohbetDers.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciSohbetDers.this, OgrenciSohbetOgretmen.class);
                startActivity(intent);
                finish();
                OgrenciSohbetDers.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}