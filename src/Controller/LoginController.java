package Controller;

import Helper.JDBC;
import Model.Appointment;
import Model.AppointmentQuery;
import Model.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private PasswordField passwordid;

    @FXML
    private Label zoneid;

    @FXML
    private TextField usernameid;

    @FXML
    private Button exitbuttonid;

    @FXML
    private Button loginbuttonid;

    /** When this event handler is called, the program will exit. */
    @FXML
    public void exitButton(ActionEvent event) throws IOException {
        Stage closeStage = (Stage) exitbuttonid.getScene().getWindow();
        JDBC.closeConnection();
        closeStage.close();
    }

    public static void viewTodayAppointments() throws SQLException {
        ObservableList<Appointment> todayAppointments = AppointmentQuery.viewTodayAppointments();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        if (todayAppointments.size() > 0) {

            for (int i = 0; i < todayAppointments.size(); ++i) {
                Appointment appointment = todayAppointments.get(i);
                Timestamp start = appointment.getStart();
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime later = now.plusMinutes(15);
                LocalDateTime ldtStart = start.toLocalDateTime();

                if (ldtStart.isBefore(later) && ldtStart.isAfter(now)) {
                    filteredAppointments.add(appointment);
                }
            }


            if (filteredAppointments.size() > 0) {

                String upcomingAppointments = "Appointments starting in the next 15 minutes: \n";
                for (int i = 0; i < filteredAppointments.size(); i++) {
                    Appointment appointment = filteredAppointments.get(i);
                    int id = appointment.getAppointmentId();
                    Timestamp start = appointment.getStart();

                    String addedAppointment = "Appointment ID: " + id + " - Start Time: " + start + "\n";

                    upcomingAppointments = upcomingAppointments.concat(addedAppointment);

                    System.out.println(upcomingAppointments);


                }
                Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
                noSelectionAlert.setTitle("Upcoming Appointments");
                noSelectionAlert.setContentText(upcomingAppointments);
                noSelectionAlert.showAndWait();
            } else {
                Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
                noSelectionAlert.setTitle("Upcoming Appointments");
                noSelectionAlert.setContentText("No appointments in the next 15 minutes.");
                noSelectionAlert.showAndWait();
            }
        }
    }


    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameid.getText();
        String password = passwordid.getText();

        if (login(username, password) == true) {
            //AppointmentQuery.allAppointments();
            viewTodayAppointments();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            scene = loader.load();
            Scene root = new Scene(scene);
            stage.setScene(root);
            stage.show();
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setContentText("Invalid username or password.");
            noSelectionAlert.showAndWait();
        }
    }

    public boolean login(String checkUsername, String checkPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, checkUsername);
        ps.setString(2, checkPassword);
        ResultSet rs = ps.executeQuery();
        boolean login;
        if (rs.next()) {
            System.out.println("Success!");
            login = true;
        }
        else{
            System.out.println("Try again!");
            login = false;
        }
        return login;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TimeZone localTZ = TimeZone.getDefault();
        String displayZone = localTZ.getDisplayName();


        zoneid.setText(displayZone);



    }
}
