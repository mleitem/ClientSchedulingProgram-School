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
import java.util.Scanner;

/** This creates an appointment scheduling application */
public class Main extends Application {

    /** The main method is called first when the program is run - it opens the database and directs the user to the login page */
    public static void main(String[] args) { launch(args); }

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
