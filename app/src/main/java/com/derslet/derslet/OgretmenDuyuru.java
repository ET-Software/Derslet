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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenDuyuru extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton duyuru_ekle_buton;
    AutoCompleteTextView ders_secim;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    ListView duyuru_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_duyuru);

        ders_secim = findViewById(R.id.ic_liste); // Açılır ders filtresi
        duyuru_listesi = (ListView) findViewById(R.id.duyuru_listesi);

        ArrayList<String> dersidleri = new ArrayList<String>(); // Veri tabanından çekilecek ders idleri
        ArrayList<String> dersisimleri = new ArrayList<String>(); // Veri tabanından çekilecek ders isimleri
        dersisimleri.add("---");
        ArrayList<ArrayList> duyurular = new ArrayList<ArrayList>();
        duyurular.add(new ArrayList<Duyuru>());

        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            //Öğretmenin verdiği derslerin id ve isim sorgusu
            String sql = "SELECT * FROM dersler WHERE ogretmenid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                dersidleri.add(rs.getString("id"));
                dersisimleri.add(rs.getString("dersadi"));
            }

            for(String dersid : dersidleri){
                sql = "SELECT * FROM duyuru WHERE dersid = '" + dersid + "' ";
                rs = stmt.executeQuery(sql);
                ArrayList <Duyuru> ders_duyuruları = new ArrayList<Duyuru>();
                while(rs.next()){
                    ders_duyuruları.add(new Duyuru(rs.getString("tarih"), rs.getString("icerik")));
                }
                duyurular.add(ders_duyuruları);
                duyurular.get(0).addAll(ders_duyuruları);
            }

        }  catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Duyuruların listelenmesi
        duyuru_listesi.setAdapter(new DuyuruAdapter(this, R.layout.list_duyuru, duyurular.get(0)));

        //Ders isimlerinin açılır filtre listesine eklenmesi
        ArrayAdapter<String> dersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dersisimleri);
        ders_secim.setThreshold(1);
        ders_secim.setAdapter(dersAdapter);
        ders_secim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                duyuru_listesi.setAdapter(new DuyuruAdapter(OgretmenDuyuru.this, R.layout.list_duyuru, duyurular.get(arg2)));
            }
        });

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDuyuru.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        duyuru_ekle_buton = (ImageButton)findViewById(R.id.duyuru_ekle_buton);
        duyuru_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDuyuru.this, OgretmenDuyuruEkle.class);
                startActivity(intent);
                OgretmenDuyuru.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}