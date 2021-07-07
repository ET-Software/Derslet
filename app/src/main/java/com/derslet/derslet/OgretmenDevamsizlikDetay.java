package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenDevamsizlikDetay extends AppCompatActivity {

    ImageButton geri_buton;
    TextView baslik;
    ListView devamsizlik_ogrenci_listesi;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_adi;
    String ders_kontrol_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_devamsizlik_detay);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");
        ders_kontrol_id = getIntent().getStringExtra("DERS_KONTROL_ID");

        devamsizlik_ogrenci_listesi = (ListView) findViewById(R.id.ogretmen_devamsizlik_ogrenci_listesi);
        baslik = (TextView) findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDevamsizlikDetay.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        /*ogrenci0_buton = (Button)findViewById(R.id.ogrenci0_buton);
        ogrenci0_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Devamsizlik> devamsizliklar = new ArrayList<Devamsizlik>();
        ArrayList<String> ogrenci_idler = new ArrayList<String>();
        ArrayList<String> ogrenci_adlar = new ArrayList<String>();
        ArrayList<String> ogrenci_nolar = new ArrayList<String>();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM ogrencidersler WHERE dersid = '" + ders_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                ogrenci_idler.add(rs.getString("ogrenciid"));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            for(String ogrenci_id: ogrenci_idler){
                String sql = "SELECT * FROM kullanicilar WHERE id = '"+ogrenci_id+"'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    ogrenci_adlar.add(rs.getString("isim") + " " + rs.getString("soyisim"));
                    ogrenci_nolar.add(rs.getString("kullanicino"));
                }
            }


        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            for(int i=0; i<ogrenci_idler.size();i++){
                String sql = "SELECT * FROM devamsizlik WHERE derskontrolid = '" + ders_kontrol_id + "' AND ogrenciid = '"+ogrenci_idler.get(i)+"'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    devamsizliklar.add(new Devamsizlik(ogrenci_nolar.get(i),ogrenci_adlar.get(i),"Girdi"));
                }else{
                    devamsizliklar.add(new Devamsizlik(ogrenci_nolar.get(i),ogrenci_adlar.get(i),"Girmedi"));
                }
            }


        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        devamsizlik_ogrenci_listesi.setAdapter(new DevamsizlikOgretmenAdapter(this, R.layout.list_ogretmen_devamsizlik_ogrenci, devamsizliklar));

        devamsizlik_ogrenci_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent=new Intent(OgretmenDevamsizlikDetay.this, OgretmenDevamsizlikDetayOgrenci.class);
                intent.putExtra("OGRENCI_ID", ogrenci_idler.get(pos));
                intent.putExtra("OGRENCI_NO", ogrenci_nolar.get(pos));
                intent.putExtra("DERS_ID", ders_id);
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