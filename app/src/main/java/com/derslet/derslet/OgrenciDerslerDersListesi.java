package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgrenciDerslerDersListesi extends AppCompatActivity {

    ImageButton geri_buton;
    ListView ders_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_ders_listesi);

        ders_listesi = (ListView) findViewById(R.id.ogrenci_ders_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> dersidleri = new ArrayList<String>(); // Veri tabanından çekilecek ders idleri
        ArrayList<String> ogretmenidleri = new ArrayList<String>(); // Veri tabanından çekilecek ogretmen idleri
        ArrayList<DersListesiOgrenci> dersler = new ArrayList<>();


        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            //Öğrencinin aldığı derslerin id sorgusu
            String sql = "SELECT * FROM ogrencidersler WHERE ogrenciid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                dersidleri.add(rs.getString("dersid"));
            }


            // Öğrencinin aldığı derslerin sorgusu
            for(String dersid : dersidleri){
                sql = "SELECT * FROM dersler WHERE id = '" + dersid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    dersler.add(new DersListesiOgrenci(rs.getString("dersadi"),"", rs.getString("kisisayisi")));
                    ogretmenidleri.add(rs.getString("ogretmenid"));
                }
            }

            // Öğrencinin aldığı derslerin öğretmen sorgusu
            int sayac = 0;
            for(String ogretmenid : ogretmenidleri){
                sql = "SELECT * FROM kullanicilar WHERE id = '" + ogretmenid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                   dersler.get(sayac).setDers_ogretmen(rs.getString("isim") + " " + rs.getString("soyisim"));
                }
                sayac++;
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Derslerin listelenmesi
        ders_listesi.setAdapter(new DersListesiOgrenciAdapter(this, R.layout.list_ogrenci_dersler, dersler));

        //Ders listesinin tıklama işlemleri
        ders_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent=new Intent(OgrenciDerslerDersListesi.this, OgrenciDerslerGonderi.class);
                intent.putExtra("DERS_ID", dersidleri.get(pos));
                intent.putExtra("DERS_ADI", dersler.get(pos).getDers_isim());
                startActivity(intent);
                OgrenciDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}