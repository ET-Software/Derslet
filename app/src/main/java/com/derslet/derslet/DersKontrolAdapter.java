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

public class DersKontrolAdapter extends ArrayAdapter<DersKontrol> {
    private Context mContext;
    private int mResource;

    public DersKontrolAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DersKontrol> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView ogrenci_bilgi = convertView.findViewById(R.id.ogrenci_bilgi);
        ogrenci_bilgi.setText(getItem(position).getNumara() + " " + getItem(position).getAd() + " " + getItem(position).getSoyad());

        return convertView;
    }
}
