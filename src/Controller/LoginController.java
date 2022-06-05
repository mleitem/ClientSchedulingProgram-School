package Controller;

import Helper.JDBC;
import Model.Appointment;
import Model.AppointmentQuery;
import Model.CustomerQuery;
import com.company.Main;
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
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
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
    private Label passwordlabelid;

    @FXML
    private Label usernamelabelid;

    @FXML
    private Label loginlabelid;

    @FXML
    private Label zoneidlabel;

    @FXML
    private Button exitbuttonid;

    @FXML
    private Button loginbuttonid;


    ResourceBundle rb = ResourceBundle.getBundle("Helper/frBundle", Locale.FRANCE);

    /** When this event handler is called, the program will exit. */
    @FXML
    public void exitButton(ActionEvent event) throws IOException {
        Stage closeStage = (Stage) exitbuttonid.getScene().getWindow();
        JDBC.closeConnection();
        closeStage.close();
    }

    /** This method alerts the user at the time of login if there are any appointments in the next 15 minutes of their local time. */
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


                }
                Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
                noSelectionAlert.setTitle("Upcoming Appointments");
                noSelectionAlert.setContentText(upcomingAppointments);
                noSelectionAlert.showAndWait();
            }
            if(filteredAppointments.size() == 0 || todayAppointments.size() == 0) {
                Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
                noSelectionAlert.setTitle("Upcoming Appointments");
                noSelectionAlert.setContentText("No appointments in the next 15 minutes.");
                noSelectionAlert.showAndWait();
            }
        }
    }


    /** This event handler checks the user's credentials against the database and directs them to the dashboard if
     * the credentials are true. In addition, the handler also records every login attempt to "Login_Activity.txt". */
    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameid.getText();
        String password = passwordid.getText();
        String filename = "login_activity.txt", user, date, time, result;
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        user = usernameid.getText();
        LocalDateTime loginTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDate = loginTime.format(dateFormat);


        if (login(username, password) == true) {

            result = "Success";
            outputFile.println(user + " | " + result + " | " + formattedDate + " | ");
            outputFile.close();
            viewTodayAppointments();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            scene = loader.load();
            Scene root = new Scene(scene);
            stage.setScene(root);
            stage.show();
        } else {
            result = "Failed";
            outputFile.println(user + " | " + result + " | " + formattedDate + " | ");
            outputFile.close();
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            if(Locale.getDefault().getLanguage().equalsIgnoreCase("fr")){
                noSelectionAlert.setTitle(rb.getString("Error"));
                noSelectionAlert.setContentText(rb.getString("Invalid"));
                noSelectionAlert.showAndWait();
            }
            else {
                noSelectionAlert.setTitle("Error");
                noSelectionAlert.setContentText("Invalid username or password.");
                noSelectionAlert.showAndWait();
            }
        }
    }

    /** This method checks the user's login credentials against those in the database. */
    public boolean login(String checkUsername, String checkPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, checkUsername);
        ps.setString(2, checkPassword);
        ResultSet rs = ps.executeQuery();
        boolean login;
        if (rs.next()) {
            login = true;
        }
        else{
            login = false;
        }
        return login;
    }

    /** This initializes the page, identifies the user's time zone, and identifies the user's computer's language. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TimeZone localTZ = TimeZone.getDefault();
        String displayZone = localTZ.getDisplayName();

        zoneid.setText(displayZone);



        if(Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) {
            usernamelabelid.setText(rb.getString("Username"));
            passwordlabelid.setText(rb.getString("Password"));
            zoneidlabel.setText(rb.getString("ZoneID"));
            loginbuttonid.setText(rb.getString("Login"));
            loginlabelid.setText(rb.getString("Login"));
            exitbuttonid.setText(rb.getString("Exit"));

        }

    }
}
