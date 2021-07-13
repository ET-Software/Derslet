package com.derslet.derslet;

public class Sohbet {
    int profilresim = 0;
    String ad_soyad = "";
    String mesaj = "";
    String numara = "";
    String ogretmen = "";

    public Sohbet(int profilresim, String ad_soyad, String mesaj) {
        this.profilresim = profilresim;
        this.ad_soyad = ad_soyad;
        this.mesaj = mesaj;
    }

    public Sohbet(String numara, String ad_soyad, String mesaj) {
        this.numara = numara;
        this.ad_soyad = ad_soyad;
        this.mesaj = mesaj;
    }

    public Sohbet(String ad_soyad, String mesaj) {
        this.ad_soyad = ad_soyad;
        this.mesaj = mesaj;
    }

    public Sohbet(String ogretmen) {
        this.ogretmen = ogretmen;
    }

    public int getProfilresim() {
        return profilresim;
    }

    public void setProfilresim(int profilresim) {
        this.profilresim = profilresim;
    }

    public String getAd_soyad() {
        return ad_soyad;
    }

    public void setAd_soyad(String ad_soyad) {
        this.ad_soyad = ad_soyad;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getNumara() {
        return numara;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }

    public String getOgretmen() {
        return ogretmen;
    }

    public void setOgretmen(String ogretmenler) {
        this.ogretmen = ogretmenler;
    }
}
