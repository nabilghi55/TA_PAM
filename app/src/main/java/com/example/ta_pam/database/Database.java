package com.example.ta_pam.database;

public class Database {

    private String dataJudul;
    private String dataHarga;
    private String dataImage;
    private String key;

    public String getKey() {return key;}

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataJudul() {
        return dataJudul;
    }

    public String getDataHarga() {
        return dataHarga;
    }


    public String getDataImage() {
        return dataImage;
    }

    public Database(String dataJudul, String dataHarga, String dataImage) {
        this.dataJudul = dataJudul;
        this.dataHarga = dataHarga;
//        this.dataLang = dataLang;
        this.dataImage = dataImage;
    }
    public Database(){

    }

}