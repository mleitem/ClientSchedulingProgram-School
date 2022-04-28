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

    protected static ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();

    public static void addAppointment(Appointment newAppointment) {
        associatedAppointments.add(newAppointment);
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return associatedAppointments;
    }

    /*public static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    public static void addToFilteredAppointments(Appointment filteredAppointment) {filteredAppointments.add(filteredAppointment);}

    public static void clearFilteredAppointments() {
        if (filteredAppointments.size() > 0) {
            for (int i = 0; i < filteredAppointments.size(); ++i) {
                filteredAppointments.remove(i);
            }
        }
    }*/

    public static void deleteAppointment(Appointment deleteAppointment) {
        associatedAppointments.remove(deleteAppointment);
    }



}
