package Model;

import Controller.Main;
import Controller.StartOrLoadGameSceneController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import View.GameViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Class representing a game.
 */

public class Game extends Application {
    /**
     * The logger of this class.
     */
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    /**
     * The board size of this game.
     */
    private int boardSize;
    /**
     * Determines if this application is closed.
     */
    private boolean over=false;
    /**
     * Determines if this game has been saved.
     */
    private boolean saved = false;
    /**
     * A previous {@code Game} to load (if it is null a new game will be started).
     */
    private GameLoader loadthis = null;
    /**
     * Determines if this game is over.
     */
    private boolean gameOver=false;
    /**
     * The board of this game.
     */
    static Board board;
    /**
     * The {@code GameManager} of this game.
     * @see GameManager
     */
    private GameManager manager;
    /**
     * The {@code GameViewer} of this game.
     * @see GameViewer
     */
    private GameViewer viewer;
    /**
     * The {@code Writer} of this game.
     * @see Writer
     */
    private Writer writer;
    /**
     * Updates the game's view and actions.
     */
    private void Update() {

        if(!manager.thereIsSpace) {
            gameOver = manager.CheckForGameOver(board);
        }
        if(gameOver) {
            if(!saved){
                writer.WriteScore(board.GetScore(), board.Size());
                saved=true;
            }
            manager.ResetGame();
        }
        if(!gameOver) {
            saved = false;
            manager.Movement();
        }

        viewer.DrawBoard();

        viewer.DisplayScore();

        if (gameOver) {
            viewer.DisplayEndOfGameScreen();
        }

    }
    /**
     * Constructs a {@code Game} object.
     * @param boardSize the size of this game's {@code Board}
     * @param loadthis {@code GameLoader} object to load a previous game from (if null a new game will be started)
     */
    public Game(int boardSize, GameLoader loadthis){
        this.boardSize=boardSize;
        if(loadthis!=null)
            this.loadthis=loadthis;
    }
    /**
     * Starts this game.
     * @param gameStage the stage this game will be displayed to.
     */
    @Override
    public void start(Stage gameStage) {
        int winWidth=boardSize*110, winHeight=boardSize*110;
        Group root= new Group();
        gameStage.setTitle("Project_2048");
        Scene gameScene = new Scene( root );
        gameStage.setScene( gameScene );
        gameStage.sizeToScene();

        Canvas canvas = new Canvas( winWidth, winHeight );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        manager = new GameManager(gameScene, boardSize, loadthis);

        viewer = new GameViewer(gc, winWidth, winHeight, manager);

        writer = new Writer();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                Update();
                if(over)
                    this.stop();
            }
        }.start();

        gameStage.setOnCloseRequest(e-> {
            over=true;
            if(!gameOver)
                writer.SaveGame(board);
            else{
                try {
                    Path pathToSave = FileSystems.getDefault().getPath(Main.savefile);
                    Files.deleteIfExists(pathToSave);
                }
                catch (Exception e1) {
                    logger.error("Error in finding the delete-able save file! ", e);
                }
            }
            StartOrLoadGameSceneController.goBack();
        });
        gameStage.setResizable(false);
        gameStage.show();
        gameStage.sizeToScene();

    }
}
