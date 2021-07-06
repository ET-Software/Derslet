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

public class OgretmenDerslerDevamsizlik extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_devamsizlik;
    ImageButton buton1;
    ImageButton buton2;
    TextView baslik;
    ListView ogretmen_devamsizlik_listesi;

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

        /*ders0_devamsizlik = (Button)findViewById(R.id.ders0_devamsizlik);
        ders0_devamsizlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDevamsizlik.this, OgretmenDevamsizlikDetay.class);
                startActivity(intent);
                OgretmenDerslerDevamsizlik.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

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

        // Öğretmen duyuru mantığı Yapıldı yapılmadı durumuna göre clickleme
        ogretmen_devamsizlik_listesi = (ListView) findViewById(R.id.ogretmen_devamsizlik_listesi);
        ArrayList<Devamsizlik> arrayList = new ArrayList<>();
        arrayList.add(new Devamsizlik("2021-06-22 / 21:34", "100 / 100"));
        arrayList.add(new Devamsizlik("2021-06-23 / 21:35", "90 / 100"));
        arrayList.add(new Devamsizlik("2021-06-24 / 21:36", "80 / 100"));
        arrayList.add(new Devamsizlik("2021-06-25 / 21:37", "70 / 100"));
        arrayList.add(new Devamsizlik("2021-06-26 / 21:38", "60 / 100"));

        DevamsizlikOgretmenAdapter devamsizlikOgretmenAdapter = new DevamsizlikOgretmenAdapter(this, R.layout.list_ogretmen_devamsizlik, arrayList);
        ogretmen_devamsizlik_listesi.setAdapter(devamsizlikOgretmenAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}