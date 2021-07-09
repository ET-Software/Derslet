package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgretmenSohbet extends AppCompatActivity {

    ImageButton geri_buton;
    Button ogrenci0_buton;
    ImageButton sohbet_ac_buton;
    ListView ogretmen_sohbet_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_sohbet);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*ogrenci0_buton = (Button)findViewById(R.id.ogrenci0_buton);
        ogrenci0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenSohbetListesi.this, OgretmenSohbetMesajlasma.class);
                startActivity(intent);
                OgretmenSohbetListesi.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

        ogretmen_sohbet_listesi = (ListView) findViewById(R.id.ogretmen_sohbet_listesi);
        ArrayList<Sohbet> arrayList = new ArrayList<>();
        arrayList.add(new Sohbet("010", "Öğrenci 1", "Okunmadı"));
        arrayList.add(new Sohbet("011", "Öğrenci 2", "Okundu"));
        arrayList.add(new Sohbet("012", "Öğrenci 3", "Cevaplandı"));
        arrayList.add(new Sohbet("013", "Öğrenci 4", "Cevaplanmadı"));
        arrayList.add(new Sohbet("014", "Öğrenci 5", "Okundu"));

        SohbetAdapter sohbetAdapter = new SohbetAdapter(this, R.layout.list_sohbet_ogretmen, arrayList);
        ogretmen_sohbet_listesi.setAdapter(sohbetAdapter);

        sohbet_ac_buton = (ImageButton)findViewById(R.id.sohbet_ac_buton);
        sohbet_ac_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}