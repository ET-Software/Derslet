package com.derslet.derslet;

public class DersKontrol {
    String numara;
    String ad;
    String soyad;

    public DersKontrol(String numara, String ad, String soyad) {
        this.numara = numara;
        this.ad = ad;
        this.soyad = soyad;
    }

    public String getNumara() {
        return numara;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
}
