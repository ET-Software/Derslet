package com.derslet.derslet;

public class Soru {
    String soru;
    String cevap1;
    String cevap2;
    String cevap3;
    String cevap4;
    String cevap5;

    public Soru(){

    }

    public Soru(String soru, String cevap1, String cevap2, String cevap3, String cevap4, String cevap5) {
        this.soru = soru;
        this.cevap1 = cevap1;
        this.cevap2 = cevap2;
        this.cevap3 = cevap3;
        this.cevap4 = cevap4;
        this.cevap5 = cevap5;
    }

    public String getSoru() {
        return soru;
    }

    public String getCevap1() {
        return cevap1;
    }

    public String getCevap2() {
        return cevap2;
    }

    public String getCevap3() {
        return cevap3;
    }

    public String getCevap4() {
        return cevap4;
    }

    public String getCevap5() {
        return cevap5;
    }
}
