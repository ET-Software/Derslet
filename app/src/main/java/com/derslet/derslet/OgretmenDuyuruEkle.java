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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgretmenDuyuruEkle extends AppCompatActivity {

    ImageButton geri_buton;
    Button gönder_buton;
    AutoCompleteTextView ders_secim;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_duyuru_ekle);

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
            //Öğretmenin verdiği derslerin id ve isim sorgusu
            String sql = "SELECT * FROM dersler WHERE ogretmenid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                dersidleri.add(rs.getString("id"));
                dersisimleri.add(rs.getString("dersadi"));
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
                OgretmenDuyuruEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gönder_buton = (Button)findViewById(R.id.gonder_buton);
        gönder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String duyuruDurumu = "Duyuru başarıyla eklendi!"; // databaseden gelicek yanıt göre belirlenecek toast mesajı

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                OgretmenDuyuruEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                timer.cancel();
                            }
                        });

                    }
                }, 1000); // 1sn bekliyor.


                //Toast menü
                Toast toast = Toast.makeText(getApplicationContext(), duyuruDurumu, Toast.LENGTH_SHORT);
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