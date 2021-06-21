package com.derslet.derslet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class KisaSinavAdapter extends ArrayAdapter<KisaSinav> {
    private Context mContext;
    private int mResource;
    String soru_id;

    public KisaSinavAdapter(@NonNull Context context, int resource, @NonNull ArrayList<KisaSinav> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView kisa_sinav_isim = convertView.findViewById(R.id.kisa_sinav_isim);
        TextView kisa_sinav_durum = convertView.findViewById(R.id.kisa_sinav_bilgi);
        kisa_sinav_isim.setText(getItem(position).getKisa_sinav_isim());
        kisa_sinav_durum.setText(getItem(position).getKisa_sinav_durum());

        kisa_sinav_durum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), OgrenciDerslerKisasinav.class); //Cevaplama ekranı yapılacak.
                soru_id = getItem(position).soru_id;
                intent.putExtra("SORU_ID", soru_id);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        return convertView;
    }
}
