package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class OgrenciDerslerGonderi extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton2;
    ImageButton buton3;
    TextView baslik;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;

    ListView gonderi_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_gonderi);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        gonderi_listesi = (ListView) findViewById(R.id.ogrenci_gonderi_listesi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerGonderi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerGonderi.this, OgrenciDerslerDegerlendirme.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgrenciDerslerGonderi.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerGonderi.this, OgrenciDerslerDevamsizlik.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgrenciDerslerGonderi.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Gonderi> gonderiler = new ArrayList<Gonderi>();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            //Öğretmenin verdiği derslerin sorgusu
            String sql = "SELECT * FROM derslergonderi WHERE dersid = '" + ders_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                switch (rs.getInt("tip")){
                    case 0:
                        gonderiler.add(new Gonderi( rs.getString("baslik"),rs.getString("tarih"),rs.getString("icerik")));
                        break;
                    case 1:
                        gonderiler.add(new Gonderi(rs.getString("baslik"),rs.getString("tarih"),rs.getString("icerik"), rs.getString("baglanti")));
                        break;
                    case 2:
                        gonderiler.add(new Gonderi(rs.getString("baslik"),rs.getString("tarih"),rs.getString("icerik"), rs.getString("id"), rs.getInt("sorusayisi")));
                        break;
                    default:
                        System.out.println("Gönderi Hatası: Gönderi tipi limit dışı!");
                        break;
                }
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        // Gönderilerin tarihe göre sıralanması
        Collections.reverse(gonderiler);

        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String id = yerel_veriler.getString("Token","");

        gonderi_listesi.setAdapter(new GonderiAdapterOgrenci(this, R.layout.list_gonderi, gonderiler, ders_id, id, ders_adi));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}