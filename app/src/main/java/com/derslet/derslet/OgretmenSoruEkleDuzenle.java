package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

public class OgretmenSoruEkleDuzenle extends AppCompatActivity {

    ImageButton geri_buton;
    Button kaydet_buton;
    TextView baslik;
    TextInputEditText soru_icerigi_metni;
    TextInputEditText cevap1_metni;
    TextInputEditText cevap2_metni;
    TextInputEditText cevap3_metni;
    TextInputEditText cevap4_metni;
    TextInputEditText cevap5_metni;

    Boolean ilk_soru_mu;
    Boolean duzenle_mi;

    String ders_id;
    int soru_sayisi;

    Soru[] sorular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_soru_ekle_duzenle);

        ilk_soru_mu = getIntent().getBooleanExtra("ILK_SORU_MU",false);
        duzenle_mi = getIntent().getBooleanExtra("DUZENLE_MI",false);
        ders_id = getIntent().getStringExtra("DERS_ID");
        if(!ilk_soru_mu){
            soru_sayisi = getIntent().getIntExtra("SORU_SAYISI",0);
            sorular = new Soru[soru_sayisi];

            for(int i = 0; i < soru_sayisi;i++){
                String soru_icerik = getIntent().getStringExtra("SORU"+(i+1));
                String cevap1 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP1");
                String cevap2 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP2");
                String cevap3 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP3");
                String cevap4 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP4");
                String cevap5 = getIntent().getStringExtra("SORU"+(i+1)+"_CEVAP5");
                sorular[i] = new Soru(soru_icerik,cevap1,cevap2,cevap3,cevap4,cevap5);
            }
        }

        baslik = (TextView) findViewById(R.id.baslik);
        soru_icerigi_metni = (TextInputEditText)findViewById(R.id.soru_icerigi_metin);
        cevap1_metni = (TextInputEditText)findViewById(R.id.cevap1_metin);
        cevap2_metni = (TextInputEditText)findViewById(R.id.cevap2_metin);
        cevap3_metni = (TextInputEditText)findViewById(R.id.cevap3_metin);
        cevap4_metni = (TextInputEditText)findViewById(R.id.cevap4_metin);
        cevap5_metni = (TextInputEditText)findViewById(R.id.cevap5_metin);

        if(duzenle_mi){
            int duzenlenecek_soru_sirasi;
            duzenlenecek_soru_sirasi = getIntent().getIntExtra("DUZENLENECEK_SORU", 0);
            baslik.setText("SORU DÜZENLE");
            soru_icerigi_metni.setText(sorular[duzenlenecek_soru_sirasi].getSoru());
            cevap1_metni.setText(sorular[duzenlenecek_soru_sirasi].getCevap1());
            cevap2_metni.setText(sorular[duzenlenecek_soru_sirasi].getCevap2());
            cevap3_metni.setText(sorular[duzenlenecek_soru_sirasi].getCevap3());
            cevap4_metni.setText(sorular[duzenlenecek_soru_sirasi].getCevap4());
            cevap5_metni.setText(sorular[duzenlenecek_soru_sirasi].getCevap5());
        }else{
            baslik.setText("SORU EKLE");
        }

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenSoruEkleDuzenle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        kaydet_buton = (Button)findViewById(R.id.kaydet_buton);
        kaydet_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eklemeDuzenlemeDurumu; //Database üzerinden gelen veriye göre değişecek

                String soru_icerigi = soru_icerigi_metni.getText().toString();
                String cevap1 = cevap1_metni.getText().toString();
                String cevap2 = cevap2_metni.getText().toString();
                String cevap3 = cevap3_metni.getText().toString();
                String cevap4 = cevap4_metni.getText().toString();
                String cevap5 = cevap5_metni.getText().toString();

                if(soru_icerigi.isEmpty() || cevap1.isEmpty() || cevap2.isEmpty() || cevap3.isEmpty() || cevap4.isEmpty() || cevap5.isEmpty()){
                    eklemeDuzenlemeDurumu = "Lürfen tüm alanları doldurunuz!";
                }else{
                    eklemeDuzenlemeDurumu = "İşlem başarılı!";

                    if(ilk_soru_mu){
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(), OgretmenKisaSinavEkle.class);
                                        intent.putExtra("SORU_SAYISI", 1);
                                        intent.putExtra("DERS_ID",ders_id);
                                        intent.putExtra("SORU1",soru_icerigi);
                                        intent.putExtra("SORU1_CEVAP1",cevap1);
                                        intent.putExtra("SORU1_CEVAP2",cevap2);
                                        intent.putExtra("SORU1_CEVAP3",cevap3);
                                        intent.putExtra("SORU1_CEVAP4",cevap4);
                                        intent.putExtra("SORU1_CEVAP5",cevap5);
                                        finish();
                                        startActivity(intent);
                                        OgretmenSoruEkleDuzenle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                        timer.cancel();
                                    }
                                });

                            }
                        }, 1000);
                    }else{
                        if(duzenle_mi){
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getApplicationContext(), OgretmenKisaSinavEkle.class);
                                            intent.putExtra("SORU_SAYISI", soru_sayisi);
                                            intent.putExtra("DERS_ID",ders_id);
                                            for(int i = 0; i < soru_sayisi; i++){
                                                String soru_kayıt_ismi = "SORU"+(i+1);
                                                intent.putExtra(soru_kayıt_ismi,sorular[i].getSoru());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP1",sorular[i].getCevap1());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP2",sorular[i].getCevap2());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP3",sorular[i].getCevap3());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP4",sorular[i].getCevap4());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP5",sorular[i].getCevap5());
                                            }
                                            finish();
                                            startActivity(intent);
                                            OgretmenSoruEkleDuzenle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                            timer.cancel();
                                        }
                                    });

                                }
                            }, 1000);
                        }else{
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getApplicationContext(), OgretmenKisaSinavEkle.class);
                                            intent.putExtra("SORU_SAYISI", soru_sayisi+1);
                                            intent.putExtra("DERS_ID",ders_id);
                                            for(int i = 0; i < soru_sayisi; i++){
                                                String soru_kayıt_ismi = "SORU"+(i+1);
                                                intent.putExtra(soru_kayıt_ismi,sorular[i].getSoru());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP1",sorular[i].getCevap1());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP2",sorular[i].getCevap2());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP3",sorular[i].getCevap3());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP4",sorular[i].getCevap4());
                                                intent.putExtra(soru_kayıt_ismi+"_CEVAP5",sorular[i].getCevap5());
                                            }

                                            String soru_kayıt_ismi = "SORU"+(soru_sayisi+1);
                                            intent.putExtra(soru_kayıt_ismi,soru_icerigi);
                                            intent.putExtra(soru_kayıt_ismi+"_CEVAP1",cevap1);
                                            intent.putExtra(soru_kayıt_ismi+"_CEVAP2",cevap2);
                                            intent.putExtra(soru_kayıt_ismi+"_CEVAP3",cevap3);
                                            intent.putExtra(soru_kayıt_ismi+"_CEVAP4",cevap4);
                                            intent.putExtra(soru_kayıt_ismi+"_CEVAP5",cevap5);

                                            finish();
                                            startActivity(intent);
                                            OgretmenSoruEkleDuzenle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                            timer.cancel();
                                        }
                                    });

                                }
                            }, 1000);
                        }
                    }

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    finish();
                                    OgretmenSoruEkleDuzenle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                    timer.cancel();
                                }
                            });

                        }
                    }, 1000);

                }


                Toast toast = Toast.makeText(getApplicationContext(), eklemeDuzenlemeDurumu, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}