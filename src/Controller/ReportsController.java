package Controller;

import Helper.JDBC;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> appointmentid1;

    @FXML
    private TableColumn<Appointment, Integer> appointmentid2;

    @FXML
    private TableColumn<Appointment, String> contact1;

    @FXML
    private TableColumn<Appointment, String> contact2;

    @FXML
    private TableView<Appointment> contactappointmentstable;

    @FXML
    private TableColumn<Contact, String> contactemail;

    @FXML
    private TableColumn<Contact, Integer> contactid;

    @FXML
    private TableColumn<Contact, String> contactname;

    @FXML
    private TableView<Contact> contactstable;

    @FXML
    private TableColumn<Appointment, Integer> customerid1;

    @FXML
    private TableColumn<Appointment, Integer> customerid2;

    @FXML
    private TableColumn<Appointment, String> description1;

    @FXML
    private TableColumn<Appointment, String> description2;

    @FXML
    private TableColumn<Appointment, Timestamp> end1;

    @FXML
    private TableColumn<Appointment, Timestamp> end2;

    @FXML
    private TableColumn<Appointment, String> location1;

    @FXML
    private TableColumn<Appointment, String> location2;

    @FXML
    private TableView<Appointment> monthappointmentstable;

    @FXML
    private TableColumn<Appointment, Timestamp> start1;

    @FXML
    private TableColumn<Appointment, Timestamp> start2;

    @FXML
    private TableColumn<Appointment, String> title1;

    @FXML
    private TableColumn<Appointment, String> title2;

    @FXML
    private TableColumn<Appointment, String> type1;

    @FXML
    private TableColumn<Appointment, String> type2;

    @FXML
    private TableColumn<Appointment, Integer> userid1;

    @FXML
    private TableColumn<Appointment, Integer> userid2;

    @FXML
    private Label totalid;

    @FXML
    private Label totalcontactappointments;

    @FXML
    private ComboBox<String> typecomboid;

    @FXML
    private ComboBox<String> monthcomboid;

    @FXML
    public void submitButton1(ActionEvent event) throws IOException, SQLException {
        String type = typecomboid.getValue();
        String month = monthcomboid.getValue();

        ObservableList<Appointment> monthTypeAppointments = ReportQuery.totalAppointmentsByTypeMonth(type, month);

        if(monthTypeAppointments.size() > 0) {
            monthappointmentstable.setItems(monthTypeAppointments);
            totalid.setText(String.valueOf(monthTypeAppointments.size()));
        }
        else {
            System.out.println("No Matches");
            totalid.setText("0");
        }

    }


    @FXML
    public void submitButton2(ActionEvent event) throws IOException, SQLException {
        Contact contact = contactstable.getSelectionModel().getSelectedItem();

        int contactId = contact.getContactId();
        ObservableList<Appointment> contactAppointments = ReportQuery.totalAppointmentsByContact(contactId);
        if(contactAppointments.size() > 0) {
            contactappointmentstable.setItems(contactAppointments);
            totalcontactappointments.setText(String.valueOf(contactAppointments.size()));
        }
        else {
            System.out.println("No matches");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ReportQuery.viewAllContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        monthcomboid.setItems(months);
        try {
            typecomboid.setItems(ReportQuery.viewAppointmentTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        monthappointmentstable.setItems(Inventory.getAllAppointments());
        appointmentid1.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title1.setCellValueFactory(new PropertyValueFactory<>("title"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));
        location1.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact1.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type1.setCellValueFactory(new PropertyValueFactory<>("type"));
        start1.setCellValueFactory(new PropertyValueFactory<>("start"));
        end1.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerid1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userid1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalid.setText(String.valueOf(Inventory.getAllAppointments().size()));

        contactstable.setItems(Inventory.getAllContacts());

        contactid.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactname.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactemail.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));


        contactappointmentstable.setItems(Inventory.getAllAppointments());
        appointmentid2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        description2.setCellValueFactory(new PropertyValueFactory<>("description"));
        location2.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact2.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type2.setCellValueFactory(new PropertyValueFactory<>("type"));
        start2.setCellValueFactory(new PropertyValueFactory<>("start"));
        end2.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerid2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userid2.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalcontactappointments.setText(String.valueOf(Inventory.getAllAppointments().size()));

    }
}
