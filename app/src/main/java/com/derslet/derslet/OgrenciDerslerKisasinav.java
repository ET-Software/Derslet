package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgrenciDerslerKisasinav extends AppCompatActivity {

    ImageButton geri_buton;
    Button gonder_buton;
    ListView ogrenci_kisa_sinav_sorular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_kisasinav);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerKisasinav.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gonder_buton = (Button)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Veri Tabanı üzerinden işlem yapılacak

            }
        });

        ogrenci_kisa_sinav_sorular = (ListView) findViewById(R.id.ogrenci_kisa_sinav_sorular);
        ArrayList<KisaSinav> arrayList = new ArrayList<>();
        arrayList.add(new KisaSinav("Soru1", "Cevaplandı", "ID1"));
        arrayList.add(new KisaSinav("Soru2", "Cevaplanmadı", "ID2"));
        arrayList.add(new KisaSinav("Soru3", "Cevaplandı", "ID3"));

        KisaSinavAdapter kisaSinavAdapter = new KisaSinavAdapter(this, R.layout.list_ogrenci_kisa_sinav, arrayList);
        ogrenci_kisa_sinav_sorular.setAdapter(kisaSinavAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}