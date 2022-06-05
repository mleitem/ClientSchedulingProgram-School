package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divId;

    public Customer(int id, String name, String address, String postalCode, String phone, int divId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divId = divId;
    }

    public Customer(String name, String address, String postalCode, String phone, int divId){
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divId = divId;
    }

    /** Getters and setters for Customer variables. */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivId() {
        return divId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }





}
