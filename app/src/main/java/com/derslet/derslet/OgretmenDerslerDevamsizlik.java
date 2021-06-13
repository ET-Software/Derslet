package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgretmenDerslerDevamsizlik extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_devamsizlik;
    ImageButton buton1;
    ImageButton buton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_devamsizlik);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ders0_devamsizlik = (Button)findViewById(R.id.ders0_devamsizlik);
        ders0_devamsizlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDevamsizlikDetay.class);
                startActivity(intent);
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDerslerGonderi.class);
                startActivity(intent);
                finish();
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDerslerDegerlendirme.class);
                startActivity(intent);
                finish();
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}