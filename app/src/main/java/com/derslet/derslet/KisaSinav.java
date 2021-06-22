package com.derslet.derslet;

public class KisaSinav {
    String kisa_sinav_isim;
    String kisa_sinav_durum;
    String soru_id;

    public KisaSinav(String kisa_sinav_isim, String kisa_sinav_durum, String soru_id) {
        this.kisa_sinav_isim = kisa_sinav_isim;
        this.kisa_sinav_durum = kisa_sinav_durum;
        this.soru_id = soru_id;
    }

    public String getKisa_sinav_isim() {
        return kisa_sinav_isim;
    }

    public void setKisa_sinav_isim(String kisa_sinav_isim) {
        this.kisa_sinav_isim = kisa_sinav_isim;
    }

    public String getKisa_sinav_durum() {
        return kisa_sinav_durum;
    }

    public void setKisa_sinav_durum(String kisa_sinav_durum) {
        this.kisa_sinav_durum = kisa_sinav_durum;
    }

    public String getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(String soru_id) {
        this.soru_id = soru_id;
    }

}
