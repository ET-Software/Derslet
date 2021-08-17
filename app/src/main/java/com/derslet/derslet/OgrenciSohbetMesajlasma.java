package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgrenciSohbetMesajlasma extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton gonder_buton;
    EditText mesaj_alani;
    ListView mesaj_listesi;
    SohbetAdapter mesaj_listesi_adapter;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String sohbet_id;
    String ogretmen_id;
    String ogrenci_id;
    String ogretmen_isim;

    Bitmap ogrenci_profil;
    Bitmap ogretmen_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_sohbet_mesajlasma);

        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);

        sohbet_id = getIntent().getStringExtra("SOHBET_ID");
        ogrenci_id = yerel_veriler.getString("Token","");
        ogretmen_id = getIntent().getStringExtra("OGRETMEN_ID");
        ogretmen_isim = getIntent().getStringExtra("OGRETMEN_ISIM");

        ogrenci_profil = getProfilBitmap(false);
        ogretmen_profil = getProfilBitmap(true);

        mesaj_listesi = (ListView) findViewById(R.id.kisi_listesi);
        mesaj_alani = (EditText) findViewById(R.id.mesaj_alani);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciSohbetMesajlasma.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gonder_buton = (ImageButton)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = mesaj_alani.getText().toString();
                if (!mesaj.equals("")){
                    // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    // Veritabanı Sorgu İşlemleri
                    try {
                        stmt = (veritabani.getExtraConnection()).createStatement();
                        String sql = "INSERT INTO mesajlar(sohbetid, mesaj, gonderen) VALUES('"+sohbet_id+"','" + mesaj + "','false')";
                        stmt.executeUpdate(sql);
                        mesajlariYenile(true);
                    }catch (Exception e){
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }

                    // Veritabanı Sorgu İşlemleri
                    try {
                        String sql = "UPDATE sohbet SET durumogretmen = 'false' WHERE id = '"+sohbet_id+"'";
                        stmt.executeUpdate(sql);
                    }catch (Exception e){
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }

                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen mesaj yazınız.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
                mesaj_alani.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "UPDATE sohbet SET durumogrenci = 'true' WHERE id = '"+sohbet_id+"'";
            stmt.executeUpdate(sql);
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        mesajlariYenile(false);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        mesajlariYenile(true);
                    }
                });
            }
        }, 0, 5000); // 5 saniyede bir mesajları yeniliyor.

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Veritabanı Sorgu İşlemleri
        try {
            String sql = "UPDATE sohbet SET durumogrenci = 'true' WHERE id = '"+sohbet_id+"'";
            stmt.executeUpdate(sql);
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    void mesajlariYenile(boolean isUpdate){

        ArrayList<Sohbet> mesajlar = new ArrayList<>();


        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM mesajlar WHERE sohbetid = '" + sohbet_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String mesaj_bilgi;
                Bitmap mesaj_profil;
                if(rs.getBoolean("gonderen")){
                    mesaj_bilgi = ogretmen_isim;
                    mesaj_profil = ogretmen_profil;
                }else{
                    mesaj_bilgi = "Siz";
                    mesaj_profil = ogrenci_profil;
                }

                mesajlar.add(new Sohbet(mesaj_profil, mesaj_bilgi, rs.getString("mesaj")));
            }


        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        if(isUpdate){
            mesaj_listesi_adapter.getData().clear();
            mesaj_listesi_adapter.getData().addAll(mesajlar);
            mesaj_listesi_adapter.notifyDataSetChanged();
        }else{
            mesaj_listesi_adapter = new SohbetAdapter(this, R.layout.list_sohbet_mesajlar, mesajlar);
            mesaj_listesi.setAdapter(mesaj_listesi_adapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }


    public Bitmap getProfilBitmap(boolean ogretmenMi) {
        String id = ogretmenMi ? ogretmen_id: ogrenci_id;
        try{
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM kullanicilar WHERE id = '" + id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())   return getBitmapFromURL(rs.getString("profilurl"));
            else return null;

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

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