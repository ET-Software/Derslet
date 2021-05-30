package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OgretmenDuyuru extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton duyuru_ekle_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_duyuru);

        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDuyuru.this, OgretmenAnamenu.class);
                startActivity(ıntent);
            }
        });

        duyuru_ekle_buton = (ImageButton)findViewById(R.id.duyuru_ekle_buton);
        duyuru_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDuyuru.this, OgretmenDuyuruEkle.class);
                startActivity(ıntent);
            }
        });
    }
}