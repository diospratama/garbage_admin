package com.example.hades.garbage.ReycylerDriver;

public class DriverObject {


    private String driverId;
    private String name;
    private String phone;
    private String saldo;
    private String point;
    private String status_driver;
    private String profilImageUrl;



    public DriverObject(String driverId, String name, String phone, String saldo, String point, String status_driver, String profilImageUrl){
        this.driverId = driverId;
        this.name = name;
        this.phone=phone;
        this.point=point;
        this.saldo=saldo;
        this.status_driver=status_driver;
        this.profilImageUrl=profilImageUrl;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStatus_driver() {
        return status_driver;
    }

    public void setStatus_driver(String status_driver) {
        this.status_driver = status_driver;
    }

    public String getProfilImageUrl() {
        return profilImageUrl;
    }

    public void setProfilImageUrl(String profilImageUrl) {
        this.profilImageUrl = profilImageUrl;
    }




}
