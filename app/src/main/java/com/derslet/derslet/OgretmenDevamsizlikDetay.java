package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OgretmenDevamsizlikDetay extends AppCompatActivity {

    ImageButton geri_buton;
    Button ogrenci0_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_devamsizlik_detay);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDevamsizlikDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ogrenci0_buton = (Button)findViewById(R.id.ogrenci0_buton);
        ogrenci0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDevamsizlikDetay.this, OgretmenDevamsizlikDetayOgrenci.class);
                startActivity(intent);
                OgretmenDevamsizlikDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}