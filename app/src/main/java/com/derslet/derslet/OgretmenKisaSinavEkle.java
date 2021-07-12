package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgretmenKisaSinavEkle extends AppCompatActivity {

    ImageButton geri_buton;
    Button gönder_buton;
    Button soru_ekle_buton;
    ListView kisa_sinav_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    Soru[] sorular;

    String ders_id;
    int soru_sayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_kisa_sinav_ekle);

        soru_sayisi = getIntent().getIntExtra("SORU_SAYISI",0);
        ders_id = getIntent().getStringExtra("DERS_ID");
        sorular = new Soru[soru_sayisi];

        ArrayList<KisaSinavEkle> kisasinav_sorular = new ArrayList<>();

        kisa_sinav_listesi = (ListView) findViewById(R.id.ogretmen_kisa_sinav_listesi);


        for(int i = 0; i<soru_sayisi;i++){
            String soru_icerik = getIntent().getStringExtra("SORU"+(i+1));
            String cevap1 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP1");
            String cevap2 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP2");
            String cevap3 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP3");
            String cevap4 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP4");
            String cevap5 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP5");
            sorular[i] = new Soru(soru_icerik,cevap1,cevap2,cevap3,cevap4,cevap5);
            kisasinav_sorular.add(new KisaSinavEkle("Soru "+(i+1)));
        }

        kisa_sinav_listesi.setAdapter(new KisaSinavEkleAdapter(this, R.layout.list_ogretmen_kisa_sinav_ekle, kisasinav_sorular, sorular, ders_id));

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenKisaSinavEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gönder_buton = (Button)findViewById(R.id.gonder_buton);
        gönder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gonderimDurumu = "Kısa Sınav gönderimi başarılı!";
                String kisa_sinav_id;
                try {
                    stmt = (veritabani.getExtraConnection()).createStatement();
                    String sql = "INSERT INTO derslergonderi(dersid, tip, baslik, icerik, sorusayisi) VALUES ('" + ders_id+ "','2','KISA SINAV','Toplam "+soru_sayisi+" adet soru bulunmaktadır.','"+soru_sayisi+"') RETURNING id";
                    ResultSet rs = stmt.executeQuery(sql);

                    if(rs.next()){
                        kisa_sinav_id = rs.getString("id");
                        for(Soru _soru: sorular){
                            sql = "INSERT INTO sorular(kisasinavid, soru, cevap1, cevap2, cevap3, cevap4, cevap5) VALUES ('" + kisa_sinav_id + "','"+ _soru.getSoru() +"','"+ _soru.getCevap1() + "','"+ _soru.getCevap2() + "','"+ _soru.getCevap3() + "','"+ _soru.getCevap4() + "','"+ _soru.getCevap5() +"')";
                            stmt.executeUpdate(sql);
                        }

                        gonderimDurumu = "Kısa Sınav gönderimi başarılı!";
                    }

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    OgretmenKisaSinavEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);;
                                    timer.cancel();
                                }
                            });

                        }
                    }, 1000); // 1sn bekliyor.

                } catch (Exception e){
                    System.err.println("Gönderim hatası: " + e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }


                Toast toast = Toast.makeText(getApplicationContext(), gonderimDurumu, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        soru_ekle_buton = (Button)findViewById(R.id.soru_ekle_buton);
        soru_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OgretmenSoruEkleDuzenle.class);
                intent.putExtra("SORU_SAYISI", sorular.length);
                intent.putExtra("DERS_ID",ders_id);
                for(int i = 0; i < sorular.length; i++){
                    String soru_kayıt_ismi = "SORU"+(i+1);
                    intent.putExtra(soru_kayıt_ismi,sorular[i].getSoru());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP1",sorular[i].getCevap1());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP2",sorular[i].getCevap2());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP3",sorular[i].getCevap3());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP4",sorular[i].getCevap4());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP5",sorular[i].getCevap5());
                }
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}