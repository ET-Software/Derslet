package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciDerslerDevamsizlik extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton1;
    ImageButton buton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_devamsizlik);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDevamsizlik.this, OgrenciDerslerGonderi.class);
                startActivity(intent);
                finish();
                OgrenciDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDevamsizlik.this, OgrenciDerslerDegerlendirme.class);
                startActivity(intent);
                finish();
                OgrenciDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}