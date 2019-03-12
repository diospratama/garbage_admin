package com.example.hades.garbage.ReycylerCustomer;

public class CustomerObject {


    private String customerId;
    private String name;
    private String phone;
    private String saldo;
    private String point;
    private String profilImageUrl;




    public CustomerObject(String customerId, String name, String phone, String saldo, String point, String profilImageUrl){
        this.customerId = customerId;
        this.name = name;
        this.phone=phone;
        this.point=point;
        this.saldo=saldo;
        this.profilImageUrl=profilImageUrl;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getProfilImageUrl() {
        return profilImageUrl;
    }

    public void setProfilImageUrl(String profilImageUrl) {
        this.profilImageUrl = profilImageUrl;
    }


}
