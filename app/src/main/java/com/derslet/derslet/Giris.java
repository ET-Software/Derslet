package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Giris extends AppCompatActivity {

    Button giris_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        //Butonlar
        giris_buton = (Button)findViewById(R.id.giris_buton);
        giris_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Giris.this, OgrenciAnamenu.class);
                startActivity(intent);
                Giris.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                Toast toast = Toast.makeText(getApplicationContext(), "Hoşgeldiniz", Toast.LENGTH_SHORT);
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