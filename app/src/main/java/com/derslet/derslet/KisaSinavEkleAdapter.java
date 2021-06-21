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
    String soru_id;

    public KisaSinavEkleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<KisaSinavEkle> objects) {
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
        TextView kisa_sinav_duzenle = convertView.findViewById(R.id.kisa_sinav_bilgi);
        kisa_sinav_isim.setText(getItem(position).getKisa_sinav_isim());

        kisa_sinav_duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), OgretmenSoruEkleDuzenle.class);
                soru_id = getItem(position).soru_id;
                intent.putExtra("SORU_ID", soru_id);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        return convertView;
    }
}
