package com.derslet.derslet;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class KisaSinavAdapter extends ArrayAdapter<KisaSinav> {
    private Context mContext;
    private int mResource;
    ArrayList<Soru> sorular;

    public KisaSinavAdapter(@NonNull Context context, int resource, @NonNull ArrayList<KisaSinav> objects, @NonNull ArrayList<Soru> sorular) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.sorular = sorular;
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
                //Yerel verilerden doğru sayısını çekme
                SharedPreferences yerel_veriler = mContext.getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
                String cevap_listesi = yerel_veriler.getString("CEVAPLAR", "0");

                BottomSheetDialog altsecim = new BottomSheetDialog(mContext, R.style.AltSecim_Tema);
                View sheetView = LayoutInflater.from(mContext).inflate(R.layout.kisasinav_alt_secim, (ViewGroup) parent.findViewById(R.id.kisasinav_alt_secim));

                TextView soru_metin = sheetView.findViewById(R.id.soru);


                TextView dogrucevap;
                TextView[] yanliscevap = new TextView[4];
                TextView[] cevaplar = new TextView[5];

                cevaplar[0] = sheetView.findViewById(R.id.cevap1_secim);
                cevaplar[1] = sheetView.findViewById(R.id.cevap2_secim);
                cevaplar[2] = sheetView.findViewById(R.id.cevap3_secim);
                cevaplar[3] = sheetView.findViewById(R.id.cevap4_secim);
                cevaplar[4] = sheetView.findViewById(R.id.cevap5_secim);

                ArrayList<Integer> sıra = new ArrayList<Integer>();
                sıra.add(0);
                sıra.add(1);
                sıra.add(2);
                sıra.add(3);
                sıra.add(4);

                int rand = (int)(Math.random() * (sıra.size()-1));
                dogrucevap = cevaplar[sıra.get(rand)];
                sıra.remove(rand);

                int c=0;
                while(sıra.size() > 1){
                    rand = (int)(Math.random() * (sıra.size()-1));
                    yanliscevap[c]=cevaplar[sıra.get(rand)];
                    sıra.remove(rand);
                    c++;
                }
                yanliscevap[c] = cevaplar[sıra.get(0)];

                Soru suankiSoru = sorular.get(position);
                soru_metin.setText(suankiSoru.getSoru());
                dogrucevap.setText(suankiSoru.getCevap1());
                yanliscevap[0].setText(suankiSoru.getCevap2());
                yanliscevap[1].setText(suankiSoru.getCevap3());
                yanliscevap[2].setText(suankiSoru.getCevap4());
                yanliscevap[3].setText(suankiSoru.getCevap5());


                dogrucevap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String yeni_cevaplar = cevap_listesi.substring(0,position) + '1' + cevap_listesi.substring(position+1);
                        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
                        duzenleyeci.putString("CEVAPLAR", yeni_cevaplar);
                        duzenleyeci.commit();
                        kisa_sinav_durum.setText("Cevaplandı");
                        altsecim.dismiss();
                    }
                });

                yanliscevap[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String yeni_cevaplar = cevap_listesi.substring(0,position) + '0' + cevap_listesi.substring(position+1);
                        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
                        duzenleyeci.putString("CEVAPLAR", yeni_cevaplar);
                        duzenleyeci.commit();
                        kisa_sinav_durum.setText("Cevaplandı");
                        altsecim.dismiss();
                    }
                });

                yanliscevap[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String yeni_cevaplar = cevap_listesi.substring(0,position) + '0' + cevap_listesi.substring(position+1);
                        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
                        duzenleyeci.putString("CEVAPLAR", yeni_cevaplar);
                        duzenleyeci.commit();
                        kisa_sinav_durum.setText("Cevaplandı");
                        altsecim.dismiss();
                    }
                });

                yanliscevap[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String yeni_cevaplar = cevap_listesi.substring(0,position) + '0' + cevap_listesi.substring(position+1);
                        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
                        duzenleyeci.putString("CEVAPLAR", yeni_cevaplar);
                        duzenleyeci.commit();
                        kisa_sinav_durum.setText("Cevaplandı");
                        altsecim.dismiss();
                    }
                });

                yanliscevap[3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String yeni_cevaplar = cevap_listesi.substring(0,position) + '0' + cevap_listesi.substring(position+1);
                        SharedPreferences.Editor duzenleyeci = yerel_veriler.edit();
                        duzenleyeci.putString("CEVAPLAR", yeni_cevaplar);
                        duzenleyeci.commit();
                        kisa_sinav_durum.setText("Cevaplandı");
                        altsecim.dismiss();
                    }
                });



                altsecim.setContentView(sheetView);
                altsecim.show();
            }
        });

        return convertView;
    }


}
