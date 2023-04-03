package com.example.hakaton;

public class Apartment extends User{
    public String comment_error, GOST, image_id;

    public Apartment() {
    }

    public Apartment(String id,String comment_error, String GOST, String image_id, String adress, String flate, String FIO) {
        super(id, adress, flate, FIO);
        this.comment_error = comment_error;
        this.GOST= GOST;
        this.image_id = image_id;
    }
    }





