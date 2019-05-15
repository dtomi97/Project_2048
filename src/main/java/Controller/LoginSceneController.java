package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Model.UserDAO;
import Model.UserDAOFactory;
import Model.Validator;
import View.Leaderboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import java.io.IOException;
/**
 * Class for controlling the Login Scene's actions.
 */
public class LoginSceneController implements Initializable {

    /**
     * The logger of this class.
     */
    private static Logger logger = LoggerFactory.getLogger(LoginSceneController.class);
    /**
     * The feed back label for giving back information.
     * @see Label
     */
    @FXML
    public Label feedbackLabel;
    /**
     * The button to start the authentication.
     */
    @FXML
    private Button LOGIN;
    /**
     * The {@code UserDAO} of this scene.
     * @see UserDAO
     */
    private UserDAO ud;
    /**
     * The authenticating {@code User}'s username.
     */
    @FXML
    private  TextField USERNAME;
    /**
     * The authenticating {@code User}'s password.
     */
    @FXML
    private PasswordField PASSWORD;

    /**
     * Loads the Start or Load Game Scene.
     */

    private void StartOrLoadGame(){
        try {
            File data = new File(System.getProperty("user.home") + File.separator + "Project_2048" + File.separator + "userdata");

            Stage stage = (Stage) USERNAME.getScene().getWindow();
            Parent root;
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/fxml/StartOrLoadGameScene.fxml"));
            root = fl.load();
            File f = new File(data + File.separator + Main.currentUser + File.separator + "Save1.xml");
            if(f.exists())
                fl.<StartOrLoadGameSceneController>getController().slot1.setText("Load Game");
            f = new File(data + File.separator + Main.currentUser + File.separator + "Save2.xml");
            if(f.exists())
                fl.<StartOrLoadGameSceneController>getController().slot2.setText("Load Game");
            f = new File(data + File.separator + Main.currentUser + File.separator + "Save3.xml");
            if(f.exists())
                fl.<StartOrLoadGameSceneController>getController().slot3.setText("Load Game");

            root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());

            Scene scene = new Scene(root);
            stage.close();
            stage.setTitle("Start or Load Game");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);

        } catch (IOException ex) {
            logger.error("Error in loading Start or Load Game Scene!", ex);
        }
    }

    /**
     * Loads the Register Scene.
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    @FXML
    public void handleRegisterBtn(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) LOGIN.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegisterScene.fxml"));
            root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());

            Scene scene = new Scene(root);
            stage.close();
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            logger.error("Error in loading the Register Scene!", ex);
        }
    }

    /**
     * Authenticates a {@code User}.
     * @param event the occurred event
     */
    @FXML
    private void handleLoginBtn(javafx.event.ActionEvent event){
        Validator v= new Validator(ud);
        if(v.loginValidate(USERNAME.getText(), PASSWORD.getText())){
            Main.currentUser=USERNAME.getText();
            StartOrLoadGame();
        }
        else
            feedbackLabel.setText("Wrong username or password!");
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
     * Loads the Login Scene.
     */
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(LoginSceneController.class.getResource("/fxml/LoginScene.fxml"));
            root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());
            Stage primaryStage = new Stage();
            Main.SetPrimaryStage(primaryStage);
            primaryStage.setTitle("Login");
            Scene scene2 = new Scene(root);
            primaryStage.close();
            primaryStage.setScene(scene2);
            primaryStage.show();
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();

            primaryStage.setOnCloseRequest(e -> Main.closeApp());
        } catch (Exception e1) {
            logger.error("Error in going back to the Login Scene!", e1);
        }
    }

    /**
     * Loads the Leader Board.
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    public void handleLeaderBoard(ActionEvent actionEvent) {
        Stage scoreStage = (Stage) LOGIN.getScene().getWindow();
        scoreStage.close();
        Leaderboard leaderboard=new Leaderboard(scoreStage);
        scoreStage.sizeToScene();
        leaderboard.drawLeaderboard();

        scoreStage.setOnCloseRequest(e-> {
            goBack();
        });
    }
}
