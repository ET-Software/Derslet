package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OgrenciAnamenu extends AppCompatActivity {

    ImageButton ayar_buton;
    ImageButton duyuru_buton;
    ImageButton qrkod_buton;
    ImageButton dersler_buton;
    ImageButton sohbet_buton;
    TextView isim;
    ImageView profil;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_anamenu);

        isim = findViewById(R.id.ogrenci_isim);
        profil = findViewById(R.id.profil);

        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM kullanicilar WHERE id = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                isim.setText(rs.getString("isim") + " " + rs.getString("soyisim"));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        //Butonlar
        ayar_buton = (ImageButton)findViewById(R.id.ayar_buton);
        ayar_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciAyarlar.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        qrkod_buton = (ImageButton)findViewById(R.id.qrkod_buton);
        qrkod_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciQrKod.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        duyuru_buton = (ImageButton)findViewById(R.id.duyuru_buton);
        duyuru_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciDuyuru.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        dersler_buton = (ImageButton)findViewById(R.id.dersler_buton);
        dersler_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciDerslerDersListesi.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        sohbet_buton = (ImageButton)findViewById(R.id.sohbet_buton);
        sohbet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciAnamenu.this, OgrenciSohbetDers.class);
                startActivity(intent);
                OgrenciAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}