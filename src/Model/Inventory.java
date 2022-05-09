package Model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    protected static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    protected static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();


    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void deleteCustomer(Customer customer){
        allCustomers.remove(customer);
    }

    public static void addAppointment(Appointment appointment) { allAppointments.add(appointment); }

    public static ObservableList<Appointment> getAllAppointments() {return allAppointments;}

    public static void deleteAppointment(Appointment appointment){allAppointments.remove(appointment);}

}
