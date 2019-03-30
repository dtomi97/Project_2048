package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.UserDAO;
import Model.UserDAOFactory;
import Model.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to handle the Register Scene's actions.
 */
public class RegisterSceneController implements Initializable {
    /**
     * The logger of this class.
     */
    private static Logger logger = LoggerFactory.getLogger(RegisterSceneController.class);
    /**
     * The button to start the registration.
     */
    @FXML
    public Button REGISTER;
    /**
     * The password of the registering {@code User}.
     */
    @FXML
    public PasswordField PASSWORD;
    /**
     * The username of the registering {@code User}.
     */
    @FXML
    public TextField USERNAME;
    /**
     * The feed back label for giving back information.
     * @see Label
     */
    @FXML
    public Label feedbackLabel;
    /**
     * The button to load the Login Scene.
     */
    @FXML
    public Button GOBACK;
    /**
     * The {@code UserDAO} of this scene.
     * @see UserDAO
     */
    private UserDAO ud;

    /**
     * Registers a new user if the USERNAME and PASSWORD fields are legally filled after that it loads the Login Scene.
     * @param event the event that occurred.
     * @see javafx.event.ActionEvent
     */
    @FXML
    public void handleRegistration(ActionEvent event) {
        Validator v = new Validator(ud);

        if (v.regUsernameisUnique(USERNAME.getText())) {
            if(v.regUsernameValidate(USERNAME.getText())) {
                if (v.regPasswordValidate(PASSWORD.getText())) {
                    ud.createUser(USERNAME.getText(), PASSWORD.getText());
                    backToLogin(true);
                }
                else
                    feedbackLabel.setText("The password must be 6-16 characters long \n and contain at least one of each of these types: \n Upper-case letters, Numbers, \n Special characters: !, $, #, +, -, %");
            }
            else
                feedbackLabel.setText("The username must be 6-16 characters long!");
        } else {
            feedbackLabel.setText("Username is already in use!");
        }
    }
    /**
     * Loads the Login Scene.
     * @param check if true sets the feedbackLabel to successful registration.
     * @see LoginSceneController#feedbackLabel
     */
    private void backToLogin(boolean check){
        try {

            Stage stage = (Stage) USERNAME.getScene().getWindow();
            Parent root;
            FXMLLoader fl = new FXMLLoader(Main.class.getResource("/fxml/LoginScene.fxml"));
            root = fl.load();
            if(check)
                fl.<LoginSceneController>getController().feedbackLabel.setText("Succesful registration!");

            root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());

            Scene scene = new Scene(root);

            stage.setTitle("Login");
            stage.close();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (IOException ex) {
            logger.error("Error in going back to the Login Scene!", ex);
        }
    }
    /**
     * Initializes the {@code UserDAO} with a new instance.
     * @see #ud
     * @see UserDAOFactory
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ud = UserDAOFactory.getInstance().createUserDAO();
    }
    /**
     * Loads the Login Scene (goes back to it).
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    public void handleGoBack(ActionEvent actionEvent) {
        backToLogin(false);
    }
}
