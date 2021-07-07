package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgretmenDersKontrol extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton ogrenci_ekle_buton;
    Button qrkod_ac_buton;
    Button ders_bitir_buton;
    TextView baslik;
    ListView ogrenci_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_ders_kontrol);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);

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

        ogrenci_listesi = (ListView) findViewById(R.id.ogrenci_listesi);
        ArrayList<DersKontrol> arrayList = new ArrayList<>();
        arrayList.add(new DersKontrol("010", "Öğrenci", "1"));
        arrayList.add(new DersKontrol("011", "Öğrenci", "1"));
        arrayList.add(new DersKontrol("012", "Öğrenci", "1"));
        arrayList.add(new DersKontrol("013", "Öğrenci", "1"));
        arrayList.add(new DersKontrol("014", "Öğrenci", "1"));

        DersKontrolAdapter dersKontrolAdapter = new DersKontrolAdapter(this, R.layout.list_ders_kontrol, arrayList);
        ogrenci_listesi.setAdapter(dersKontrolAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}