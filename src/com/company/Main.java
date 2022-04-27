package com.company;

import Helper.JDBC;
import Model.AppointmentQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    /*public static void main(String[] args) throws SQLException, ParseException {
        JDBC.openConnection();

        /*LocalDate startDate = LocalDate.of(2022, 04, 20);
        LocalTime startTime = LocalTime.of(10, 00);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        java.sql.Date sqlDateStart = java.sql.Date.valueOf(startDateTime.toLocalDate());

        LocalDate endDate = LocalDate.of(2022, 04, 20);
        LocalTime endTime = LocalTime.of(11, 00);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        java.sql.Date sqlDateEnd = java.sql.Date.valueOf(endDateTime.toLocalDate());

        //AppointmentQuery.addAppointment("last week", "normal checkup", "primary office", "checkup", sqlDateStart, sqlDateEnd, 3, 1, 1);

        //AppointmentQuery.deleteAppointment(3);

        //AppointmentQuery.viewLastWeekAppointments();


        JDBC.closeConnection();
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
