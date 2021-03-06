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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.ResultSet;
import java.sql.Statement;

public class Giris extends AppCompatActivity {

    // Değişken Tanımlamaları
    Button giris_buton;
    TextInputEditText kullanicino_metin;
    TextInputEditText sifre_metin;
    Veritabani veritabani = new Veritabani();
    Statement stmt = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Tanımlamalar
        kullanicino_metin = (TextInputEditText) findViewById(R.id.kullanicino_metin);
        sifre_metin = (TextInputEditText) findViewById(R.id.sifre_metin);
        giris_buton = (Button) findViewById(R.id.giris_buton);

        // Buton Tıklaması İşlemleri
        giris_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Veritabanı Sorgu İşlemleri
                    stmt = (veritabani.getExtraConnection()).createStatement();
                    String sql = "SELECT * FROM kullanicilar WHERE kullanicino = '" + kullanicino_metin.getText().toString() + "' AND sifre = '" + sifre_metin.getText().toString() + "' ";
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()){
                        // Kullanıcı Tip Kontrolü (True = Öğretmen, False = Öğrenci)
                        Boolean tip = rs.getBoolean("tip");
                        // Kullanıcıya özel giriş anahtarı bir sonraki uygulama girişinde otomatik giriş yapması için
                        String token = rs.getString("id");

                        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri",Context.MODE_PRIVATE);
                        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
                        duzenleyeci.putString("Token", token); //Token yerel olarak kaydediliyor
                        duzenleyeci.commit(); //Kayıt işlemi gerçekleşiyor.

                        if (tip){
                            Intent intent=new Intent(Giris.this, OgretmenAnamenu.class);
                            startActivity(intent);
                            finish();
                            Giris.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        }
                        else {
                            Intent intent=new Intent(Giris.this, OgrenciAnamenu.class);
                            startActivity(intent);
                            finish();
                            Giris.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        }
                    }
                    else{
                        // Bilgilendirme Mesajı
                        Toast toast = Toast.makeText(getApplicationContext(), "Hatalı Giriş!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                        toast.show();
                    }
                }
                catch (Exception e){
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Geriye Dönüş Animasyonu
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}