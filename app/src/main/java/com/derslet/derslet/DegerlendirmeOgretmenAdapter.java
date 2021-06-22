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

public class DegerlendirmeOgretmenAdapter extends ArrayAdapter<Degerlendirme> {
    private Context mContext;
    private int mResource;

    public DegerlendirmeOgretmenAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Degerlendirme> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView tarih_saat = convertView.findViewById(R.id.degerlendirme_tarih_saat);
        TextView ortalama = convertView.findViewById(R.id.degerlendirme_bilgi);
        tarih_saat.setText(getItem(position).getTarih_saat());
        ortalama.setText(getItem(position).getOrtalama().toString());

        return convertView;
    }
}
