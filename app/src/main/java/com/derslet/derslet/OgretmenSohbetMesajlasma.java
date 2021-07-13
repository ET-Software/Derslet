package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgretmenSohbetMesajlasma extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton gonder_buton;
    EditText mesaj_alani;
    ListView mesaj_listesi;
    SohbetAdapter mesaj_listesi_adapter;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String sohbet_id;
    String ogrenci_isim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_sohbet_mesajlasma);

        sohbet_id = getIntent().getStringExtra("SOHBET_ID");
        ogrenci_isim = getIntent().getStringExtra("OGRENCI_ISIM");

        mesaj_listesi = (ListView) findViewById(R.id.kisi_listesi);
        mesaj_alani = (EditText) findViewById(R.id.mesaj_alani);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenSohbetMesajlasma.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
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
                        String sql = "INSERT INTO mesajlar(sohbetid, mesaj, gonderen) VALUES('"+sohbet_id+"','"+mesaj+"','true')";
                        stmt.executeUpdate(sql);
                        mesajlariYenile(true);
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
                mesajlar.add(new Sohbet(1, rs.getBoolean("gonderen")? "Siz" : ogrenci_isim,rs.getString("mesaj")));
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
}