package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.UserDAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The Main class implements an application that
 * is able to start a 2048 game after logging in.
 */
public class Main extends Application {

    /**
     * The logger of this class.
     */
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * The main stage of this application.
     */
    private static Stage mainStage;
    /**
     * Current User of the application.
     */
    public static String currentUser=null;
    /**
     * The current user's game will be saved to this file.
     */
    public static String savefile=null;

    /**
     * Sets the main stage of the application.
     * @param primaryStage set the main stage to this
     */
    public static void SetPrimaryStage(Stage primaryStage){
        mainStage=primaryStage;
    }

    /**
     * Loads the Login Scene upon start.
     */
    @Override
    public void start(Stage primaryStage) {

        SetPrimaryStage(primaryStage);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
        } catch (IOException e) {
            logger.error("Error in loading the Login Scene!", e);
        }
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e->closeApp());

    }
    /**
     * Starts the application.
     * @param args arguments to start the application with
     */
    public static void main(String[] args) { launch(args); }
    /**
     * Cleans up the connection pull upon closing the app.
     */
    public static void closeApp() {
        UserDAOFactory.getInstance().close();
    }
    /**
     * Creates a {@code Main} object.
     */
    public Main(){}

}
