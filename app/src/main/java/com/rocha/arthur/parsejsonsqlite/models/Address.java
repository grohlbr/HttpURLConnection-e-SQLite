package com.rocha.arthur.parsejsonsqlite.models;

import java.io.Serializable;

/**
 * Created by arthur on 07/07/16.
 */

public class Address implements Serializable {
    private String street;
    private String suite;
    private String city;
    private String zipcode;

    public Address(String street, String zipcode, String city, String suite) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.suite = suite;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress(){
        return this.street+", "+this.suite+" - "+this.city+" / "+this.zipcode;
    }



}
