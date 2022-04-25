package Controller;

import Helper.JDBC;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private PasswordField passwordid;

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

    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameid.getText();
        String password = passwordid.getText();

        if (login(username, password) == true) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/"));
            scene = loader.load();
            Scene root = new Scene(scene);
            stage.setScene(root);
            stage.show();
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

    }
}
