package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for controlling the Choose Game Mode Scene's actions.
 */
public class ChooseGameModeSceneController {

    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(ChooseGameModeSceneController.class);
    /**
     * 3x3 game start button.
     */
    @FXML
    private Button btn3x3;

    /**
     * Starts a new game with a 3x3 size board.
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    public void handleGameStartAction3x3(ActionEvent actionEvent) {
        Game game = new Game(3, null);
        Stage gameStage = (Stage) btn3x3.getScene().getWindow();
        gameStage.close();
        game.start(gameStage);
        logger.info("The user started a 3x3 game.");
    }
    /**
     * Starts a new game with a 4x4 size board.
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    public void handleGameStartAction4x4(ActionEvent actionEvent) {
        Game game = new Game(4, null);
        Stage gameStage = (Stage) btn3x3.getScene().getWindow();
        gameStage.close();
        game.start(gameStage);
        logger.info("The user started a 4x4 game.");
    }
    /**
     * Starts a new game with a 5x5 size board.
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    public void handleGameStartAction5x5(ActionEvent actionEvent) {
        Game game = new Game(5, null);
        Stage gameStage = (Stage) btn3x3.getScene().getWindow();
        gameStage.close();
        game.start(gameStage);
        logger.info("User started a 5x5 game.");
    }
    /**
     * Starts a new game with an 8x8 size board.
     * @param actionEvent the event that occurred.
     * @see javafx.event.ActionEvent
     */
    public void handleGameStartAction8x8(ActionEvent actionEvent) {
        Game game = new Game(8, null);
        Stage gameStage = (Stage) btn3x3.getScene().getWindow();
        gameStage.close();
        game.start(gameStage);
        logger.info("User started a 8x8 game.");
    }
}
