package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class OgretmenKisaSinavSonuclar extends AppCompatActivity {

    ImageButton geri_buton;
    TextView baslik;
    ListView kisa_sinav_sonuclar;

    String kisa_sinav_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_kisa_sinav_sonuclar);

        kisa_sinav_id = getIntent().getStringExtra("KISA_SINAV_ID");

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenKisaSinavSonuclar.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        kisa_sinav_sonuclar = (ListView) findViewById(R.id.ogretmen_kisa_sinav_sonuclar_listesi);
        ArrayList<KisaSinavSonuclar> arrayList = new ArrayList<>();
        arrayList.add(new KisaSinavSonuclar("010", "Öğrenci 0", "0 / 5"));
        arrayList.add(new KisaSinavSonuclar("011", "Öğrenci 1", "1 / 5"));
        arrayList.add(new KisaSinavSonuclar("012", "Öğrenci 2", "2 / 5"));
        arrayList.add(new KisaSinavSonuclar("013", "Öğrenci 3", "3 / 5"));

        KisaSinavSonuclarAdapter kisaSinavSonuclarAdapter = new KisaSinavSonuclarAdapter(this, R.layout.list_ogretmen_kisa_sinav_sonuclar, arrayList);
        kisa_sinav_sonuclar.setAdapter(kisaSinavSonuclarAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}