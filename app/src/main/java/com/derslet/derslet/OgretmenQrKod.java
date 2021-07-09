package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class OgretmenQrKod extends AppCompatActivity {

    ImageButton geri_buton;
    String ders_kontrol_id;
    ImageView qrkod_gorsel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_qr_kod);

        ders_kontrol_id = getIntent().getStringExtra("DERS_KONTROL_ID");
        qrkod_gorsel = (ImageView)findViewById(R.id.qrkod); // Qrkod yansılatılack imageview

        qrkod_gorsel.setImageBitmap(QRKodGorseliOlustur(ders_kontrol_id, 1000)); // Imageview qrkod görseli ekleme

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenQrKod.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    Bitmap QRKodGorseliOlustur(String qrkod_veri, int boyut){

        QRGEncoder qrgEncoder = new QRGEncoder(qrkod_veri, null, QRGContents.Type.TEXT, boyut); //qrkod oluşturucu
        qrgEncoder.setColorBlack(Color.BLACK); // ana renk
        qrgEncoder.setColorWhite(Color.TRANSPARENT); // arkaplan rengi
        Bitmap qrkod_bitmap = qrgEncoder.getBitmap(); // bitmap çevirici

        return  qrkod_bitmap;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
