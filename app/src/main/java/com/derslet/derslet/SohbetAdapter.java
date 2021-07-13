package com.derslet.derslet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SohbetAdapter extends ArrayAdapter<Sohbet> {
    private Context mContext;
    private int mResource;
    private List<Sohbet> mObjects;

    public SohbetAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Sohbet> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
    }

    public List<Sohbet> getData() {
        return mObjects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView mesaj_baslik = convertView.findViewById(R.id.mesaj_baslik);
        TextView mesaj = convertView.findViewById(R.id.mesaj);
        TextView sohbet_ogretmen_numara = convertView.findViewById(R.id.sohbet_ogretmen_numara);
        TextView sohbet_ogretmen_isim = convertView.findViewById(R.id.sohbet_ogretmen_isim);
        TextView sohbet_ogretmen_bilgi = convertView.findViewById(R.id.sohbet_ogretmen_bilgi);
        TextView sohbet_isim = convertView.findViewById(R.id.sohbet_isim);
        TextView sohbet_bilgi = convertView.findViewById(R.id.sohbet_bilgi);
        TextView altsecim = convertView.findViewById(R.id.kisi_isim);

        if (getItem(position).getProfilresim() != 0){
            mesaj_baslik.setText(getItem(position).getAd_soyad());
            mesaj.setText(getItem(position).getMesaj());
        }
        else if (getItem(position).getNumara() != ""){
            sohbet_ogretmen_numara.setText(getItem(position).getNumara());
            sohbet_ogretmen_isim.setText(getItem(position).getAd_soyad());
            sohbet_ogretmen_bilgi.setText(getItem(position).getMesaj());
        }
        else if (getItem(position).getAd_soyad() != "") {
            sohbet_isim.setText(getItem(position).getAd_soyad());
            sohbet_bilgi.setText(getItem(position).getMesaj());
        }

        if(getItem(position).getOgretmen() != ""){
            altsecim.setText(getItem(position).getOgretmen());
        }

        return convertView;
    }
}

