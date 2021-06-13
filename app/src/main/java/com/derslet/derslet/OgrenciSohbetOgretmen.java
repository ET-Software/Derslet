package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciSohbetOgretmen extends AppCompatActivity {

    ImageButton geri_buton;
    Button ogretmen0_buton;
    ImageButton buton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_sohbet_ogretmen);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciSohbetOgretmen.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ogretmen0_buton = (Button)findViewById(R.id.ogretmen0_buton);
        ogretmen0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciSohbetOgretmen.this, OgrenciSohbetOgretmenMesajlasma.class);
                startActivity(intent);
                OgrenciSohbetOgretmen.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciSohbetOgretmen.this, OgrenciSohbetDers.class);
                startActivity(intent);
                finish();
                OgrenciSohbetOgretmen.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}