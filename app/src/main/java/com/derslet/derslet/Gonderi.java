package com.derslet.derslet;

import android.net.Uri;

public class Gonderi {
    int tip;
    String gonderi_baslik;
    String gonderi_tarih;
    String gonderi_icerik;
    Uri gonderi_baglanti;
    String kisa_sinav_id;

    public Gonderi(int tip, String gonderi_baslik, String gonderi_tarih, String gonderi_icerik, String arg) {
        this.tip = tip;
        this.gonderi_baslik = gonderi_baslik;
        this.gonderi_tarih = gonderi_tarih;
        this.gonderi_icerik = gonderi_icerik;
        if (tip == 1){
            this.gonderi_baglanti = Uri.parse(arg);
        }
        else if (tip == 2){
            this.kisa_sinav_id = arg;
        }
    }

    public String getGonderi_baslik() {
        return gonderi_baslik;
    }

    public void setGonderi_baslik(String gonderi_baslik) {
        this.gonderi_baslik = gonderi_baslik;
    }

    public String getGonderi_tarih() {
        return gonderi_tarih;
    }

    public void setGonderi_tarih(String gonderi_tarih) {
        this.gonderi_tarih = gonderi_tarih;
    }

    public String getGonderi_icerik() {
        return gonderi_icerik;
    }

    public void setGonderi_icerik(String gonderi_icerik) {
        this.gonderi_icerik = gonderi_icerik;
    }

    public Uri getGonderi_baglanti() { return gonderi_baglanti; }

    public void setGonderi_baglanti(Uri gonderi_baglanti) { this.gonderi_baglanti = gonderi_baglanti; }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getKisa_sinav_id() {
        return kisa_sinav_id;
    }

    public void setKisa_sinav_id(String kisa_sinav_id) {
        this.kisa_sinav_id = kisa_sinav_id;
    }
}
