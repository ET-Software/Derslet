package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

public class OgretmenAnamenu extends AppCompatActivity {

    ImageButton duyuru_buton;
    ImageButton derskontrol_buton;
    ImageButton dersler_buton;
    ImageButton sohbet_buton;
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
                Bitmap bitmap = getBitmapFromURL(rs.getString("profilurl"));
                if(bitmap != null)profil.setImageBitmap(bitmap);
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        duyuru_buton = (ImageButton)findViewById(R.id.duyuru_buton);
        duyuru_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenDuyuru.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        derskontrol_buton = (ImageButton)findViewById(R.id.derskontrol_buton);
        derskontrol_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenDersKontrolDersler.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        dersler_buton = (ImageButton)findViewById(R.id.dersler_buton);
        dersler_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenDerslerDersListesi.class);
                startActivity(intent);
                OgretmenAnamenu.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        sohbet_buton = (ImageButton)findViewById(R.id.sohbet_buton);
        sohbet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenAnamenu.this, OgretmenSohbet.class);
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}