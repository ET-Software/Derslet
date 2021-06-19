package com.derslet.derslet;

public class DersListesiOgretmen {
    String ders_isim;
    String ders_bilgi;

    public DersListesiOgretmen(String ders_isim, String ders_bilgi) {
        this.ders_isim = ders_isim;
        this.ders_bilgi = ders_bilgi;
    }

    public String getDers_isim() {
        return ders_isim;
    }

    public void setDers_isim(String ders_isim) {
        this.ders_isim = ders_isim;
    }

    public String getDers_bilgi() {
        return ders_bilgi;
    }

    public void setDers_bilgi(String ders_bilgi) {
        this.ders_bilgi = ders_bilgi;
    }
}
