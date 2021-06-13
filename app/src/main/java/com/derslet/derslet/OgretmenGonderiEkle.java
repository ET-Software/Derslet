package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class OgretmenGonderiEkle extends AppCompatActivity {

    ImageButton geri_buton;
    Button gönder_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_gonderi_ekle);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                OgretmenGonderiEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gönder_buton = (Button)findViewById(R.id.gonder_buton);
        gönder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gonderimDurumu  = "Başarıyla gönderildi!"; //Database üzerinden gelen veriye göre değişecek

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), OgretmenDerslerGonderi.class));
                                OgretmenGonderiEkle.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}