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

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    /**
     * Creating log file
     */

    //File name & variables
    String filename = "login_activity.txt", attemptId, user, date, timestamp, result;

    // Create Scanner
    //Scanner keyboad = new Scanner(System.in);

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
