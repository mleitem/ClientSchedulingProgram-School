package Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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
        closeStage.close();
    }

    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameid.getText();
        String password = passwordid.getText();

        User.login(username, password);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
