package com.derslet.derslet;

public class Devamsizlik {
    String tarih_saat = "";
    Boolean bilgi;
    String bilgi2;
    String ogrencinumara;
    String ogrenciisim;

    public Devamsizlik(String tarih_saat, Boolean bilgi) {
        this.tarih_saat = tarih_saat;
        this.bilgi = bilgi;
    }

    public Devamsizlik(String tarih_saat, String bilgi) {
        this.tarih_saat = tarih_saat;
        this.bilgi2 = bilgi;
    }

    public Devamsizlik(String ogrencinumara, String ogrenciisim, String bilgi) {
        this.ogrencinumara = ogrencinumara;
        this.ogrenciisim = ogrenciisim;
        this.bilgi2 = bilgi;
    }

    public String getTarih_saat() {
        return tarih_saat;
    }

    public void setTarih_saat(String tarih_saat) {
        this.tarih_saat = tarih_saat;
    }

    public boolean getBilgi() {
        return bilgi;
    }

    public void setBilgi(Boolean bilgi) {
        this.bilgi = bilgi;
    }

    public String getBilgi2() {
        return bilgi2;
    }

    public void setBilgi2(String bilgi2) {
        this.bilgi2 = bilgi2;
    }

    public String getOgrencinumara() {
        return ogrencinumara;
    }

    public void setOgrencinumara(String ogrencinumara) {
        this.ogrencinumara = ogrencinumara;
    }

    public String getOgrenciisim() {
        return ogrenciisim;
    }

    public void setOgrenciisim(String ogrenciisim) {
        this.ogrenciisim = ogrenciisim;
    }
}
