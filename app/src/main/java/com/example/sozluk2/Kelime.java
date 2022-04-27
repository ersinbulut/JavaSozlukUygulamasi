package com.example.sozluk2;

public class Kelime {
    private int id;
    private String turkce;
    private String ingilizce;

    public Kelime() {
    }


    public Kelime(int id, String turkce, String ingilizce) {
        this.id = id;
        this.turkce = turkce;
        this.ingilizce = ingilizce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }

    public String getIngilizce() {
        return ingilizce;
    }

    public void setIngilizce(String ingilizce) {
        this.ingilizce = ingilizce;
    }
}
