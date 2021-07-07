package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class OgretmenDerslerDegerlendirme extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton1;
    ImageButton buton3;
    TextView baslik;
    TextView degenlendirme_ortalamasi;
    ListView degerlendirme_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_degerlendirme);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        degenlendirme_ortalamasi = (TextView) findViewById(R.id.degerlendirme_ortalamasi);
        degerlendirme_listesi = (ListView) findViewById(R.id.ogretmen_degerlendirme_listesi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDerslerGonderi.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDerslerDevamsizlik.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Degerlendirme> degerlendirmeler = new ArrayList<Degerlendirme>();
        ArrayList<String> derskontrol_idler = new ArrayList<String>();
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM derskontrol WHERE dersid = '"+ders_id+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                degerlendirmeler.add(new Degerlendirme(rs.getString("tarih"),0.0f));
                derskontrol_idler.add(rs.getString("id"));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            float ortalama = 0;
            for(int i=0; i<derskontrol_idler.size();i++){
                String sql = "SELECT * FROM degerlendirmeistatistik WHERE id = '"+derskontrol_idler.get(i)+"'";
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()){
                    degerlendirmeler.get(i).setOrtalama(rs.getFloat("genelortalama"));
                    if(i==0){
                        ortalama = rs.getFloat("genelortalama");
                    }else{
                        ortalama = ((ortalama * i) + rs.getFloat("genelortalama"))/(i+1);
                    }
                }
            }
            degenlendirme_ortalamasi.setText(Float.toString(ortalama));
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        Collections.reverse(degerlendirmeler);
        Collections.reverse(derskontrol_idler);
        degerlendirme_listesi.setAdapter(new DegerlendirmeOgretmenAdapter(this, R.layout.list_ogretmen_degerlendirme, degerlendirmeler));

        degerlendirme_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDegerlendirmeDetay.class);
                intent.putExtra("DERS_KONTROL_ID", derskontrol_idler.get(pos));
                intent.putExtra("DERS_TARIH", degerlendirmeler.get(pos).tarih_saat);
                startActivity(intent);
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}