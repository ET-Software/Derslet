package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import java.util.Collections;

public class OgrenciDuyuru extends AppCompatActivity {

    ImageButton geri_buton;
    AutoCompleteTextView ders_secim;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    ListView duyuru_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_duyuru);

        ders_secim = (AutoCompleteTextView) findViewById(R.id.ic_liste); // Açılır ders filtresi
        duyuru_listesi = (ListView) findViewById(R.id.duyuru_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDuyuru.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

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
            //Öğrencinin aldığı derslerin id sorgusu
            String sql = "SELECT * FROM ogrencidersler WHERE ogrenciid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                dersidleri.add(rs.getString("dersid"));
            }

            // Öğrencinin aldığı derslerin isim sorgusu
            for(String dersid : dersidleri){
                sql = "SELECT * FROM dersler WHERE id = '" + dersid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    dersisimleri.add(rs.getString("dersadi"));
                }
            }
            int sayac = 1;
            for(String dersid : dersidleri){
                sql = "SELECT * FROM duyuru WHERE dersid = '" + dersid + "' ";
                rs = stmt.executeQuery(sql);
                ArrayList <Duyuru> ders_duyuruları = new ArrayList<Duyuru>();
                while(rs.next()){
                    ders_duyuruları.add(new Duyuru(rs.getString("tarih") + " - " + dersisimleri.get(sayac) , rs.getString("icerik")));
                }
                duyurular.add(ders_duyuruları);
                duyurular.get(0).addAll(ders_duyuruları);
                sayac++;
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // Duyuruların tarihe göre sıralanması
        for (int i=0; i<duyurular.size();i++){
            Collections.reverse(duyurular.get(i));
        }
        //Duyuruların listelenmesi
        duyuru_listesi.setAdapter(new DuyuruAdapter(this, R.layout.list_duyuru, duyurular.get(0)));

        //Ders isimlerinin açılır filtre listesine eklenmesi
        ArrayAdapter<String> dersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dersisimleri);
        ders_secim.setThreshold(1);
        ders_secim.setAdapter(dersAdapter);
        ders_secim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                duyuru_listesi.setAdapter(new DuyuruAdapter(OgrenciDuyuru.this, R.layout.list_duyuru, duyurular.get(pos)));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}