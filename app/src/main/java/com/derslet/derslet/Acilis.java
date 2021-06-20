package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class Acilis extends AppCompatActivity {

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);
    }

    @Override
    protected void onStart(){
        super.onStart();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        if(token.isEmpty()){
            GirisEkraniAc();
        }else{
            TokenileGirisYap(token);
        }
    }

    void TokenileGirisYap(String _token){
        try {
            // Veritabanı Sorgu İşlemleri
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM kullanicilar WHERE id = '" + _token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()){
                // Kullanıcı Tip Kontrolü (True = Öğretmen, False = Öğrenci)
                Boolean tip = rs.getBoolean("tip");
                if (tip){
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(Acilis.this, OgretmenAnamenu.class));
                                    finish();
                                    Acilis.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                    timer.cancel();
                                }
                            });
                        }
                    }, 100);

                }
                else {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(Acilis.this, OgrenciAnamenu.class));
                                    finish();
                                    Acilis.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                    timer.cancel();
                                }
                            });
                        }
                    }, 100);
                }
            } else{
                GirisEkraniAc();
            }
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    void GirisEkraniAc(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), Giris.class));
                        finish();
                        Acilis.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        timer.cancel();
                    }
                });

            }
        }, 100);
    }


}