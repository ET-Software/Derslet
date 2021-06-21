package com.derslet.derslet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GonderiAdapterOgretmen extends ArrayAdapter<Gonderi> {
    private Context mContext;
    private int mResource;
    String kisa_sinav_id;

    public GonderiAdapterOgretmen(@NonNull Context context, int resource, @NonNull ArrayList<Gonderi> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView gonderi_baslik = convertView.findViewById(R.id.gonderi_baslik);
        TextView gonderi_tarih = convertView.findViewById(R.id.gonderi_tarih);
        TextView gonderi_icerik = convertView.findViewById(R.id.gonderi_icerik);
        TextView gonderi_bilgi = convertView.findViewById(R.id.gonderi_bilgi);
        TextView gonderi_bilgi_ek = convertView.findViewById(R.id.gonderi_bilgi_ek);
        gonderi_baslik.setText(getItem(position).getGonderi_baslik());
        gonderi_tarih.setText(getItem(position).getGonderi_tarih());
        gonderi_icerik.setText(getItem(position).getGonderi_icerik());
        gonderi_bilgi.setText("Gönderi");

        if (getItem(position).getTip() == 1){
            gonderi_bilgi.setText("Kaynak: Erişmek için ");
            gonderi_bilgi_ek.setText("tıklayınız.");
            gonderi_bilgi_ek.setClickable(true);
            gonderi_bilgi_ek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = (Uri) getItem(position).getGonderi_baglanti();
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            });
        }
        else if (getItem(position).getTip() == 2){
            gonderi_bilgi.setText("Kısa Sınav: Sonuçları için ");
            gonderi_bilgi_ek.setText("tıklayınız.");
            gonderi_bilgi_ek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(), OgretmenKisaSinavSonuclar.class);
                    kisa_sinav_id = getItem(position).kisa_sinav_id;
                    intent.putExtra("KISA_SINAV_ID", kisa_sinav_id);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                }
            });
        }

        return convertView;
    }
}
