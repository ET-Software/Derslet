package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Giris extends AppCompatActivity {

    Button giris_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        giris_buton = (Button)findViewById(R.id.giris_buton);
        giris_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(Giris.this, OgretmenAnamenu.class);
                startActivity(ıntent);
            }
        });
    }
}