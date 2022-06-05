package Model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    protected static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    protected static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    protected static ObservableList<Contact> allContacts = FXCollections.observableArrayList();


    /** This method adds a customer to allLCustomers.
     * @param newCustomer is the customer added to allCustomers.
     */
    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    /** This method gets all items from allCustomers.
     * @return allCustomers
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /** This method added an appointment to allAppointments.
     * @param appointment is the appointment added to allAppointments.
     */
    public static void addAppointment(Appointment appointment) { allAppointments.add(appointment); }

    /** This method gets all items from allAppointments
     * @return allAppointments
     */
    public static ObservableList<Appointment> getAllAppointments() {return allAppointments;}

    /** This method deletes an appointment from allAppointments.
     * @param appointment is the appointment deleted from allAppointments.
     */
    public static void deleteAppointment(Appointment appointment){allAppointments.remove(appointment);}

    /** This method adds a contact to allContacts.
     * @param contact is the contact added to allContacts.
     */
    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }

    /** This method gets all items from allContacts.
     * @return allContacts.
     */
    public static ObservableList<Contact> getAllContacts() {return allContacts;}

}
