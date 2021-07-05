package com.derslet.derslet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class GonderiAdapterOgrenci extends ArrayAdapter<Gonderi> {
    private Context mContext;
    private int mResource;
    String kisa_sinav_id;
    String ders_id;
    String ogrenci_id;
    String ders_adi;

    private Veritabani veritabani = new Veritabani();
    private Statement stmt = null;

    public GonderiAdapterOgrenci(@NonNull Context context, int resource, @NonNull ArrayList<Gonderi> objects, @NonNull String ders_id, @NonNull String ogrenci_id, @NonNull String ders_adi) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.ders_id = ders_id;
        this.ders_adi  = ders_adi;
        this.ogrenci_id = ogrenci_id;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView gonderi_baslik = convertView.findViewById(R.id.gonderi_baslik);
        TextView gonderi_tarih = convertView.findViewById(R.id.gonderi_tarih);
        TextView gonderi_icerik = convertView.findViewById(R.id.gonderi_icerik);
        TextView gonderi_bilgi = convertView.findViewById(R.id.gonderi_bilgi);
        TextView gonderi_bilgi_ek = convertView.findViewById(R.id.gonderi_bilgi_ek);
        gonderi_baslik.setText(getItem(position).getGonderi_baslik());
        gonderi_tarih.setText(getItem(position).getGonderi_tarih());
        gonderi_icerik.setText(getItem(position).getGonderi_icerik());
        gonderi_bilgi.setText("Gönderi");

        if (getItem(position).getTip() == 1){
            gonderi_bilgi.setText("Kaynak: Erişmek için ");
            gonderi_bilgi_ek.setText("tıklayınız.");
            gonderi_bilgi_ek.setClickable(true);
            gonderi_bilgi_ek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = (Uri) getItem(position).getGonderi_baglanti();
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            });
        }
        else if (getItem(position).getTip() == 2){
            gonderi_bilgi.setText("Kısa Sınav: Cevaplamak için ");
            gonderi_bilgi_ek.setText("tıklayınız.");
            gonderi_bilgi_ek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kisa_sinav_id = getItem(position).kisa_sinav_id;
                    try {
                        stmt = (veritabani.getExtraConnection()).createStatement();
                        //Öğretmenin verdiği derslerin sorgusu
                        String sql = "SELECT * FROM sonuclar WHERE kisasinavid = '" + kisa_sinav_id + "' AND ogrenciid = '"+ogrenci_id+"'";
                        ResultSet rs = stmt.executeQuery(sql);

                        if(rs.next()){
                            //Toast menü
                            Toast toast = Toast.makeText(mContext, "Bu kısa sınavı önceden yaptınız!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                            toast.show();
                        }else{
                            Intent intent = new Intent(mContext.getApplicationContext(), OgrenciDerslerKisasinav.class);
                            intent.putExtra("KISA_SINAV_ID", kisa_sinav_id);
                            intent.putExtra("DERS_ID", ders_id);
                            intent.putExtra("DERS_ADI",ders_adi);
                            mContext.startActivity(intent);
                            ((Activity) mContext).overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        }

                    } catch (Exception e){
                        System.err.println("Kısa Sınav Hatası: "+e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                }
            });
        }

        return convertView;
    }
}
