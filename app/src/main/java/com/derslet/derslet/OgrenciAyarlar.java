package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgrenciAyarlar extends AppCompatActivity {

    ImageButton geri_buton;
    Button kaydet_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_ayarlar);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciAyarlar.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        kaydet_buton = (Button)findViewById(R.id.kaydet_buton);
        kaydet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Veri tabanı üzerinden işlem yapılacak

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}