package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgretmenKisaSinavEkle extends AppCompatActivity {

    ImageButton geri_buton;
    Button soru0_isim;
    Button gönder_buton;
    Button soru_ekle_buton;
    ListView kisa_sinav_listesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_kisa_sinav_ekle);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenKisaSinavEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*soru0_isim = (Button)findViewById(R.id.soru0_isim);
        soru0_isim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenKisaSinavEkle.this, OgretmenSoruEkleDuzenle.class);
                startActivity(intent);
                OgretmenKisaSinavEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });*/

        gönder_buton = (Button)findViewById(R.id.gonder_buton);
        gönder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gonderimDurumu = "Başarıyla Gönderildi!"; //Database üzerinden gelen veriye göre değişecek

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), OgretmenDerslerGonderi.class));
                                OgretmenKisaSinavEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);;
                                timer.cancel();
                            }
                        });

                    }
                }, 1000);

                Toast toast = Toast.makeText(getApplicationContext(), gonderimDurumu, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        soru_ekle_buton = (Button)findViewById(R.id.soru_ekle_buton);
        soru_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yapılacak İşlemler
            }
        });

        kisa_sinav_listesi = (ListView) findViewById(R.id.ogretmen_kisa_sinav_listesi);
        ArrayList<KisaSinavEkle> arrayList = new ArrayList<>();
        arrayList.add(new KisaSinavEkle("Soru1", "ID1"));
        arrayList.add(new KisaSinavEkle("Soru2", "ID2"));
        arrayList.add(new KisaSinavEkle("Soru3", "ID3"));
        arrayList.add(new KisaSinavEkle("Soru4", "ID4"));
        arrayList.add(new KisaSinavEkle("Soru5", "ID5"));

        KisaSinavEkleAdapter kisaSinavEkleAdapter = new KisaSinavEkleAdapter(this, R.layout.list_ogretmen_kisa_sinav_ekle, arrayList);
        kisa_sinav_listesi.setAdapter(kisaSinavEkleAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}