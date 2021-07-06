package com.derslet.derslet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class OgrenciDerslerDegerlendirmeYap extends AppCompatActivity {

    TextView baslik;
    ImageButton geri_buton;
    ImageButton[] ders_yildiz_buton = new ImageButton[5];
    ImageButton[] ogretmen_yildiz_buton = new ImageButton[5];
    EditText yorum;
    Button gonder_buton;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    float soru1_puan = 0;
    float soru2_puan = 0;
    String derskontrol_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_dersler_degerlendirme_yap);

        baslik = (TextView) findViewById(R.id.baslik);
        baslik.setText(getIntent().getStringExtra("DERS_ADI"));

        yorum = (EditText) findViewById(R.id.soru2_icerik);

        derskontrol_id = getIntent().getStringExtra("DERS_KONTROL_ID");

        //Butonlar
        ders_yildiz_buton[0] = (ImageButton)findViewById(R.id.ders_yildiz_1);
        ders_yildiz_buton[1] = (ImageButton)findViewById(R.id.ders_yildiz_2);
        ders_yildiz_buton[2] = (ImageButton)findViewById(R.id.ders_yildiz_3);
        ders_yildiz_buton[3] = (ImageButton)findViewById(R.id.ders_yildiz_4);
        ders_yildiz_buton[4] = (ImageButton)findViewById(R.id.ders_yildiz_5);

        ogretmen_yildiz_buton[0] = (ImageButton)findViewById(R.id.ogretmen_yildiz_1);
        ogretmen_yildiz_buton[1] = (ImageButton)findViewById(R.id.ogretmen_yildiz_2);
        ogretmen_yildiz_buton[2] = (ImageButton)findViewById(R.id.ogretmen_yildiz_3);
        ogretmen_yildiz_buton[3] = (ImageButton)findViewById(R.id.ogretmen_yildiz_4);
        ogretmen_yildiz_buton[4] = (ImageButton)findViewById(R.id.ogretmen_yildiz_5);

        for(int i=0; i<5; i++){
            int puan = i;

            ders_yildiz_buton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0; j < 5; j++){
                        if(j>puan){
                            ders_yildiz_buton[j].setImageResource(R.drawable.ic_baseline_star_border_33);
                        }else{
                            ders_yildiz_buton[j].setImageResource(R.drawable.ic_baseline_star_33);
                        }
                    }
                    soru1_puan = puan + 1;
                }
            });

            ogretmen_yildiz_buton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0; j < 5; j++){
                        if(j>puan){
                            ogretmen_yildiz_buton[j].setImageResource(R.drawable.ic_baseline_star_border_33);
                        }else{
                            ogretmen_yildiz_buton[j].setImageResource(R.drawable.ic_baseline_star_33);
                        }
                    }
                    soru2_puan = puan + 1;
                }
            });
        }

        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciDerslerDegerlendirmeYap.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        gonder_buton = (Button)findViewById(R.id.gonder_buton);
        gonder_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soru1_puan == 0 || soru2_puan == 0){
                    //Toast menü
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen puan veriniz!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }else{
                    SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
                    String id = yerel_veriler.getString("Token","");

                    // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    // Veritabanı İşlemleri
                    try {
                        stmt = (veritabani.getExtraConnection()).createStatement();
                        String sql = "INSERT INTO degerlendirme(ogrenciid, derskontrolid, yorum, ortalama) VALUES ('" + id + "','" + derskontrol_id +"','"+yorum.getText()+"','"+((soru1_puan+soru2_puan)/2)+"')";
                        stmt.executeUpdate(sql);
                    } catch (Exception e){
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }

                    try {
                        String sql = "SELECT * FROM degerlendirmeistatistik WHERE id = '" + derskontrol_id + "' ";
                        ResultSet rs = stmt.executeQuery(sql);

                        float soru1_ortalama = 0;
                        float soru2_ortalama = 0;
                        float genel_ortalama = 0;
                        int kisi_sayisi = 0;

                        if(rs.next()){
                            soru1_ortalama = rs.getFloat("soru1ortalama");
                            soru2_ortalama = rs.getFloat("soru2ortalama");
                            genel_ortalama = rs.getFloat("genelortalama");
                            kisi_sayisi = rs.getInt("kisisayisi");

                            soru1_ortalama = ((soru1_ortalama*kisi_sayisi)+soru1_puan)/(kisi_sayisi+1);
                            soru2_ortalama = ((soru2_ortalama*kisi_sayisi)+soru2_puan)/(kisi_sayisi+1);
                            genel_ortalama = ((genel_ortalama*kisi_sayisi)+((soru1_puan+soru2_puan)/2))/(kisi_sayisi+1);
                            kisi_sayisi++;
                        }

                        sql = "UPDATE degerlendirmeistatistik SET soru1ortalama = '"+soru1_ortalama+"', soru2ortalama = '"+ soru2_ortalama+"', genelortalama = '"+genel_ortalama+"', kisisayisi = '"+kisi_sayisi+"' WHERE id = '"+derskontrol_id+"'";
                        stmt.executeUpdate(sql);

                    } catch (Exception e){
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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