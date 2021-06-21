package com.derslet.derslet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class KisaSinavEkleAdapter extends ArrayAdapter<KisaSinavEkle> {
    private Context mContext;
    private int mResource;
    Soru[] sorular;
    String ders_id;

    public KisaSinavEkleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<KisaSinavEkle> objects, @NonNull Soru[] sorular ,@NonNull String ders_id) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.sorular = sorular;
        this.ders_id = ders_id;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView kisa_sinav_isim = convertView.findViewById(R.id.kisa_sinav_isim);
        TextView kisa_sinav_duzenle = convertView.findViewById(R.id.kisa_sinav_bilgi);
        kisa_sinav_isim.setText(getItem(position).getKisa_sinav_isim());

        kisa_sinav_duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), OgretmenSoruEkleDuzenle.class);
                intent.putExtra("DUZENLE_MI",true);
                intent.putExtra("DERS_ID",ders_id);
                for(int i = 0; i<sorular.length; i++){
                    String soru_kayıt_ismi = "SORU"+(i+1);
                    intent.putExtra(soru_kayıt_ismi,sorular[i].getSoru());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP1",sorular[i].getCevap1());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP2",sorular[i].getCevap2());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP3",sorular[i].getCevap3());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP4",sorular[i].getCevap4());
                    intent.putExtra(soru_kayıt_ismi+"_CEVAP5",sorular[i].getCevap5());
                }
                intent.putExtra("DUZENLENECEK_SORU",position);
                intent.putExtra("SORU_SAYISI",sorular.length);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        return convertView;
    }
}
