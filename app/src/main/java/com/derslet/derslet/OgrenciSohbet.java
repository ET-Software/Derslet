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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OgrenciSohbet extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton sohbet_ac_buton;
    ListView sohbet_listesi;
    ListView ogretmen_listesi;
    BottomSheetDialog ogretmen_altsecim;
    View sheetView;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_sohbet);

        sohbet_listesi = (ListView) findViewById(R.id.sohbet_listesi);

        ogretmen_altsecim = new BottomSheetDialog(OgrenciSohbet.this, R.style.AltSecim_Tema);
        sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sohbet_alt_secim, (ViewGroup) findViewById(R.id.sohbet_alt_secim) );

        TextView alt_secim_baslik = (TextView)sheetView.findViewById(R.id.alt_secim_baslik);
        alt_secim_baslik.setText("Sohbet başlatmak istediğiniz öğretmeni seçiniz!");

        ogretmen_listesi = sheetView.findViewById(R.id.kisi_listesi);


        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        sohbet_ac_buton = (ImageButton)findViewById(R.id.sohbet_ac_buton);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> sohbetidleri = new ArrayList<String>(); // Veri tabanından çekilecek sohbet idleri
        ArrayList<String> ogretmenidleri = new ArrayList<String>(); // Veri tabanından çekilecek ogretmen idleri
        ArrayList<Sohbet> sohbet = new ArrayList<>();

        //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        String token = yerel_veriler.getString("Token","");

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            //Öğrencinin açtığı sohbetlerin id sorgusu
            String sql = "SELECT * FROM sohbet WHERE ogrenciid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                sohbetidleri.add(rs.getString("id"));
            }

            // Öğrencinin açtığı sohbetlerin sorgusu
            for(String sohbetid : sohbetidleri){
                sql = "SELECT * FROM sohbet WHERE id = '" + sohbetid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    String durum = "";
                    if (rs.getBoolean("durumogretmen")){
                        durum = "Okundu";
                    }
                    else{
                        durum = "Okunmadı";
                    }
                    sohbet.add(new Sohbet("", durum));
                    ogretmenidleri.add(rs.getString("ogretmenid"));
                }
            }

            // Öğrencinin açtığı sohbetlerin öğretmen sorgusu
            int sayac = 0;
            for(String ogretmenid : ogretmenidleri){
                sql = "SELECT * FROM kullanicilar WHERE id = '" + ogretmenid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    sohbet.get(sayac).setAd_soyad(rs.getString("isim") + " " + rs.getString("soyisim"));
                }
                sayac++;
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //Sohbetlerin listelenmesi
        sohbet_listesi.setAdapter(new SohbetAdapter(this, R.layout.list_sohbet, sohbet));

        //Sohbet listesinin tıklama işlemleri
        sohbet_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent=new Intent(OgrenciSohbet.this, OgrenciSohbetMesajlasma.class);
                intent.putExtra("SOHBET_ID", sohbetidleri.get(pos));
                intent.putExtra("OGRETMEN_ISIM", sohbet.get(pos).getAd_soyad());
                intent.putExtra("OGRETMEN_ID",ogretmenidleri.get(pos));
                startActivity(intent);
                OgrenciSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ArrayList<Sohbet> ogretmenler = new ArrayList<Sohbet>();
        ArrayList<String> dersidler = new ArrayList<String>();
        ArrayList<String> ogretmenidler = new ArrayList<String>();

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM ogrencidersler WHERE ogrenciid = '" + token + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                dersidler.add(rs.getString("dersid"));
            }
            for(String dersid: dersidler){
                sql = "SELECT * FROM dersler WHERE id = '" + dersid + "' ";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    String aratilanId = rs.getString("ogretmenid");
                    boolean varmi = false;
                    for(String ogretmenid : ogretmenidler){
                        if(aratilanId.equals(ogretmenid))
                            varmi = true;
                    }
                    if(!varmi)
                        ogretmenidler.add(aratilanId);
                }
            }

            for(String ogretmenid : new ArrayList<String>(ogretmenidler)){
                sql = "SELECT * FROM sohbet WHERE ogretmenid = '" + ogretmenid + "' AND ogrenciid = '" + token + "'";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    ogretmenidler.remove(ogretmenidler.indexOf(ogretmenid));
                }
            }

            for(String ogretmenid : ogretmenidler){
                sql = "SELECT * FROM kullanicilar WHERE id = '" + ogretmenid + "'";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    ogretmenler.add(new Sohbet(rs.getString("isim")+ " " + rs.getString("soyisim")));
                }
            }

            ogretmen_listesi.setAdapter(new SohbetAdapter(ogretmen_altsecim.getContext(), R.layout.list_sohbet_alt_secim, ogretmenler));
            ogretmen_listesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    String sohbet_id = "";
                    try{
                        String sql = "INSERT INTO sohbet(ogrenciid, ogretmenid) VALUES('"+ token +"','"+ogretmenidler.get(pos)+"') RETURNING id";
                        ResultSet rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            sohbet_id = rs.getString("id");
                        }
                    }catch (Exception e){
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }

                    Intent intent=new Intent(OgrenciSohbet.this, OgrenciSohbetMesajlasma.class);
                    intent.putExtra("SOHBET_ID", sohbet_id);
                    intent.putExtra("OGRENCI_ISIM", ogretmenler.get(pos).getOgretmen());
                    startActivity(intent);
                    OgrenciSohbet.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                    ogretmen_altsecim.dismiss();
                }
            });
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        if(ogretmenidler.isEmpty()){
            sohbet_ac_buton.setVisibility(View.INVISIBLE);
        }
        sohbet_ac_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ogretmen_altsecim.setContentView(sheetView);
                ogretmen_altsecim.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}