package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgretmenDegerlendirmeDetay extends AppCompatActivity {

    ImageButton geri_buton;
    ListView ogretmen_degerlendirme_yorum_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_degerlendirme_detay);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDegerlendirmeDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ogretmen_degerlendirme_yorum_listesi = (ListView) findViewById(R.id.ogretmen_degerlendirme_yorum_listesi);
        ArrayList<Degerlendirme> arrayList = new ArrayList<>();
        arrayList.add(new Degerlendirme((float) 4.10, "Avü pişmanlıktır"));
        arrayList.add(new Degerlendirme((float) 4.11, "Avü pişmanlıktır2"));
        arrayList.add(new Degerlendirme((float) 4.12, "Avü pişmanlıktır3"));
        arrayList.add(new Degerlendirme((float) 4.13, "Avü pişmanlıktır4"));
        arrayList.add(new Degerlendirme((float) 4.14, "Avü pişmanlıktır5"));

        DegerlendirmeOgrenciAdapter degerlendirmeOgrenciAdapter = new DegerlendirmeOgrenciAdapter(this, R.layout.list_ogretmen_degerlendirme_detay, arrayList);
        ogretmen_degerlendirme_yorum_listesi.setAdapter(degerlendirmeOgrenciAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}