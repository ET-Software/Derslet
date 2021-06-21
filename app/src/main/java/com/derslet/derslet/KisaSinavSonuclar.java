package com.derslet.derslet;

public class KisaSinavSonuclar {
    String ogrencinumara;
    String ogrenciisim;
    String dogrusayisi;

    public KisaSinavSonuclar(String ogrencinumara, String ogrenciisim, String dogrusayisi) {
        this.ogrencinumara = ogrencinumara;
        this.ogrenciisim = ogrenciisim;
        this.dogrusayisi = dogrusayisi;
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

    public String getDogrusayisi() {
        return dogrusayisi;
    }

    public void setDogrusayisi(String dogrusayisi) {
        this.dogrusayisi = dogrusayisi;
    }
}
