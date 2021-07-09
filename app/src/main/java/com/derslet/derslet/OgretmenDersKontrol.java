package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.Destroyable;

public class OgretmenDersKontrol extends AppCompatActivity {

    ImageButton geri_buton;
    ImageButton ogrenci_ekle_buton;
    Button qrkod_ac_buton;
    Button ders_bitir_buton;
    TextView baslik;
    TextView ders_kisi_sayisi_metni;
    TextView mevcut_kisi_sayisi_metni;
    TextView gecen_sure_metni;
    EditText eklenecek_ogrenci_numarasi_metni;
    ListView ogrenci_listesi;
    BottomSheetDialog onay_alt_secim;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String ders_id;
    String ders_kontrol_id;
    String ders_adi;
    String ders_kisi_sayisi;
    int mevcut_kisi_sayisi = 0;
    Date baslangic_zamani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen_ders_kontrol);

        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_kontrol_id = getIntent().getStringExtra("DERS_KONTROL_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");
        ders_kisi_sayisi = getIntent().getStringExtra("DERS_KISI_SAYISI");

        baslangic_zamani =  Calendar.getInstance().getTime();

        ogrenci_listesi = (ListView) findViewById(R.id.ogrenci_listesi);
        baslik = findViewById(R.id.baslik);
        baslik.setText(ders_adi);
        ders_kisi_sayisi_metni = findViewById(R.id.ders_kisi_sayisi);
        ders_kisi_sayisi_metni.setText(" / "+ders_kisi_sayisi + " kişi");
        mevcut_kisi_sayisi_metni = findViewById(R.id.kisisayisi);
        mevcut_kisi_sayisi_metni.setText(Integer.toString(mevcut_kisi_sayisi));
        eklenecek_ogrenci_numarasi_metni = findViewById(R.id.eklenecek_ogrenci_metin);
        gecen_sure_metni = findViewById(R.id.gecensure);

        //Alt seçim menüsü
        onay_alt_secim = new BottomSheetDialog(OgretmenDersKontrol.this, R.style.AltSecim_Tema);
        View viewSecim = LayoutInflater.from(getApplicationContext()).inflate(R.layout.onay_alt_secim,(ViewGroup) findViewById(R.id.onay_alt_secim));
        TextView onaybaslik = (TextView)viewSecim.findViewById(R.id.secim_baslik);
        onaybaslik.setText("Dersi bitirmek istiyor musunuz?");

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
                    String sql = "UPDATE derskontrol SET katilimcisayisi = '" + mevcut_kisi_sayisi + "' WHERE id = '" + ders_kontrol_id + "'";
                    stmt.executeUpdate(sql);

                } catch (Exception e){
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                finish();
                OgretmenDersKontrol.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                Toast toast = Toast.makeText(getApplicationContext(), "Ders başarıyla sonlandırıldı!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        viewSecim.findViewById(R.id.onay_secim_hayir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onay_alt_secim.dismiss();
            }
        });
        onay_alt_secim.setContentView(viewSecim);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gecen_sure_metni.setText(getGecenSure());
            }
        }, 0, 1000);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onay_alt_secim.show();
            }
        });

        ogrenci_ekle_buton = (ImageButton)findViewById(R.id.ogrenci_ekle_buton);
        ogrenci_ekle_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ogrencino = eklenecek_ogrenci_numarasi_metni.getText().toString();
                String toastmsg = "Öğrenci eklenirken hata meydana geldi!";
                // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                // Veritabanı Sorgu İşlemleri
                try {
                    stmt = (veritabani.getExtraConnection()).createStatement();
                    String sql = "SELECT * FROM kullanicilar WHERE kullanicino = '"+ogrencino+"'";
                    ResultSet rs = stmt.executeQuery(sql);


                    if(rs.next()){
                        if(!rs.getBoolean("tip")){
                            sql = "INSERT INTO devamsizlik(ogrenciid, derskontrolid) VALUES('"+rs.getString("id")+"','"+ders_kontrol_id+"')";
                            stmt.executeUpdate(sql);

                            toastmsg = "Başarılı bir şekilde eklendi!";
                        }
                    }

                    onResume();
                    //Toast menü
                    Toast toast = Toast.makeText(getApplicationContext(), toastmsg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();

                } catch (Exception e){
                    System.err.println("Veritabanı hatası: "+e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }


            }
        });

        qrkod_ac_buton = (Button)findViewById(R.id.qrkod_ac_buton);
        qrkod_ac_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OgretmenDersKontrol.this, OgretmenQrKod.class);
                intent.putExtra("DERS_KONTROL_ID", ders_kontrol_id);
                startActivity(intent);
                OgretmenDersKontrol.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        ders_bitir_buton = (Button)findViewById(R.id.ders_bitir_buton);
        ders_bitir_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onay_alt_secim.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mevcut_kisi_sayisi = 0;
        ArrayList<DersKontrol> derse_katilan_ogrenciler = new ArrayList<DersKontrol>();
        ArrayList<String> ogrenci_idler = new ArrayList<String>();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM devamsizlik WHERE derskontrolid = '" + ders_kontrol_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                ogrenci_idler.add(rs.getString("ogrenciid"));
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            for(int i=0;i<ogrenci_idler.size();i++){
                String sql = "SELECT * FROM kullanicilar WHERE id = '" + ogrenci_idler.get(i) + "' ";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    derse_katilan_ogrenciler.add(new DersKontrol(rs.getString("kullanicino"),rs.getString("isim"),rs.getString("soyisim")));
                    mevcut_kisi_sayisi++;
                }
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        mevcut_kisi_sayisi_metni.setText(Integer.toString(mevcut_kisi_sayisi));
        ogrenci_listesi.setAdapter(new DersKontrolAdapter(this, R.layout.list_ders_kontrol, derse_katilan_ogrenciler));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onResume();
    }

    @Override
    public void onBackPressed() {
        onay_alt_secim.show();
    }

    String getGecenSure(){
        Date suanki_zaman =  Calendar.getInstance().getTime();
        long gecen_sure  = suanki_zaman.getTime() - baslangic_zamani.getTime();

        long gecen_saniye = gecen_sure / 1000 % 60;
        long gecen_dakika = gecen_sure / (60 * 1000) % 60;
        long gecen_saat = gecen_sure / (60 * 60 * 1000);

        String gecenSure = "";

        if(gecen_saat<10){
            gecenSure += "0" + gecen_saat;
        }else{
            gecenSure += gecen_saat;
        }

        gecenSure += ":";

        if(gecen_dakika<10){
            gecenSure += "0"+gecen_dakika;
        }else{
            gecenSure += gecen_dakika;
        }

        gecenSure += ":";

        if(gecen_saniye<10){
            gecenSure += "0"+gecen_saniye;
        }else{
            gecenSure += gecen_saniye;
        }
        return gecenSure;
    }
}