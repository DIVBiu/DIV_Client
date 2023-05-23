package com.example.managementapp;

public class Car {
    private String car_number;
    private String owner_email;
    private String owner_name;

    public Car(String car_number, String owner_email, String owner_name) {
        this.car_number = car_number;
        this.owner_email = owner_email;
        this.owner_name = owner_name;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "car_number='" + car_number + '\'' +
                ", owner_email='" + owner_email + '\'' +
                ", owner_name='" + owner_name + '\'' +
                '}';
    }
}
