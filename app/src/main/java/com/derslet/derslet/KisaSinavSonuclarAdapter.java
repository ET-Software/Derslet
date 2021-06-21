package com.derslet.derslet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
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
import java.sql.Statement;
import java.util.ArrayList;

public class KisaSinavSonuclarAdapter extends ArrayAdapter<KisaSinavSonuclar> {
    private Context mContext;
    private int mResource;
    String ogrenciid;
    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    public KisaSinavSonuclarAdapter(@NonNull Context context, int resource, @NonNull ArrayList<KisaSinavSonuclar> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView ogrencinumara = convertView.findViewById(R.id.sonuc_numara);
        TextView ogrenciisim = convertView.findViewById(R.id.sonuc_isim);
        TextView ogrencibilgi = convertView.findViewById(R.id.sonuc_bilgi);
        ogrencinumara.setText(getItem(position).getOgrencinumara());
        ogrenciisim.setText(getItem(position).getOgrenciisim());
        ogrencibilgi.setText(getItem(position).getDogrusayisi());

        return convertView;
    }
}
