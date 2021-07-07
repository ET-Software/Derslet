package com.derslet.derslet;

public class Degerlendirme {
    String tarih_saat;
    Boolean bilgi;
    Float ortalama;
    String yorum;

    public Degerlendirme(String tarih_saat, Boolean bilgi){
        this.tarih_saat = tarih_saat;
        this.bilgi = bilgi;
    }

    public Degerlendirme(String tarih_saat, Float ortalama) {
        this.tarih_saat = tarih_saat;
        this.ortalama = ortalama;
    }

    public Degerlendirme(Float ortalama, String yorum) {
        this.ortalama = ortalama;
        this.yorum = yorum;
    }

    public String getTarih_saat() {
        return tarih_saat;
    }

    public void setTarih_saat(String tarih_saat) {
        this.tarih_saat = tarih_saat;
    }

    public Boolean getBilgi() {
        return bilgi;
    }

    public void setBilgi(Boolean bilgi) {
        this.bilgi = bilgi;
    }

    public Float getOrtalama() {
        return ortalama;
    }

    public void setOrtalama(Float ortalama) {
        this.ortalama = ortalama;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }
}
