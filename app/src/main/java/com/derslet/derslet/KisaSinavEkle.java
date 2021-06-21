package com.derslet.derslet;

public class KisaSinavEkle {
    String kisa_sinav_isim;
    String soru_id;

    public KisaSinavEkle(String kisa_sinav_isim, String soru_id) {
        this.kisa_sinav_isim = kisa_sinav_isim;
        this.soru_id = soru_id;
    }

    public String getKisa_sinav_isim() {
        return kisa_sinav_isim;
    }

    public void setKisa_sinav_isim(String kisa_sinav_isim) {
        this.kisa_sinav_isim = kisa_sinav_isim;
    }

    public String getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(String soru_id) {
        this.soru_id = soru_id;
    }
}
