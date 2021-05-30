package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

public class OgretmenDuyuruEkle extends AppCompatActivity {

    ImageButton geri_buton;
    Button gönder_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_duyuru_ekle);

        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(OgretmenDuyuruEkle.this, OgretmenDuyuru.class);
                startActivity(ıntent);
            }
        });

        gönder_buton = (Button)findViewById(R.id.gönder_buton);
        gönder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), OgretmenDuyuru.class));
                    }
                }, 1000); // 1sn bekliyor.

                // Toast ile ekrana bilgi yazısı yazdırılacak.
            }
        });
    }
}