package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciDerslerKisasinav extends AppCompatActivity {

    ImageButton geri_buton;
    Button gonder_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_kisasinav);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerKisasinav.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gonder_buton = (Button)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Veri Tabanı üzerinden işlem yapılacak

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}