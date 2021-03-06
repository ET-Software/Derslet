package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgrenciDerslerDegerlendirme extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton buton1;
    ImageButton buton3;
    TextView baslik;
    TextView degenlendirme_ortalamasi;
    ListView degerlendirme_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_degerlendirme);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        degenlendirme_ortalamasi = (TextView) findViewById(R.id.degerlendirme_ortalamasi);
        degerlendirme_listesi = (ListView) findViewById(R.id.ogrenci_degerlendirme_listesi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        buton1 = (ImageButton)findViewById(R.id.buton1);
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDegerlendirme.this, OgrenciDerslerGonderi.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgrenciDerslerDegerlendirme.this.overridePendingTransition(R.anim.slidein_rl,R.anim.slideout_rl);
            }
        });

        buton3 = (ImageButton)findViewById(R.id.buton3);
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgrenciDerslerDegerlendirme.this, OgrenciDerslerDevamsizlik.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                startActivity(intent);
                finish();
                OgrenciDerslerDegerlendirme.this.overridePendingTransition(R.anim.slidein_lr,R.anim.slideout_lr);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Degerlendirme> degerlendirmeler = new ArrayList<Degerlendirme>();
        ArrayList<String> dk_idler = new ArrayList<String>();

        // Veritaban?? Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritaban?? Sorgu ????lemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM derskontrol WHERE dersid = '" + ders_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                degerlendirmeler.add(new Degerlendirme(rs.getString("tarih"),false));
                dk_idler.add(rs.getString("id"));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String id = yerel_veriler.getString("Token","");

        try {
            for(int i = 0; i < dk_idler.size(); i++){
                //????retmenin verdi??i derslerin sorgusu
                String sql = "SELECT * FROM degerlendirme WHERE derskontrolid = '" + dk_idler.get(i) + "' AND ogrenciid = '"+ id + "'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    degerlendirmeler.get(i).setBilgi(true);
                }
            }
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            float ortalama = 0;
            for(int i = 0; i < dk_idler.size(); i++){
                String sql = "SELECT * FROM degerlendirme WHERE ogrenciid = '"+ id + "' AND derskontrolid = '"+dk_idler.get(i)+"'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    if(i == 0){
                        ortalama = rs.getFloat("ortalama");
                    }else{
                        ortalama = ((ortalama * i) + rs.getFloat("ortalama"))/(i+1);
                    }
                }

            }
            degenlendirme_ortalamasi.setText(Float.toString(ortalama));
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        degerlendirme_listesi.setAdapter(new DegerlendirmeOgrenciAdapter(this, R.layout.list_ogrenci_degerlendirme, degerlendirmeler));

        //Ders listesinin t??klama i??lemleri
        degerlendirme_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if(!degerlendirmeler.get(pos).getBilgi()){
                    Intent intent=new Intent(OgrenciDerslerDegerlendirme.this, OgrenciDerslerDegerlendirmeYap.class);
                    intent.putExtra("DERS_KONTROL_ID", dk_idler.get(pos));
                    intent.putExtra("DERS_ADI", ders_adi);
                    startActivity(intent);
                    OgrenciDerslerDegerlendirme.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                }else{
                    //Toast men??
                    Toast toast = Toast.makeText(getApplicationContext(), "Bu de??erlendirmeyi ??nceden yapt??n??z!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}