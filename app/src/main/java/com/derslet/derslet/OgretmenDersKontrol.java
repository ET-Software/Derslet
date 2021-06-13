package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class OgretmenDersKontrol extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton ogrenci_ekle_buton;
    Button qrkod_ac_buton;
    Button ders_bitir_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_ders_kontrol);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDersKontrol.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ogrenci_ekle_buton = (ImageButton)findViewById(R.id.ogrenci_ekle_buton);
        ogrenci_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });

        qrkod_ac_buton = (Button)findViewById(R.id.qrkod_ac_buton);
        qrkod_ac_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDersKontrol.this, OgretmenQrKod.class);
                startActivity(intent);
                OgretmenDersKontrol.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ders_bitir_buton = (Button)findViewById(R.id.ders_bitir_buton);
        ders_bitir_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dersSonlandirmaDurumu = "Ders başarıyla sonlandırıldı!"; //databaseden çekilecek veriye göre değişecek

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), OgretmenAnamenu.class));
                                OgretmenDersKontrol.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                timer.cancel();
                            }
                        });

                    }
                }, 1000);

                Toast toast = Toast.makeText(getApplicationContext(), dersSonlandirmaDurumu, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}