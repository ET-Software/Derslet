package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenSohbet extends AppCompatActivity {

    ImageButton geri_buton;
    ListView sohbet_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_sohbet);

        sohbet_listesi = (ListView) findViewById(R.id.ogretmen_sohbet_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> sohbetidleri = new ArrayList<String>(); // Veri tabanından çekilecek sohbet idleri
        ArrayList<String> ogrenciidleri = new ArrayList<String>(); // Veri tabanından çekilecek ogrenci idleri
        ArrayList<Sohbet> sohbet = new ArrayList<>();

        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            //Öğretmenin açtığı sohbetlerin id sorgusu
            String sql = "SELECT * FROM sohbet WHERE ogretmenid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                sohbetidleri.add(rs.getString("id"));
            }

            // Öğretmenin açtığı sohbetlerin sorgusu
            for(String sohbetid : sohbetidleri){
                sql = "SELECT * FROM sohbet WHERE id = '" + sohbetid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    String durum = "";
                    if (rs.getBoolean("durumogrenci")){
                        durum = "Okundu";
                    }
                    else{
                        durum = "Okunmadı";
                    }
                    sohbet.add(new Sohbet("", durum));
                    ogrenciidleri.add(rs.getString("ogrenciid"));
                }
            }

            // Öğretmenin açtığı sohbetlerin öğretmen sorgusu
            int sayac = 0;
            for(String ogrenciid : ogrenciidleri){
                sql = "SELECT * FROM kullanicilar WHERE id = '" + ogrenciid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    sohbet.get(sayac).setNumara(rs.getString("kullanicino"));
                    sohbet.get(sayac).setAd_soyad(rs.getString("isim") + " " + rs.getString("soyisim"));
                }
                sayac++;
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Sohbetlerin listelenmesi
        sohbet_listesi.setAdapter(new SohbetAdapter(this, R.layout.list_sohbet_ogretmen, sohbet));

        //Sohbet listesinin tıklama işlemleri
        sohbet_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent=new Intent(OgretmenSohbet.this, OgretmenSohbetMesajlasma.class);
                intent.putExtra("SOHBET_ID", sohbetidleri.get(pos));
                intent.putExtra("OGRENCI_ISIM", sohbet.get(pos).getAd_soyad());
                intent.putExtra("OGRENCI_ID", ogrenciidleri.get(pos));
                startActivity(intent);
                OgretmenSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}