package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OgrenciSohbetMesajlasma extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton gonder_buton;
    EditText mesaj_alani;
    ListView mesaj_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_sohbet_mesajlasma);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciSohbetMesajlasma.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        mesaj_listesi = (ListView) findViewById(R.id.mesaj_listesi);
        ArrayList<Sohbet> arrayList = new ArrayList<>();
        arrayList.add(new Sohbet(1, "010", "Öğrenci"));
        arrayList.add(new Sohbet(2, "011", "Öğrenci"));
        arrayList.add(new Sohbet(3, "012", "Öğrenci"));
        arrayList.add(new Sohbet(4, "013", "Öğrenci"));
        arrayList.add(new Sohbet(5, "014", "Öğrenci"));

        SohbetAdapter sohbetAdapter = new SohbetAdapter(this, R.layout.list_sohbet_mesajlar, arrayList);
        mesaj_listesi.setAdapter(sohbetAdapter);

        gonder_buton = (ImageButton)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = mesaj_alani.getText().toString();
                if (!mesaj.equals("")){
                    //Yapılacak işlemler
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen mesaj yazınız.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
                mesaj_alani.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

}