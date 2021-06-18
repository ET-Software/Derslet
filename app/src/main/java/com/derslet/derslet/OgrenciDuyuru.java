package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgrenciDuyuru extends AppCompatActivity {

    ImageButton geri_buton;
    AutoCompleteTextView ders_secim;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_duyuru);

        ders_secim = findViewById(R.id.ic_liste); // Açılır ders filtresi

        ArrayList<String> dersidleri = new ArrayList<String>(); // Veri tabanından çekilecek ders idleri
        ArrayList<String> dersisimleri = new ArrayList<String>(); // Veri tabanından çekilecek ders isimleri

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

            //Öğrencinin aldığı derslerin isim sorgusu
            for(String dersid : dersidleri){
                sql = "SELECT * FROM dersler WHERE id = '" + dersid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    dersisimleri.add(rs.getString("dersadi"));
                }
                else{
                    //Toast menü
                    Toast toast = Toast.makeText(getApplicationContext(), "Ders bilgileri çekilirken hata alındı!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Ders isimlerinin açılır filtre listesine eklenmesi
        ArrayAdapter<String> ders_liste = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, dersisimleri);
        ders_secim.setAdapter(ders_liste);

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}