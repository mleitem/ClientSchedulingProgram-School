package com.company;

import Helper.JDBC;
import Model.AppointmentQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    /*public static void main(String[] args) throws SQLException, ParseException {
        JDBC.openConnection();

        System.out.println("Adding appointment...");
        LocalDate startDate = LocalDate.of(2022, 12, 20);
        LocalTime startTime = LocalTime.of(10, 00);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        java.sql.Date sqlDateStart = java.sql.Date.valueOf(startDateTime.toLocalDate());
        ///Date start = Date.valueOf()

        LocalDate endDate = LocalDate.of(2022, 12, 20);
        LocalTime endTime = LocalTime.of(11, 00);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        java.sql.Date sqlDateEnd = java.sql.Date.valueOf(endDateTime.toLocalDate());
        //Date end = Date.valueOf("12/11/2022");
        int user = 1;

        System.out.println("Really adding it now");
        AppointmentQuery.addAppointment("NEW", "NEW", "NEW", "checkup", sqlDateStart, sqlDateEnd, 1, user, 1);

        System.out.println("Appointment should have added");
        //AppointmentQuery.deleteAppointment(3);

        //AppointmentQuery.viewLastWeekAppointments();


        //JDBC.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }*/

    @Override
    public void start(Stage stage) throws Exception {
        JDBC.openConnection();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
