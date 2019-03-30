package View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import Model.GameManager;
import Model.Tile;
/**
 * Class for displaying and updating the game's view.
 */
public class GameViewer {
    /**
     * Determines if the operation system is windows.
     */
    private boolean OS = System.getProperty("os.name").contains("Windows");
    /**
     * The border of the displayable {@code Game}'s {@code Board}.
     */
    private int BOARD_BORDER;
    /**
     * The {@code GraphicsContext} to draw the game to.
     */
    private GraphicsContext gc;
    /**
     * The width of the displayable {@code Game}'s window.
     */
    private int winWidth;
    /**
     * The height of the displayable {@code Game}'s window.
     */
    private int winHeight;
    /**
     * The board size of the displayable {@code Game}.
     */
    private int boardSize;
    /**
     * The {@code GameManager} of the displayable {@code Game}.
     * @see GameManager
     */
    private GameManager manager;
    /**
     * Constructs a {@code GameViewer} object to display a game with.
     * @param gc GraphicsContext of the game
     * @param winHeight height of the game's window
     * @param winWidth width of the game's window
     * @param manager the GameManager of the game
     * @see GraphicsContext
     * @see GameManager
     */
    public GameViewer(GraphicsContext gc, int winWidth, int winHeight, GameManager manager){
        this.gc=gc;
        this.winWidth=winWidth;
        this.winHeight=winHeight;
        this.manager=manager;
        this.boardSize=manager.board.Size();
        this.BOARD_BORDER = (winWidth - (70 + 80 * (boardSize - 1))) / 2;
    }
    /**
     * Displays the current score of the game.
     */
    public void DisplayScore() {
	int fontSize = OS ? 30 : 24;
        String s = String.valueOf(manager.board.GetScore());
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, fontSize);
        gc.setFont(theFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Score:", BOARD_BORDER, BOARD_BORDER - 10);
        gc.fillText(s, BOARD_BORDER + 85, BOARD_BORDER - 10);
    }
    /**
     * Displays the end of game screen.
     */
    public void DisplayEndOfGameScreen() {
        gc.setFill(Color.color(1.0000, 1.0000, 1.0000, 0.5));
        gc.fillRect(0, 0, winWidth, winHeight);
        int fontSize1, fontSize2;
        if(OS) {
            fontSize1 = 48;
            fontSize2 = 64;
        } else {
            fontSize1 = 38;
            fontSize2 = 54;
        }
        int size = boardSize < 5 ? fontSize1 : fontSize2;
        gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, size));
        gc.setFill(Color.color(0.3058, 0.5450, 0.7921));
        gc.fillText("Game over!", winWidth / 2 - size * 2 - 20, winHeight / 2 - size);
        gc.setFont(Font.font("Times New Roman", FontPosture.REGULAR, size / 2));
        gc.setFill(Color.color(0.5019, 0.5019, 0.5019));
        gc.fillText("Press ESC to play again", winWidth / 2 - size * 2 - 20, winHeight / 2 + size);
    }
    /**
     * Displays the board of the game.
     */
    public void DrawBoard() {
        gc.setFill(Color.color(0.7333, 0.6784, 0.6274));
        gc.fillRect(0, 0, winWidth, winHeight);

        int x = BOARD_BORDER, y = BOARD_BORDER;

        for (int i = 0; i < manager.board.Size(); i++) {
            for (int j = 0; j < manager.board.Size(); j++) {
                if (!manager.board.Tiles[i][j].sliding || manager.board.Tiles[i][j].willMergeAfterSlide)
                    DrawTile(gc, manager.board.Tiles[i][j], x, y);
                else
                    DrawTile(gc, new Tile(), x, y);
                x += 80;
            }
            x = BOARD_BORDER;
            y += 80;
        }


        x = BOARD_BORDER;
        y = BOARD_BORDER;

        boolean noneMoving = true;
        for (int i = 0; i < manager.board.Size(); i++) {
            for (int j = 0; j < manager.board.Size(); j++) {
                if (manager.board.Tiles[i][j].sliding) {
                    SlideAnimation(manager.board.Tiles[i][j], x, y, i);
                    noneMoving = false;
                }
                x += 80;
            }
            x = BOARD_BORDER;
            y += 80;
        }
        if (noneMoving && manager.canAddTile) {
            manager.addTile(manager.board);
            manager.thereIsSpace = manager.CheckForSpace(manager.board);
            manager.canAddTile = false;
        }

    }
    /**
     * Displays a tile's slide animation.
     * @param tile the sliding tile
     * @param x the X coordinate of the sliding tile
     * @param y the Y coordinate of the sliding tile
     * @param direction the direction this tile is moving to (vertical or horizontal)
     * @see Tile
     */
    private void SlideAnimation(Tile tile, float x, float y, int direction) {
        float prevX = BOARD_BORDER + tile.movingFromTo[1] * 80, prevY = BOARD_BORDER + tile.movingFromTo[0] * 80;
        if (direction == tile.movingFromTo[0]) {
            DrawTile(gc, tile, prevX + tile.movement, y);
            tile.movement += tile.movementChange;
            if (tile.movement < 0) {
                if (!tile.willMergeAfterSlide && prevX + tile.movement <= x) {
                    tile.sliding = false;
                } else if (tile.willMergeAfterSlide && prevX + tile.movement <= x + 80) {
                    tile.sliding = false;
                    tile.willMergeAfterSlide = false;
                }
            } else {
                if (!tile.willMergeAfterSlide && prevX + tile.movement >= x) {
                    tile.sliding = false;
                } else if (tile.willMergeAfterSlide && prevX + tile.movement >= x - 80) {
                    tile.sliding = false;
                    tile.willMergeAfterSlide = false;
                }
            }
        } else {
            DrawTile(gc, tile, x, prevY + tile.movement);
            tile.movement += tile.movementChange;
            if (tile.movement < 0) {
                if (!tile.willMergeAfterSlide && prevY + tile.movement <= y) {
                    tile.sliding = false;
                } else if (tile.willMergeAfterSlide && prevY + tile.movement <= y + 80) {
                    tile.sliding = false;
                    tile.willMergeAfterSlide = false;
                }
            } else {
                if (!tile.willMergeAfterSlide && prevY + tile.movement >= y) {
                    tile.sliding = false;
                } else if (tile.willMergeAfterSlide && prevY + tile.movement >= y - 80) {
                    tile.sliding = false;
                    tile.willMergeAfterSlide = false;
                }
            }
        }
    }
    /**
     * Displays a tile's spawn animation.
     * @param tile the spawning tile
     * @param x the X coordinate of the spawning tile
     * @param y the Y coordinate of the spawning tile
     * @see Tile
     */
    private void SpawnAnimation(Tile tile, float x, float y) {

        float xTemp = x + 35 - tile.spawnChange;
        float yTemp = y + 35 - tile.spawnChange;
        
        gc.fillRoundRect(xTemp, yTemp, 70 * tile.scaleSpawn, 70 * tile.scaleSpawn, 14, 14);
        if(OS) {
            tile.scaleSpawn += 0.1;
            tile.spawnChange += 3.5;
        } else {
            tile.scaleSpawn += 0.02;
            tile.spawnChange += 0.7;
        }
        if (tile.scaleSpawn >= 1)
            tile.justSpawned = false;
    }
    /**
     * Displays two tile's merge animation.
     * @param tile the merge's tile
     * @param x the X coordinate of the merged tile
     * @param y the Y coordinate of the merged tile
     * @see Tile
     */
    private void MergeAnimation(Tile tile, float x, float y) {
        float xTemp = x - tile.mergeChange;
        float yTemp = y - tile.mergeChange;
        gc.fillRoundRect(xTemp, yTemp, 70 * tile.scaleMerge, 70 * tile.scaleMerge, 14, 14);
        if(OS) {
            tile.scaleMerge += 0.02;
            tile.mergeChange += 0.7;
        } else {
            tile.scaleMerge += 0.004;
            tile.mergeChange += 0.14;
        }
        if (tile.scaleMerge >= 1.2)
            tile.merged = false;
    }
    /**
     * Displays a tile on the {@code Board}.
     * @param tile the tile to display.
     * @param x the X coordinate of the displayed tile
     * @param y the Y coordinate of the displayed tile
     * @param gc the {@code GraphicsContext} to draw the tile to
     * @see Tile
     * @see GraphicsContext
     */
    private void DrawTile(GraphicsContext gc, Tile tile, float x, float y) {
        if (tile.justSpawned) {
            gc.setFill(Color.color(0.8039, 0.7568, 0.7058));
            gc.fillRoundRect(x, y, 70, 70, 14, 14);
        }

        if (tile.willMergeAfterSlide)
            gc.setFill(tile.GetBackgroundColor(tile.preValue));
        else
            gc.setFill(tile.GetBackgroundColor(tile.GetValue()));

        if (tile.justSpawned && !tile.IsEmpty()) {
            SpawnAnimation(tile, x, y);
        } else if (tile.merged && !tile.sliding) {
            MergeAnimation(tile, x, y);
        } else {
            gc.fillRoundRect(x, y, 70, 70, 14, 14);
        }

        String s;
        if (tile.willMergeAfterSlide) {
            gc.setFill(tile.GetTextColor(tile.preValue));
            s = String.valueOf(tile.preValue);
        } else {
            gc.setFill(tile.GetTextColor(tile.GetValue()));
            s = String.valueOf(tile.GetValue());
        }
        int fontSize1, fontSize2, fontSize3;
        if(OS) {
            fontSize1=36;
            fontSize2=32;
            fontSize3=24;
        } else {
            fontSize1=30;
            fontSize2=26;
            fontSize3=18;
        }
        final int size = tile.GetValue() < 100 ? fontSize1 : tile.GetValue() < 1000 ? fontSize2 : fontSize3;
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, size);
        gc.setFont(theFont);

        int xT = 0, yT = 0;

        if(OS) {
            switch (size) {
                case 24:
                    xT = 11;
                    yT = 42;
                    break;
                case 32:
                    xT = 10;
                    yT = 45;
                    break;
                case 36:
                    if (tile.GetValue() < 16)
                        xT = 26;
                    else
                        xT = 16;
                    yT = 47;
                    break;
            }
        } else {
            switch (size) {
                case 18:
                    xT = 11;
                    yT = 42;
                    break;
                case 26:
                    xT = 10;
                    yT = 45;
                    break;
                case 30:
                    if (tile.GetValue() < 16)
                        xT = 26;
                    else
                        xT = 16;
                    yT = 47;
                    break;
            }
        }
        if (tile.GetValue() != 0 || tile.willMergeAfterSlide) {
            gc.fillText(s, x + xT, y + yT);
        }
    }
}
