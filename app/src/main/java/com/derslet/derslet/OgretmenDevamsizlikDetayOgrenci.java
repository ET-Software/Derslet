package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgretmenDevamsizlikDetayOgrenci extends AppCompatActivity {

    ImageButton geri_buton;
    ListView ogretmen_devamsizlik_ogrenci_devamsizlik_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_devamsizlik_detay_ogrenci);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDevamsizlikDetayOgrenci.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        // Öğretmen duyuru mantığı Yapıldı yapılmadı durumuna göre clickleme
        ogretmen_devamsizlik_ogrenci_devamsizlik_listesi = (ListView) findViewById(R.id.ogretmen_devamsizlik_ogrenci_devamsizlik_listesi);
        ArrayList<Devamsizlik> arrayList = new ArrayList<>();
        arrayList.add(new Devamsizlik("2021-06-22 / 21:34", true));
        arrayList.add(new Devamsizlik("2021-06-23 / 21:35", false));
        arrayList.add(new Devamsizlik("2021-06-24 / 21:36", true));
        arrayList.add(new Devamsizlik("2021-06-25 / 21:37", false));
        arrayList.add(new Devamsizlik("2021-06-26 / 21:38", true));

        DevamsizlikOgretmenAdapter devamsizlikOgretmenAdapter = new DevamsizlikOgretmenAdapter(this, R.layout.list_ogrenci_devamsizlik, arrayList);
        ogretmen_devamsizlik_ogrenci_devamsizlik_listesi.setAdapter(devamsizlikOgretmenAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}