package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class OgretmenAnamenu extends AppCompatActivity {

    ImageButton ayar_buton;
    Button duyuru_buton;
    Button derskontrol_buton;
    Button dersler_buton;
    Button sohbet_buton;
    TextView isim;
    ImageView profil;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_anamenu);

        isim = findViewById(R.id.ogretmen_isim);
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
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenAyarlar.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        duyuru_buton = (Button)findViewById(R.id.duyuru_buton);
        duyuru_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenDuyuru.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        derskontrol_buton = (Button)findViewById(R.id.derskontrol_buton);
        derskontrol_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenDersKontrolDersler.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        dersler_buton = (Button)findViewById(R.id.dersler_buton);
        dersler_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenDerslerDersListesi.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        sohbet_buton = (Button)findViewById(R.id.sohbet_buton);
        sohbet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenSohbetListesi.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}