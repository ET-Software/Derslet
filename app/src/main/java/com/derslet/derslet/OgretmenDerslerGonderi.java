package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenDerslerGonderi extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton gonderi_ekle_buton;
    ImageButton buton2;
    ImageButton buton3;
    TextView baslik;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;

    ListView ogretmen_gonderi_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_gonderi);

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
                OgretmenDerslerGonderi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gonderi_ekle_buton = (ImageButton)findViewById(R.id.gonderi_ekle_buton);
        gonderi_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerGonderi.this, OgretmenKisaSinavEkle.class);
                startActivity(intent);
                OgretmenDerslerGonderi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton2 = (ImageButton)findViewById(R.id.buton2);
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerGonderi.this, OgretmenDerslerDegerlendirme.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgretmenDerslerGonderi.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerGonderi.this, OgretmenDerslerDevamsizlik.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgretmenDerslerGonderi.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });

        ogretmen_gonderi_listesi = (ListView) findViewById(R.id.ogretmen_gonderi_listesi);
        ArrayList<Gonderi> arrayList = new ArrayList<>();
        arrayList.add(new Gonderi(0,"Gönderi1", "01-01-2021", "Normal Gönderi", null));
        arrayList.add(new Gonderi(1,"Gönderi2", "02-02-2021", "Bağlantı Gönderi", "https://www.google.com.tr"));
        arrayList.add(new Gonderi(2,"Gönderi3", "03-03-2021", "Kısa Sınav Gönderi", "ID Yazılacak."));

        GonderiAdapterOgretmen gonderiAdapterOgretmen = new GonderiAdapterOgretmen(this, R.layout.list_gonderi, arrayList);
        ogretmen_gonderi_listesi.setAdapter(gonderiAdapterOgretmen);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}