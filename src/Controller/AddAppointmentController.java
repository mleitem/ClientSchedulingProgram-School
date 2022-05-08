package Controller;

import Model.Appointment;
import Model.AppointmentQuery;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentid;

    @FXML
    private ComboBox<Integer> contactid;

    @FXML
    private ComboBox<Integer> customerid;

    @FXML
    private TextField descriptionid;

    @FXML
    private DatePicker enddateid;

    @FXML
    private ComboBox<LocalTime> endtimeid;

    @FXML
    private TextField locationid;

    @FXML
    private DatePicker startdateid;

    @FXML
    private ComboBox<LocalTime> starttimeid;

    @FXML
    private TextField titleid;

    @FXML
    private TextField typeid;

    @FXML
    private ComboBox<Integer> userid;

    public void endSelect(ActionEvent event) {

        if(starttimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime start = starttimeid.getSelectionModel().getSelectedItem();
            LocalTime end = start.plusHours(1);
            System.out.println("Start time: " + start + " - " + "End time: " + end);
            endtimeid.getSelectionModel().select(end);
        }
    }

    public void startSelect(ActionEvent event) {

        if(endtimeid.getSelectionModel().getSelectedItem() != null) {
            LocalTime end = endtimeid.getSelectionModel().getSelectedItem();
            LocalTime start = end.minusHours(1);
            starttimeid.setValue(start);
        }
    }

    public void endDate(ActionEvent event) {
        if(startdateid.getValue() != null){
            //Date start = startdateid.getValue();
            LocalDate start = startdateid.getValue();
            LocalDate end = start;
            System.out.println("Start date: " + start + " - " + "End date: " + end);
            enddateid.setValue(end);

        }

    }

    public void startDate(ActionEvent event){
        if(enddateid.getValue() != null) {
            LocalDate end = enddateid.getValue();
            LocalDate start = end;
            startdateid.setValue(start);
        }
    }

    public void submitAppointment(ActionEvent event) throws SQLException, IOException {

        int size = Customer.getAllAppointments().size();

        /*String title = titleid.getText();
        String description = descriptionid.getText();
        String location = locationid.getText();
        String type = typeid.getText();
        LocalDate startDate = startdateid.getValue();
        LocalDate endDate = enddateid.getValue();
        LocalTime startTime = starttimeid.getValue();
        LocalTime endTime = endtimeid.getValue();
        int customerId = customerid.getValue();
        int userId = userid.getValue();
        int contactId = contactid.getValue();*/

        String title = "test";
        String description = "test";
        String location = "test";
        String type = "test";
        int customerId = 1;
        int userId = 1;
        int contactId = 1;

        LocalDate endDate = LocalDate.of(2022, 12, 20);
        LocalTime endTime = LocalTime.of(11, 00);
        LocalDate startDate = LocalDate.of(2022, 12, 20);
        LocalTime startTime = LocalTime.of(10, 00);

        //Combine date/time entries to get one entry for the constuctor
        //LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        //LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        //Date startDate1 = Date.valueOf(startDate);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        java.sql.Date sqlDateEnd = java.sql.Date.valueOf(endDateTime.toLocalDate());
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        java.sql.Date sqlDateStart = java.sql.Date.valueOf(startDateTime.toLocalDate());
        //Date endDate1 = Date.valueOf(endDate);


        AppointmentQuery.addAppointment(title, description, location, type, sqlDateStart, sqlDateEnd, customerId, userId, contactId);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Integer> allContacts = AppointmentQuery.viewAllContacts();
            ObservableList<Integer> allCustomers = AppointmentQuery.viewAllCustomers();
            ObservableList<Integer> allUsers = AppointmentQuery.viewAllUsers();
            ObservableList<LocalTime> startTimes = AppointmentQuery.viewStartTimes();
            ObservableList<LocalTime> endTimes = AppointmentQuery.viewEndTimes();
            contactid.setItems(allContacts);
            customerid.setItems(allCustomers);
            userid.setItems(allUsers);
            starttimeid.setItems(startTimes);
            endtimeid.setItems(endTimes);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}
