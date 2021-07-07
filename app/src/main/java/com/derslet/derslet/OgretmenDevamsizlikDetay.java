package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgretmenDevamsizlikDetay extends AppCompatActivity {

    ImageButton geri_buton;
    ListView ogretmen_devamsizlik_ogrenci_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_devamsizlik_detay);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDevamsizlikDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*ogrenci0_buton = (Button)findViewById(R.id.ogrenci0_buton);
        ogrenci0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDevamsizlikDetay.this, OgretmenDevamsizlikDetayOgrenci.class);
                startActivity(intent);
                OgretmenDevamsizlikDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

        // Öğretmen duyuru mantığı Yapıldı yapılmadı durumuna göre clickleme
        ogretmen_devamsizlik_ogrenci_listesi = (ListView) findViewById(R.id.ogretmen_devamsizlik_ogrenci_listesi);
        ArrayList<Devamsizlik> arrayList = new ArrayList<>();
        arrayList.add(new Devamsizlik("010", "Öğrenci 1", "Detay"));
        arrayList.add(new Devamsizlik("011", "Öğrenci 2", "Detay"));
        arrayList.add(new Devamsizlik("012", "Öğrenci 3", "Detay"));
        arrayList.add(new Devamsizlik("013", "Öğrenci 4", "Detay"));
        arrayList.add(new Devamsizlik("014", "Öğrenci 5", "Detay"));

        DevamsizlikOgretmenAdapter devamsizlikOgretmenAdapter = new DevamsizlikOgretmenAdapter(this, R.layout.list_ogretmen_devamsizlik_ogrenci, arrayList);
        ogretmen_devamsizlik_ogrenci_listesi.setAdapter(devamsizlikOgretmenAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}