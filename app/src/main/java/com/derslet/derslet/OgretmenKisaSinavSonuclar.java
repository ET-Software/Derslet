package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class OgretmenKisaSinavSonuclar extends AppCompatActivity {

    ImageButton geri_buton;
    TextView baslik;
    ListView kisa_sinav_sonuclar;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String kisa_sinav_id;
    int soru_sayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_kisa_sinav_sonuclar);

        kisa_sinav_id = getIntent().getStringExtra("KISA_SINAV_ID");
        soru_sayisi = getIntent().getIntExtra("SORU_SAYISI",0);

        kisa_sinav_sonuclar = (ListView) findViewById(R.id.ogretmen_kisa_sinav_sonuclar_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenKisaSinavSonuclar.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> ogrenciidleri = new ArrayList<String>(); // Veri tabanından çekilecek ders idleri
        ArrayList<KisaSinavSonuclar> kisasinavSonuclar = new ArrayList<KisaSinavSonuclar>();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM sonuclar WHERE kisasinavid = '" + kisa_sinav_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                ogrenciidleri.add(rs.getString("ogrenciid"));
                kisasinavSonuclar.add(new KisaSinavSonuclar("", "", rs.getString("dogrusayisi") + " / " + soru_sayisi));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        // Veritabanı Sorgu İşlemleri
        try {
            for(int i=0; i<ogrenciidleri.size(); i++){
                String sql = "SELECT * FROM kullanicilar WHERE id = '" + ogrenciidleri.get(i) + "' ";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    kisasinavSonuclar.get(i).setOgrencinumara(rs.getString("kullanicino"));
                    kisasinavSonuclar.get(i).setOgrenciisim(rs.getString("isim") + " " + rs.getString("soyisim"));
                }


            }


        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Sonucların listelenmesi
        KisaSinavSonuclarAdapter kisaSinavSonuclarAdapter = new KisaSinavSonuclarAdapter(this, R.layout.list_ogretmen_kisa_sinav_sonuclar, kisasinavSonuclar);

        kisa_sinav_sonuclar.setAdapter(kisaSinavSonuclarAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}