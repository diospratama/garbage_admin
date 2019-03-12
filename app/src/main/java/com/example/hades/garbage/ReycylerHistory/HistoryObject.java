package com.example.hades.garbage.ReycylerHistory;

public class HistoryObject {


    private String HistoryId;
    private String driver_name;
    private String customer_name;
    private String biaya_jarak;
    private String biaya_sampah;
    private String tgl;
    private String profilImageUrlCustomer;
    private String profilImageUrlDriver;






    public HistoryObject(String HistoryId, String customer_name,String driver_name, String biaya_jarak, String biaya_sampah, String tgl, String profilImageUrlCustomer,String profilImageUrlDriver){
        this.HistoryId = HistoryId;
        this.driver_name = driver_name;
        this.customer_name=customer_name;
        this.biaya_jarak=biaya_jarak;
        this.biaya_sampah=biaya_sampah;
        this.tgl=tgl;
        this.profilImageUrlCustomer=profilImageUrlCustomer;
        this.profilImageUrlDriver=profilImageUrlDriver;

    }

    public String getHistoryId() {
        return HistoryId;
    }

    public void setHistoryId(String historyId) {
        HistoryId = historyId;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getBiaya_jarak() {
        return biaya_jarak;
    }

    public void setBiaya_jarak(String biaya_jarak) {
        this.biaya_jarak = biaya_jarak;
    }

    public String getBiaya_sampah() {
        return biaya_sampah;
    }

    public void setBiaya_sampah(String biaya_sampah) {
        this.biaya_sampah = biaya_sampah;
    }


    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getProfilImageUrlCustomer() {
        return profilImageUrlCustomer;
    }

    public void setProfilImageUrlCustomer(String profilImageUrl) {
        this.profilImageUrlCustomer = profilImageUrl;
    }

    public String getProfilImageUrlDriver() {
        return profilImageUrlDriver;
    }



    public void setProfilImageUrlDriver(String profilImageUrlDriver) {
        this.profilImageUrlDriver = profilImageUrlDriver;
    }







}
