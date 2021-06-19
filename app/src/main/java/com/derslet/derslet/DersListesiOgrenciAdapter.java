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

public class DersListesiOgrenciAdapter extends ArrayAdapter<DersListesiOgrenci> {

    private Context mContext;
    private int mResource;

    public DersListesiOgrenciAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DersListesiOgrenci> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView ders_isim = convertView.findViewById(R.id.ders_isim);
        TextView ders_ogretmen = convertView.findViewById(R.id.ders_ogretmen);
        TextView ders_bilgi = convertView.findViewById(R.id.ders_bilgi);
        ders_isim.setText(getItem(position).getDers_isim());
        ders_ogretmen.setText(getItem(position).getDers_ogretmen());
        ders_bilgi.setText(getItem(position).getDers_bilgi());

        return convertView;
    }
}
