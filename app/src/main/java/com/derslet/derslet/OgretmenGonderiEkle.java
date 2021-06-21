package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class OgretmenGonderiEkle extends AppCompatActivity {

    ImageButton geri_buton;
    Button gönder_buton;
    TextInputEditText gonderi_baslik_metni;
    TextInputEditText gonderi_icerik_metni;
    TextInputEditText gonderi_kaynak_metni;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_gonderi_ekle);

        ders_id = getIntent().getStringExtra("DERS_ID");

        gonderi_baslik_metni = (TextInputEditText) findViewById(R.id.gonderi_baslik_metin);
        gonderi_icerik_metni = (TextInputEditText) findViewById(R.id.gonderi_icerik_metin);
        gonderi_kaynak_metni = (TextInputEditText) findViewById(R.id.gonderi_kaynak_metin);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                OgretmenGonderiEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gönder_buton = (Button)findViewById(R.id.gonder_buton);
        gönder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gonderimDurumu; //Database üzerinden gelen veriye göre değişecek

                String baslik = gonderi_baslik_metni.getText().toString();
                String icerik = gonderi_icerik_metni.getText().toString();
                String kaynak = gonderi_kaynak_metni.getText().toString();

                // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                if(baslik.isEmpty() || icerik.isEmpty()){
                    gonderimDurumu  = "Başlık veya İçerik Boş!";
                }else if(kaynak.isEmpty()){
                    gonderimDurumu = "Gönderi gönderilerken hata meydana geldi!";
                    try {
                        stmt = (veritabani.getExtraConnection()).createStatement();
                        String sql = "INSERT INTO derslergonderi(dersid, tip, baslik, icerik) VALUES ('" + ders_id + "','0','"+ baslik +"','"+ icerik+"')";
                        stmt.executeUpdate(sql);
                        gonderimDurumu  = "Gönderi Başarıyla Gönderildi!";

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                        OgretmenGonderiEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                        timer.cancel();
                                    }
                                });

                            }
                        }, 1000); // 1sn bekliyor.

                    } catch (Exception e){
                        System.err.println("Gönderim hatası: " + e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                }else{
                    if(URLUtil.isHttpUrl(kaynak) || URLUtil.isHttpsUrl(kaynak)){
                        gonderimDurumu = "Kaynak gönderilerken hata meydana geldi!";
                        try {
                            stmt = (veritabani.getExtraConnection()).createStatement();
                            String sql = "INSERT INTO derslergonderi(dersid, tip, baslik, icerik, baglanti) VALUES ('" + ders_id + "','1','"+ baslik +"','"+ icerik+"','"+kaynak+"')";
                            stmt.executeUpdate(sql);
                            gonderimDurumu  = "Kaynak Başarıyla Gönderildi";

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                            OgretmenGonderiEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                            timer.cancel();
                                        }
                                    });

                                }
                            }, 1000); // 1sn bekliyor.

                        } catch (Exception e){
                            System.err.println("Gönderim hatası: " + e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }else{
                        gonderimDurumu = "Geçerli bir kaynak giriniz!";
                    }
                }

                Toast toast = Toast.makeText(getApplicationContext(), gonderimDurumu, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}