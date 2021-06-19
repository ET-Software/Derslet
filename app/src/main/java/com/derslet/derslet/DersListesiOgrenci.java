package com.derslet.derslet;

public class DersListesiOgrenci {
    String ders_isim;
    String ders_ogretmen;
    String ders_bilgi;

    public DersListesiOgrenci(String ders_isim, String ders_ogretmen, String ders_bilgi) {
        this.ders_isim = ders_isim;
        this.ders_ogretmen = ders_ogretmen;
        this.ders_bilgi = ders_bilgi;
    }

    public String getDers_isim() {
        return ders_isim;
    }

    public void setDers_isim(String ders_isim) {
        this.ders_isim = ders_isim;
    }

    public String getDers_ogretmen() {
        return ders_ogretmen;
    }

    public void setDers_ogretmen(String ders_ogretmen) {
        this.ders_ogretmen = ders_ogretmen;
    }

    public String getDers_bilgi() {
        return ders_bilgi;
    }

    public void setDers_bilgi(String ders_bilgi) {
        this.ders_bilgi = ders_bilgi;
    }
}
