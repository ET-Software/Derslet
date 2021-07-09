package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgretmenDersKontrolDersler extends AppCompatActivity {

    ImageButton geri_buton;
    ListView ders_listesi;
    BottomSheetDialog onay_alt_secim;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_kontrol_id;
    String ders_adi;
    String ders_kisi_sayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_ders_kontrol_dersler);

        ders_listesi = (ListView) findViewById(R.id.ogretmen_ders_listesi);

        //Alt seçim menüsü
        onay_alt_secim = new BottomSheetDialog(OgretmenDersKontrolDersler.this, R.style.AltSecim_Tema);
        View viewSecim = LayoutInflater.from(getApplicationContext()).inflate(R.layout.onay_alt_secim,(ViewGroup) findViewById(R.id.onay_alt_secim));
        TextView onaybaslik = (TextView)viewSecim.findViewById(R.id.secim_baslik);
        onaybaslik.setText("Dersi başlatmak istiyor musunuz?");

        viewSecim.findViewById(R.id.onay_secim_evet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                // Veritabanı Sorgu İşlemleri
                try {
                    stmt = (veritabani.getExtraConnection()).createStatement();
                    //Öğretmenin verdiği derslerin sorgusu
                    String sql = "INSERT INTO derskontrol(dersid) VALUES ('" + ders_id+ "') RETURNING id";
                    ResultSet rs = stmt.executeQuery(sql);

                    if(rs.next()){
                        ders_kontrol_id = rs.getString("id");
                    }

                } catch (Exception e){
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                Intent intent=new Intent(OgretmenDersKontrolDersler.this, OgretmenDersKontrol.class);
                intent.putExtra("DERS_ID", ders_id);
                intent.putExtra("DERS_ADI", ders_adi);
                intent.putExtra("DERS_KONTROL_ID", ders_kontrol_id);
                intent.putExtra("DERS_KISI_SAYISI", ders_kisi_sayisi);
                startActivity(intent);
                OgretmenDersKontrolDersler.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                onay_alt_secim.dismiss();
            }
        });

        viewSecim.findViewById(R.id.onay_secim_hayir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onay_alt_secim.dismiss();
            }
        });
        onay_alt_secim.setContentView(viewSecim);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgretmenDersKontrolDersler.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> dersidleri = new ArrayList<String>(); // Veri tabanından çekilecek ders idleri
        ArrayList<DersListesiOgretmen> dersler = new ArrayList<DersListesiOgretmen>();

        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            //Öğretmenin verdiği derslerin sorgusu
            String sql = "SELECT * FROM dersler WHERE ogretmenid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                dersidleri.add(rs.getString("id"));
                dersler.add(new DersListesiOgretmen(rs.getString("dersadi"),rs.getString("kisisayisi")));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Derslerin listelenmesi
        ders_listesi.setAdapter(new DersListesiOgretmenAdapter(this, R.layout.list_ogretmen_dersler, dersler));

        //Ders listesinin tıklama işlemleri
        ders_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                ders_id = dersidleri.get(pos);
                ders_adi = dersler.get(pos).getDers_isim();
                ders_kisi_sayisi = dersler.get(pos).getDers_bilgi();
                onay_alt_secim.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}