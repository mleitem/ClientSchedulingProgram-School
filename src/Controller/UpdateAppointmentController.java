package Controller;

import Model.Appointment;
import Model.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class UpdateAppointmentController {

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

    Stage stage;
    Parent scene;


    public void setAppointment(Appointment appointment){

        appointmentid.setText(String.valueOf(appointment.getAppointmentId()));
        titleid.setText(appointment.getTitle());
        descriptionid.setText(appointment.getDescription());
        typeid.setText(appointment.getType());
        locationid.setText(appointment.getLocation());
        contactid.setValue(appointment.getContactId());
        //startdateid.setValue(appointment.getStart());
        //enddateid.setValue(appointment.getEnd());
        customerid.setValue(appointment.getCustomerId());
        userid.setValue(appointment.getUserId());

    }

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

    public void saveButton(ActionEvent event) throws SQLException, IOException {
        int id = Integer.parseInt(appointmentid.getText());
        String title = titleid.getText();
        String description = descriptionid.getText();
        String location  = locationid.getText();
        String type = typeid.getText();
        LocalDate startDate = startdateid.getValue();
        LocalDate endDate = enddateid.getValue();
        LocalTime startTime = starttimeid.getValue();
        LocalTime endTime = endtimeid.getValue();
        int customerId = customerid.getValue();
        int userId = userid.getValue();
        int contactId = contactid.getValue();

        //Combine date/time entries to get one entry for the constuctor
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        Date sqlDateEnd = java.sql.Date.valueOf(endDateTime.toLocalDate());
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        Date sqlDateStart = java.sql.Date.valueOf(startDateTime.toLocalDate());

        AppointmentQuery.updateAppointment(id, title, description, location, type, sqlDateStart, sqlDateEnd, customerId, userId, contactId);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        scene = loader.load();
        Scene root = new Scene(scene);
        stage.setScene(root);
        stage.show();
    }

}
