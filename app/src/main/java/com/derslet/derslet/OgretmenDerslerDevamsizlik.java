package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class OgretmenDerslerDevamsizlik extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton1;
    ImageButton buton2;
    TextView baslik;
    ListView devamsizlik_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_devamsizlik);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        devamsizlik_listesi = (ListView) findViewById(R.id.ogretmen_devamsizlik_listesi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDerslerGonderi.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDerslerDegerlendirme.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Devamsizlik> devamsizliklar = new ArrayList<Devamsizlik>();
        ArrayList<String> derskontrol_idler = new ArrayList<String>();
        int kisi_sayisi = 0;

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM dersler WHERE id = '" + ders_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                kisi_sayisi = rs.getInt("kisisayisi");
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        // Veritabanı İşlemleri
        try {
            String sql = "SELECT * FROM derskontrol WHERE dersid = '" + ders_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                derskontrol_idler.add(rs.getString("id"));
                devamsizliklar.add(new Devamsizlik(rs.getString("tarih"), (rs.getString("katilimcisayisi")+" / "+Integer.toString(kisi_sayisi))));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        Collections.reverse(devamsizliklar);
        Collections.reverse(derskontrol_idler);
        devamsizlik_listesi.setAdapter(new DevamsizlikOgretmenAdapter(this, R.layout.list_ogretmen_devamsizlik, devamsizliklar));

        devamsizlik_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDevamsizlikDetay.class);
                intent.putExtra("DERS_KONTROL_ID", derskontrol_idler.get(pos));
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}