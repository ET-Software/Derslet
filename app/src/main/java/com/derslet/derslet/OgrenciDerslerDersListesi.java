package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgrenciDerslerDersListesi extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_buton;

    ListView ogrenci_ders_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_ders_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*ders0_buton = (Button)findViewById(R.id.ders0_buton);
        ders0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDersListesi.this, OgrenciDerslerGonderi.class);
                startActivity(intent);
                OgrenciDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

        ogrenci_ders_listesi = (ListView) findViewById(R.id.ogrenci_ders_listesi);

        ArrayList<DersListesiOgrenci> arrayList = new ArrayList<>();
        arrayList.add(new DersListesiOgrenci("Deneme Ders1", "Deneme Öğretmen1", "10"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders2", "Deneme Öğretmen2", "120"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders1", "Deneme Öğretmen1", "10"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders2", "Deneme Öğretmen2", "120"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders1", "Deneme Öğretmen1", "10"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders2", "Deneme Öğretmen2", "120"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders1", "Deneme Öğretmen1", "10"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders2", "Deneme Öğretmen2", "120"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders1", "Deneme Öğretmen1", "10"));
        arrayList.add(new DersListesiOgrenci("Deneme Ders2", "Deneme Öğretmen2", "120"));


        DersListesiOgrenciAdapter dersListesiOgrenciAdapter = new DersListesiOgrenciAdapter(this, R.layout.list_ogrenci_dersler, arrayList);
        ogrenci_ders_listesi.setAdapter(dersListesiOgrenciAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}