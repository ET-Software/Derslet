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

public class DevamsizlikOgretmenAdapter extends ArrayAdapter<Devamsizlik> {
    private Context mContext;
    private int mResource;

    public DevamsizlikOgretmenAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Devamsizlik> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView tarih_saat = convertView.findViewById(R.id.devamsizlik_tarih_saat);
        TextView bilgi = convertView.findViewById(R.id.devamsizlik_bilgi);
        TextView ogrencinumara = convertView.findViewById(R.id.devamsizlik_numara);
        TextView ogrenciisim = convertView.findViewById(R.id.devamsizlik_isim);
        bilgi.setText(getItem(position).getBilgi2());
        if (getItem(position).getTarih_saat() != ""){
            tarih_saat.setText(getItem(position).getTarih_saat());
        }
        if (getItem(position).getOgrencinumara() != "" && getItem(position).getOgrenciisim() != ""){
            ogrencinumara.setText(getItem(position).getOgrencinumara());
            ogrenciisim.setText(getItem(position).getOgrenciisim());
            bilgi.setText(getItem(position).getBilgi3());
        }

        return convertView;
    }
}
