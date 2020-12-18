package com.example.locationshop;

public class DataModel {
    String Email;
    String SPName;
    String Price;
    String Address;
    String Documnet;
    String Url;
    Double Lat;
    Double Lng;



    public DataModel(String email, String SPName, String price, String address, String documnet, String Url, Double lat, Double lng) {
        Email = email;
        this.SPName = SPName;
        Price = price;
        Address = address;
        Documnet = documnet;
        this.Url=Url;
        Lat = lat;
        Lng = lng;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSPName() {
        return SPName;
    }

    public void setSPName(String SPName) {
        this.SPName = SPName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDocumnet() {
        return Documnet;
    }

    public void setDocumnet(String documnet) {
        Documnet = documnet;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
