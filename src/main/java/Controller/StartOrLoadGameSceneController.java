package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Model.Game;
import Model.GameLoader;
import Model.Reader;
import View.Leaderboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to handle the Start or Load Game Scene's actions.
 */
public class StartOrLoadGameSceneController implements Initializable {
    /**
     * The logger of this class.
     */
    private static Logger logger = LoggerFactory.getLogger(StartOrLoadGameSceneController.class);
    /**
     * The Reader of this class.
     * @see Reader
     */
    private Reader reader;
    /**
     * The folder to data to.
     */
    private File data;
    /**
     * The first save slot of the {@code User}.
     */
    @FXML
    public Button slot1;
    /**
     * The second save slot of the {@code User}.
     */
    @FXML
    public Button slot2;
    /**
     * The third save slot of the {@code User}.
     */
    @FXML
    public Button slot3;

    /**
     * Starts a new {@code Game} or loads one depending on if the slot's save file file exists (Save1.xml).
     * @param event the event that occurred.
     * @see javafx.event.ActionEvent
     */
    @FXML
    public void HandleSaveSlot1(javafx.event.ActionEvent event) {
        Main.savefile = data + File.separator + Main.currentUser + File.separator +"Save1.xml";
        File f1 = new File(Main.savefile);
        if (f1.exists() && !f1.isDirectory()) {
            GameLoader loadthis = reader.LoadGame(Main.savefile);
            Game game = new Game(loadthis.boardSize, loadthis);
            Stage gameStage = (Stage) slot1.getScene().getWindow();
            gameStage.close();
            game.start(gameStage);
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseGameModeScene.fxml"));
                root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());
                Stage chooseGameStage = (Stage) slot1.getScene().getWindow();
                chooseGameStage.setTitle("Choose game mode");
                Scene scene2 = new Scene(root);
                chooseGameStage.close();
                chooseGameStage.setScene(scene2);
                chooseGameStage.setOnCloseRequest(e -> { goBack(); });
                chooseGameStage.show();
                chooseGameStage.centerOnScreen();
            } catch (IOException e) {
                logger.error("Error in loading the Choose Game Mode Scene (Save slot 1)!", e);
            }
        }
    }

    /**
     * Starts a new {@code Game} or loads one depending on if the slot's save file file exists (Save2.xml).
     * @param event the event that occurred.
     * @see javafx.event.ActionEvent
     */
    @FXML
    public void HandleSaveSlot2(javafx.event.ActionEvent event) {
        Main.savefile = data + File.separator + Main.currentUser + File.separator + "Save2.xml";
        File f = new File(Main.savefile);
        if(f.exists() && !f.isDirectory()) {
            GameLoader loadthis = reader.LoadGame(Main.savefile);
            Game game = new Game(loadthis.boardSize, loadthis);
            Stage gameStage = (Stage) slot1.getScene().getWindow();
            gameStage.close();
            game.start(gameStage);
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseGameModeScene.fxml"));
                root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());
                Stage chooseGameStage = (Stage) slot1.getScene().getWindow();
                chooseGameStage.setTitle("Choose game mode");
                Scene scene2 = new Scene(root);
                chooseGameStage.close();
                chooseGameStage.setScene(scene2);
                chooseGameStage.setOnCloseRequest(e -> { goBack(); });
                chooseGameStage.show();
                chooseGameStage.centerOnScreen();
            } catch (Exception e1) {
                logger.error("Error in loading the Choose Game Mode Scene (Save slot 2)!", e1);
            }
        }
    }
    /**
     * Starts a new {@code Game} or loads one depending on if the slot's save file file exists (Save3.xml).
     * @param event the event that occurred.
     * @see javafx.event.ActionEvent
     */
    @FXML
    private void HandleSaveSlot3(javafx.event.ActionEvent event) {
        Main.savefile = data + File.separator + Main.currentUser + File.separator + "Save3.xml";
        File f = new File(Main.savefile);
        if(f.exists() && !f.isDirectory()) {
            GameLoader loadthis = reader.LoadGame(Main.savefile);
            Game game = new Game(loadthis.boardSize, loadthis);
            Stage gameStage = (Stage) slot1.getScene().getWindow();
            gameStage.close();
            game.start(gameStage);
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseGameModeScene.fxml"));
                root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());
                Stage chooseGameStage = (Stage) slot1.getScene().getWindow();
                chooseGameStage.setTitle("Choose game mode");
                Scene scene2 = new Scene(root);
                chooseGameStage.close();
                chooseGameStage.setScene(scene2);
                chooseGameStage.setOnCloseRequest(e -> { goBack(); });
                chooseGameStage.show();
                chooseGameStage.centerOnScreen();
            } catch (IOException e1) {
                logger.error("Error in loading the Choose Game Mode Scene (Save slot 3)!", e1);
            }
        }
    }

    /**
     * Loads the Login Scene (goes back to it).
     */
    public static void goBack() {
        try {
            File data = new File(System.getProperty("user.home") + File.separator + "Project_2048" + File.separator + "userdata");

            Parent root;
            FXMLLoader fl = new FXMLLoader(StartOrLoadGameSceneController.class.getResource("/fxml/StartOrLoadGameScene.fxml"));
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

            root.getStylesheets().add(StartOrLoadGameSceneController.class.getResource("/stylesheets/styling.css").toExternalForm());

            Stage primaryStage = new Stage();
            Main.SetPrimaryStage(primaryStage);
            primaryStage.close();
            primaryStage.setTitle("Start or load game");
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();

            Scene scene2 = new Scene(root);
            primaryStage.setScene(scene2);
            primaryStage.show();

            primaryStage.setOnCloseRequest(e -> Main.closeApp());
        } catch (IOException e1) {
            logger.error("Error in going back to the Start or Load Game Scene", e1);
        }
    }

    /**
     * Loads the Leader Board.
     * @param event the event that occurred.
     * @see javafx.event.ActionEvent
     */
    @FXML
    public void HandleLeaderBoard(javafx.event.ActionEvent event) {
        Stage scoreStage = (Stage) slot1.getScene().getWindow();
        scoreStage.close();
        Leaderboard leaderboard=new Leaderboard(scoreStage);
        leaderboard.drawLeaderboard();
        scoreStage.sizeToScene();

        scoreStage.setOnCloseRequest(e-> {
            goBack();
        });
    }
    /**
     * Loads the Login Scene.
     * @param event the event that occurred
     */
    @FXML
    private void HandleGoBackToLogIn(javafx.event.ActionEvent event) {
        try {
            Stage stage = (Stage) slot1.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
            root.getStylesheets().add(getClass().getResource("/stylesheets/styling.css").toExternalForm());

            Scene scene = new Scene(root);

            Main.currentUser=null;
            Main.savefile=null;
            stage.close();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            logger.error("Error in going back to the Login Scene", ex);
        }
    }
    /**
     * Initializes the {@code Reader} of the save files.
     * @see Reader
     * @see #reader
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = new File(System.getProperty("user.home") + File.separator + "Project_2048" + File.separator + "userdata");
        if(!data.exists())
            data.mkdirs();
        this.reader=new Reader();
    }
}
