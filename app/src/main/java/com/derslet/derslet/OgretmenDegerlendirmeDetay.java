package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;

public class OgretmenDegerlendirmeDetay extends AppCompatActivity {

    ImageButton geri_buton;
    TextView baslik;
    TextView ders_ortalama_metni;
    TextView ogretmen_ortalama_metni;
    TextView genel_ortalama_metni;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String derskontrol_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_degerlendirme_detay);

        derskontrol_id =  getIntent().getStringExtra("DERS_KONTROL_ID");

        baslik = findViewById(R.id.baslik);
        baslik.setText(getIntent().getStringExtra("DERS_TARIH"));
        ders_ortalama_metni = (TextView) findViewById(R.id.degerlendirme_soru1_ortalama);
        ogretmen_ortalama_metni = (TextView) findViewById(R.id.degerlendirme_soru2_ortalama);
        genel_ortalama_metni = (TextView) findViewById(R.id.degerlendirme_genel_ortalama);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDegerlendirmeDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM degerlendirmeistatistik WHERE id = '"+derskontrol_id+"'";
            ResultSet rs = stmt.executeQuery(sql);

            float ders_ortalamasi = 0f;
            float ogretim_elemani_ortalamasi = 0f;
            float genel_ortalama = 0f;

            if(rs.next()){
                ders_ortalamasi = rs.getFloat("soru1ortalama");
                ogretim_elemani_ortalamasi = rs.getFloat("soru2ortalama");
                genel_ortalama = rs.getFloat("genelortalama");
            }

            ders_ortalama_metni.setText(Float.toString(ders_ortalamasi));
            ogretmen_ortalama_metni.setText(Float.toString(ogretim_elemani_ortalamasi));
            genel_ortalama_metni.setText(Float.toString(genel_ortalama));

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}