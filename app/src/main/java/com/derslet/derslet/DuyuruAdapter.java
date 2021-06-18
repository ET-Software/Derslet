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

public class DuyuruAdapter extends ArrayAdapter<Duyuru> {
    private Context mContext;
    private int mResource;

    public DuyuruAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Duyuru> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView duyuru_baslik = convertView.findViewById(R.id.duyuru_baslik);
        TextView duyuru_yazi = convertView.findViewById(R.id.duyuru_yazi);
        duyuru_baslik.setText(getItem(position).getBaslik());
        duyuru_yazi.setText(getItem(position).getIcerik());

        return convertView;
    }
}
