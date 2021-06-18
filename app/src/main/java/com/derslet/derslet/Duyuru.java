package com.derslet.derslet;

public class Duyuru {
    String baslik;
    String icerik;

    public Duyuru(String baslik, String icerik) {
        this.baslik = baslik;
        this.icerik = icerik;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }
}
