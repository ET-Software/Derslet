package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

        // Öğrencinin kısa sınavı yapıp yapmadığı durumu kontrolü yapılacak.

        ArrayList<Gonderi> arrayList = new ArrayList<>();
        arrayList.add(new Gonderi(0,"Gönderi1", "01-01-2021", "Normal Gönderi", null));
        arrayList.add(new Gonderi(1,"Gönderi2", "02-02-2021", "Bağlantı Gönderi", "https://www.google.com.tr"));
        arrayList.add(new Gonderi(2,"Gönderi3", "03-03-2021", "Kısa Sınav Gönderi", "ID Yazılacak."));

        GonderiAdapterOgrenci gonderiAdapter = new GonderiAdapterOgrenci(this, R.layout.list_gonderi, arrayList);
        gonderi_listesi.setAdapter(gonderiAdapter);
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
                        gonderiler.add(new Gonderi(0, rs.getString("baslik"),rs.getString("tarih"),rs.getString("icerik"), null));
                        break;
                    case 1:
                        gonderiler.add(new Gonderi(1, rs.getString("baslik"),rs.getString("tarih"),rs.getString("icerik"), rs.getString("baglanti")));
                        break;
                    case 2:
                        gonderiler.add(new Gonderi(2, rs.getString("baslik"),rs.getString("tarih"),rs.getString("icerik"), rs.getString("id")));
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

        gonderi_listesi.setAdapter(new GonderiAdapterOgrenci(this, R.layout.list_gonderi, gonderiler));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}