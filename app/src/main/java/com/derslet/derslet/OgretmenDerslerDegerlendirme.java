package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenDerslerDegerlendirme extends AppCompatActivity {

    ImageButton geri_buton;
    Button degerlendirme0_buton;
    ImageButton buton1;
    ImageButton buton3;
    TextView baslik;
    ListView ogretmen_degerlendirme_listesi;

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

        /*degerlendirme0_buton = (Button)findViewById(R.id.degerlendirme0_buton);
        degerlendirme0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDegerlendirme.this, OgretmenDegerlendirmeDetay.class);
                startActivity(intent);
                OgretmenDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

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


        // OgretmenDuyuru mantığı
        ogretmen_degerlendirme_listesi = (ListView) findViewById(R.id.ogretmen_degerlendirme_listesi);
        ArrayList<Degerlendirme> arrayList = new ArrayList<>();
        arrayList.add(new Degerlendirme("2021-06-22 / 21:34", (float) 4.43));
        arrayList.add(new Degerlendirme("2021-06-23 / 21:35", (float) 4.44));
        arrayList.add(new Degerlendirme("2021-06-24 / 21:36", (float) 4.45));
        arrayList.add(new Degerlendirme("2021-06-25 / 21:37", (float) 4.46));
        arrayList.add(new Degerlendirme("2021-06-26 / 21:38", (float) 4.47));

        DegerlendirmeOgretmenAdapter degerlendirmeOgretmenAdapter = new DegerlendirmeOgretmenAdapter(this, R.layout.list_ogretmen_degerlendirme, arrayList);
        ogretmen_degerlendirme_listesi.setAdapter(degerlendirmeOgretmenAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}