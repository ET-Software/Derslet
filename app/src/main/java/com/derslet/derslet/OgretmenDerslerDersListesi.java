package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgretmenDerslerDersListesi extends AppCompatActivity {

    ImageButton geri_buton;
    Button ders0_isim;

    ListView ogretmen_ders_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_dersler_ders_listesi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*ders0_isim = (Button)findViewById(R.id.ders0_isim);
        ders0_isim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDerslerDersListesi.this, OgretmenDerslerGonderi.class);
                startActivity(intent);
                OgretmenDerslerDersListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

        ogretmen_ders_listesi = (ListView) findViewById(R.id.ogretmen_ders_listesi);

        ArrayList<DersListesiOgretmen> arrayList = new ArrayList<>();
        arrayList.add(new DersListesiOgretmen("Deneme Ders1", "10"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders2", "120"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders1", "10"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders2", "120"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders1", "10"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders2", "120"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders1", "10"));
        arrayList.add(new DersListesiOgretmen("Deneme Ders2", "120"));


        DersListesiOgretmenAdapter dersListesiOgretmenAdapter = new DersListesiOgretmenAdapter(this, R.layout.list_ogretmen_dersler, arrayList);
        ogretmen_ders_listesi.setAdapter(dersListesiOgretmenAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}