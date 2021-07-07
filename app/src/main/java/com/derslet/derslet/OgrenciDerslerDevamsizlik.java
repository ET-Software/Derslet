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
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class OgrenciDerslerDevamsizlik extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton1;
    ImageButton buton2;
    TextView baslik;
    TextView devamsizlik_sayisi_metni;
    ListView devamsizlik_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;
    int devamsizlik_sayisi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_devamsizlik);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        devamsizlik_sayisi_metni = (TextView) findViewById(R.id.ogrenci_devamsizlik_sayisi);
        devamsizlik_listesi = (ListView) findViewById(R.id.ogrenci_devamsizlik_listesi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDevamsizlik.this, OgrenciDerslerGonderi.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgrenciDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDevamsizlik.this, OgrenciDerslerDegerlendirme.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgrenciDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Devamsizlik> devamsizliklar = new ArrayList<Devamsizlik>();

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
                String sql = "SELECT * FROM devamsizlik WHERE derskontrolid = '" + yapilan_ders_idleri.get(i) + "' AND ogrenciid = '"+ id +"'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    devamsizliklar.add(new Devamsizlik(yapilan_ders_tarihleri.get(i),true));
                }else{
                    devamsizliklar.add(new Devamsizlik(yapilan_ders_tarihleri.get(i),false));
                    devamsizlik_sayisi++;
                }
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        devamsizlik_sayisi_metni.setText(Integer.toString(devamsizlik_sayisi));

        Collections.reverse(devamsizliklar);
        devamsizlik_listesi.setAdapter(new DevamsizlikOgrenciAdapter(this, R.layout.list_ogrenci_devamsizlik, devamsizliklar));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}