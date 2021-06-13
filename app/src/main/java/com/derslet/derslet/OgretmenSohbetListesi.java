package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgretmenSohbetListesi extends AppCompatActivity {

    ImageButton geri_buton;
    Button ogrenci0_buton;
    ImageButton sohbet_ac_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_sohbet_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenSohbetListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ogrenci0_buton = (Button)findViewById(R.id.ogrenci0_buton);
        ogrenci0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenSohbetListesi.this, OgretmenSohbetMesajlasma.class);
                startActivity(intent);
                OgretmenSohbetListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        sohbet_ac_buton = (ImageButton)findViewById(R.id.sohbet_ac_buton);
        sohbet_ac_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}