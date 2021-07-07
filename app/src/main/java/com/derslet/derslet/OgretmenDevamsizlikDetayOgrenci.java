package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenDevamsizlikDetayOgrenci extends AppCompatActivity {

    ImageButton geri_buton;
    TextView baslik;
    TextView devamsizlik_sayisi_metni;
    ListView devamsizlik_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ogrenci_id;
    String ogrenci_no;
    String ders_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_devamsizlik_detay_ogrenci);

        ogrenci_id = getIntent().getStringExtra("OGRENCI_ID");
        ogrenci_no = getIntent().getStringExtra("OGRENCI_NO");
        ders_id = getIntent().getStringExtra("DERS_ID");

        devamsizlik_listesi = (ListView) findViewById(R.id.ogretmen_devamsizlik_ogrenci_devamsizlik_listesi);
        devamsizlik_sayisi_metni = (TextView) findViewById(R.id.ogrenci_devamsizlik_sayisi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ogrenci_no);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDevamsizlikDetayOgrenci.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Devamsizlik> devamsizliklar = new ArrayList<Devamsizlik>();
        int devamsizlik_sayisi = 0;

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<String> yapilan_ders_idleri = new ArrayList<String>();
        ArrayList<String> yapilan_ders_tarihleri = new ArrayList<String>();

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM derskontrol WHERE dersid = '" + ders_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                yapilan_ders_idleri.add(rs.getString("id"));
                yapilan_ders_tarihleri.add(rs.getString("tarih"));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        try {
            //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
            SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
            String id = yerel_veriler.getString("Token","");

            for(int i=0;i<yapilan_ders_idleri.size();i++){
                String sql = "SELECT * FROM devamsizlik WHERE derskontrolid = '" + yapilan_ders_idleri.get(i) + "' AND ogrenciid = '"+ ogrenci_id +"'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    devamsizliklar.add(new Devamsizlik(yapilan_ders_tarihleri.get(i),"Girdi"));
                }else{
                    devamsizliklar.add(new Devamsizlik(yapilan_ders_tarihleri.get(i),"Girmedi"));
                    devamsizlik_sayisi++;
                }
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        devamsizlik_sayisi_metni.setText(Integer.toString(devamsizlik_sayisi));
        devamsizlik_listesi.setAdapter(new DevamsizlikOgretmenAdapter(this, R.layout.list_ogrenci_devamsizlik, devamsizliklar));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}