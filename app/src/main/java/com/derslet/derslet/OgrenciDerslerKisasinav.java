package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OgrenciDerslerKisasinav extends AppCompatActivity {

    ImageButton geri_buton;
    Button gonder_buton;
    ListView kisa_sinav_sorular;
    TextView baslik;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    String kisa_sinav_id;
    String ders_id;
    String ders_adi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_kisasinav);

        kisa_sinav_id = getIntent().getStringExtra("KISA_SINAV_ID");
        ders_id = getIntent().getStringExtra("DERS_ID");
        ders_adi = getIntent().getStringExtra("DERS_ADI");

        kisa_sinav_sorular = (ListView) findViewById(R.id.ogrenci_kisa_sinav_sorular);
        baslik = (TextView) findViewById(R.id.baslik);
        baslik.setText(ders_adi);

        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerKisasinav.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gonder_buton = (Button)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kisasinavDurumu = "Gönderim başarısız!";

                SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
                String id = yerel_veriler.getString("Token","");
                String cevap_listesi = yerel_veriler.getString("CEVAPLAR","0");

                int dogru_sayisi = 0;
                for(Character cevap: cevap_listesi.toCharArray()){
                    if(cevap.equals('1')){
                        dogru_sayisi++;
                    }
                }

                // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                // Veritabanı Sorgu İşlemleri
                try {
                    stmt = (veritabani.getExtraConnection()).createStatement();
                    String sql = "INSERT INTO sonuclar(kisasinavid,ogrenciid,dogrusayisi) VALUES('" + kisa_sinav_id + "','"+id+"','"+dogru_sayisi+"')";
                    stmt.executeUpdate(sql);

                    kisasinavDurumu = "Gönderim başarılı!";

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    OgrenciDerslerKisasinav.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                    timer.cancel();
                                }
                            });

                        }
                    }, 1000); // 1sn bekliyor.

                } catch (Exception e){
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                //Toast menü
                Toast toast = Toast.makeText(getApplicationContext(), kisasinavDurumu, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<KisaSinav> sorular_bilgi = new ArrayList<KisaSinav>();
        ArrayList<Soru> sorular = new ArrayList<Soru>();

        // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String bos_cevap_listesi = "";
        // Veritabanı Sorgu İşlemleri
        try {
            stmt = (veritabani.getExtraConnection()).createStatement();
            String sql = "SELECT * FROM sorular WHERE kisasinavid = '" + kisa_sinav_id + "' ";
            ResultSet rs = stmt.executeQuery(sql);

            int sayac = 1;
            while(rs.next()){
                sorular_bilgi.add(new KisaSinav("Soru "+sayac, "Cevaplanmadı", rs.getString("id")));
                sorular.add(new Soru(rs.getString("soru"),rs.getString("cevap1"),rs.getString("cevap2"),rs.getString("cevap3"),rs.getString("cevap4"),rs.getString("cevap5")));
                bos_cevap_listesi += "0";
                sayac++;
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
        duzenleyeci.putString("CEVAPLAR", bos_cevap_listesi);
        duzenleyeci.commit();

        kisa_sinav_sorular.setAdapter(new KisaSinavAdapter(this, R.layout.list_ogrenci_kisa_sinav, sorular_bilgi, sorular));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


    }
}