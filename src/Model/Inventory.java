package Model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    protected static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void deleteCustomer(String customer){
        allCustomers.remove(customer);
    }


}
