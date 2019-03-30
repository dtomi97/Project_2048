package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Model.PlayerScore;
import Model.Reader;

import java.util.List;

/**
 * Class for displaying the leader board among the players.
 */
public class Leaderboard {
    /**
     * The stage to display the leader board to.
     */
    private Stage scoreStage;
    /**
     * The {@code Reader} of this object.
     * @see Reader
     */
    private Reader reader;
    /**
     * Draws a game mode's top 10 highest scores.
     * @param boardSize the game mode's board size
     * @param scoreType the type of scores to display the highest scores from (can be average or highest)
     * @param gc the {@code GraphicsContext} to draw these scores to.
     * @param x the X coordinate to start the drawing from
     * @param y the Y coordinate to start the drawing from
     */
    private void drawScores(int boardSize, String scoreType, GraphicsContext gc, int x, int y){
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, System.getProperty("os.name").contains("Windows") ? 20 : 14);
        gc.setFont(theFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Game mode " + boardSize + "x" + boardSize, x+75, y);
        String scorename = scoreType.equals("highscore") ? "Highscore" : "Avarage score";
        int indent = scoreType.equals("highscore") ? 105 : 85;
        gc.fillText(scorename, x+indent, y+25);
        List<PlayerScore> scores = reader.ReadUserScores(scoreType, boardSize);

        int listSize=scores.size() < 10 ? scores.size() : 10;
        if(scores.size()==0){
            gc.setFill(Color.RED);
            gc.fillText("No records yet!", x+85, y + 100);
        } else {
            for (int i=0; i<listSize; i++){
                gc.fillText(String.valueOf(i+1) + ".  " + scores.get(i).username + ":" , x, y+50+i*20);
                gc.fillText(scores.get(i).score, x+250, y+50+i*20);
            }
        }
    }
    /**
     * Displays all game mode's leader board.
     */
    public void drawLeaderboard(){
        Group root= new Group();
        scoreStage.setTitle("Leaderboard");
        Scene gameScene = new Scene( root );
        scoreStage.setScene( gameScene );

        Canvas canvas = new Canvas( 1500, 750 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.color(0.7333, 0.6784, 0.6274));
        gc.fillRect(0, 0, 1500, 750);

        int size=3;
        for(int i=0; i< 4; i++) {
            if(size==6)
                size=8;
            drawScores(size, "highscore", gc, i*375+30, 50);
            drawScores(size, "avgscore", gc, i*375+30, 350);
            size++;
        }

        scoreStage.show();
    }
    /**
     * Constructs a {@code Leaderboard} object.
     * @param scoreStage the stage to display the leader board to
     * @see Stage
     */
    public Leaderboard(Stage scoreStage){
        this.scoreStage=scoreStage;
        this.reader = new Reader();
    }

}
