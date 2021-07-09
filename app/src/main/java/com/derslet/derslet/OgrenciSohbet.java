package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OgrenciSohbet extends AppCompatActivity {

    ImageButton geri_buton;
    ListView sohbet_listesi;
    Button ogretmen0_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_sohbet);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*ogretmen0_buton = (Button)findViewById(R.id.ogretmen0_buton);
        ogretmen0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciSohbet.this, OgrenciSohbetMesajlasma.class);
                startActivity(intent);
                OgrenciSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

        sohbet_listesi = (ListView) findViewById(R.id.sohbet_listesi);
        ArrayList<Sohbet> arrayList = new ArrayList<>();
        arrayList.add(new Sohbet("010", "Okunmadı"));
        arrayList.add(new Sohbet("011", "Okundu"));
        arrayList.add(new Sohbet("012", "Cevaplandı"));
        arrayList.add(new Sohbet("013", "Cevaplanmadı"));
        arrayList.add(new Sohbet("014", "Okundu"));

        SohbetAdapter sohbetAdapter = new SohbetAdapter(this, R.layout.list_sohbet, arrayList);
        sohbet_listesi.setAdapter(sohbetAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}