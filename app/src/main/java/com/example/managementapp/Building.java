package com.example.managementapp;

import java.util.ArrayList;

public class Building{

    private String id;
    private String address;
    private ArrayList<User> tenants;

    public Building(String id, String address, ArrayList<User> tenants) {
        this.id = id;
        this.address = address;
        this.tenants = tenants;
    }

    public Building() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<User> getTenants() {
        return tenants;
    }

    public void setTenants(ArrayList<User> tenants) {
        this.tenants = tenants;
    }
}

