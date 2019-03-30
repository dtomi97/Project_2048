package Model;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Class providing a manager for a {@code Game} object.
 */

public class GameManager {
    /**
     * Determines if the board has changed.
     */
    private boolean somethingChanged;
    /**
     * Determines if the pressed key is released.
     */
    private boolean keyReleased;
    /**
     * Determines if a tile can be added to the managed game's {@code Board}.
     */
    public boolean canAddTile;
    /**
     * Determines if a there is space on the managed game's {@code Board}.
     */
    public boolean thereIsSpace = true;
    /**
     * The pressed keys at the moment.
     */
    private HashSet<String> atmActiveKeys;
    /**
     * The managed game's {@code Board}.
     */
    public Board board;
    /**
     * Prepares the managed game's action handlers.
     * @param gameScene the scene to prepare the action handlers for
     */
    private void PrepareActionHandlers(Scene gameScene) {
        atmActiveKeys = new HashSet<>();
        gameScene.setOnKeyPressed(event -> atmActiveKeys.add(event.getCode().toString()));
        gameScene.setOnKeyReleased(event -> atmActiveKeys.remove(event.getCode().toString()));
    }
    /**
     * Checks if the pressed key has been released.
     */
    private void CheckIfKeyReleased() {
        if (!atmActiveKeys.contains("LEFT") && !atmActiveKeys.contains("RIGHT") && !atmActiveKeys.contains("UP") && !atmActiveKeys.contains("DOWN") && !atmActiveKeys.contains("ESCAPE")) {
            keyReleased = true;
        }
    }
    /**
     * Adds a new tile to a {@code Board} if there is space on it.
     * @param board the {@code Board} to add a new tile to.
     */
    public void addTile(Board board) {
        List<Integer[]> list = new ArrayList<>();
        for (int i = 0; i < board.Size(); i++) {
            for (int j = 0; j < board.Size(); j++) {
                if (board.Tiles[i][j].IsEmpty())
                    list.add(new Integer[]{i, j});
            }
        }
        if (list.size() != 0) {
            int index = (int) (Math.random() * list.size());
            Integer[] position = list.get(index);
            int tileValue = Math.random() < 0.9 ? 2 : 4;
            board.Tiles[position[0]][position[1]] = new Tile(tileValue);
            board.Tiles[position[0]][position[1]].justSpawned = true;
        }
    }
    /**
     * Returns if there is space on a {@code Board}.
     * @param board the {@code Board} to check for space on.
     * @return is there space on this {@code Board}.
     */
    public boolean CheckForSpace(Board board) {
        for (int i = 0; i<board.Size(); i++){
            for (int j = 0; j < board.Size(); j++) {
                if (board.Tiles[i][j].IsEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns if the game is over on a {@code Board}.
     * @param board the {@code Board} to check if the game is over on
     * @return is the game over on this {@code Board}
     */
    public boolean CheckForGameOver(Board board) {
        for (int i = 0; i < board.Size(); i++) {
            for (int j = 0; j < board.Size(); j++) {
                if (board.Tiles[i][j].IsEmpty())
                    return false;
                int iPlus = i + 1, iMinus = i - 1, jPlus = j + 1, jMinus = j - 1;
                if (iPlus != board.Size() && board.Tiles[i][j].GetValue() == board.Tiles[iPlus][j].GetValue())
                    return false;
                if (iMinus != -1 && board.Tiles[i][j].GetValue() == board.Tiles[iMinus][j].GetValue())
                    return false;
                if (jPlus != board.Size() && board.Tiles[i][j].GetValue() == board.Tiles[i][jPlus].GetValue())
                    return false;
                if (jMinus != -1 && board.Tiles[i][j].GetValue() == board.Tiles[i][jMinus].GetValue())
                    return false;
            }
        }
        return true;
    }
    /**
     * Brings up all of a {@code Board}'s tiles.
     * @param board the {@code Board} to bring up the tiles on
     */
    public void SortUp(Board board) {

        for (int j = 0; j < board.Size(); j++) {
            for (int i = 0; i < board.Size() - 1; i++) {
                if (board.Tiles[i][j].IsEmpty()) {
                    for (int k = i; k < board.Size(); k++) {
                        if (!board.Tiles[k][j].IsEmpty()) {
                            board.Tiles[i][j] = new Tile(board.Tiles[k][j].GetValue());
                            board.Tiles[i][j].merged = board.Tiles[k][j].merged;
                            board.Tiles[i][j].sliding = true;
                            if (board.Tiles[k][j].sliding) {
                                board.Tiles[i][j].SetMovement(board.Tiles[k][j].movingFromTo[0], board.Tiles[k][j].movingFromTo[1], i, j);
                            } else {
                                board.Tiles[i][j].SetMovement(k, j, i, j);
                            }
                            board.Tiles[k][j] = new Tile();
                            somethingChanged = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     *  Brings up all of a {@code Board}'s tiles and
     *  merges tiles with the same values, next to each other, in the same column, upwards on this {@code Board}.
     * 	@param board the {@code Board} to check and merge tiles on
     */
    public void Up(Board board) {

        SortUp(board);

        for (int i = 0; i < board.Size() - 1; i++) {
            for (int j = 0; j < board.Size(); j++) {
                if (board.Tiles[i][j].GetValue() == board.Tiles[i + 1][j].GetValue() && !board.Tiles[i][j].IsEmpty() && board.Tiles[i][j].GetValue() != 2048) {
                    board.Tiles[i][j] = new Tile(board.Tiles[i][j].GetValue() + board.Tiles[i + 1][j].GetValue());
                    board.Tiles[i][j].merged = true;
                    board.Tiles[i][j].sliding = board.Tiles[i + 1][j].sliding;
                    board.SetScore(board.GetScore() + board.Tiles[i][j].GetValue());
                    if (board.Tiles[i][j].sliding) {
                        board.Tiles[i][j].SetMovement(board.Tiles[i + 1][j].movingFromTo[0], board.Tiles[i + 1][j].movingFromTo[1], board.Tiles[i + 1][j].movingFromTo[2], board.Tiles[i + 1][j].movingFromTo[3]);
                        board.Tiles[i][j].preValue = board.Tiles[i][j].GetValue() / 2;
                        board.Tiles[i][j].willMergeAfterSlide = true;
                    }
                    board.Tiles[i + 1][j] = new Tile();
                    somethingChanged = true;
                }
            }
        }

        SortUp(board);

        if (somethingChanged) {
            canAddTile = true;
            somethingChanged = false;
        }
    }
    /**
     * Brings down all of a {@code Board}'s tiles.
     * @param board the {@code Board} to bring down the tiles on
     */
    public void SortDown(Board board) {

        for (int j = 0; j < board.Size(); j++) {
            for (int i = board.Size() - 1; i > 0; i--) {
                if (board.Tiles[i][j].IsEmpty()) {
                    for (int k = i; k >= 0; k--) {
                        if (!board.Tiles[k][j].IsEmpty()) {
                            board.Tiles[i][j] = new Tile(board.Tiles[k][j].GetValue());
                            board.Tiles[i][j].merged = board.Tiles[k][j].merged;
                            board.Tiles[i][j].sliding = true;
                            if (board.Tiles[k][j].sliding) {
                                board.Tiles[i][j].SetMovement(board.Tiles[k][j].movingFromTo[0], board.Tiles[k][j].movingFromTo[1], i, j);
                            } else {
                                board.Tiles[i][j].SetMovement(k, j, i, j);
                            }
                            board.Tiles[k][j] = new Tile();
                            somethingChanged = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     *  Brings down all of a {@code Board}'s tiles and
     *  merges tiles with the same values, next to each other, in the same column, downwards on this {@code Board}.
     * 	@param board the {@code Board} to check and merge tiles on
     */
    public void Down(Board board) {

        SortDown(board);

        for (int i = board.Size() - 1; i > 0; i--) {
            for (int j = 0; j < board.Size(); j++) {
                if (board.Tiles[i][j].GetValue() == board.Tiles[i - 1][j].GetValue() && !board.Tiles[i][j].IsEmpty() && board.Tiles[i][j].GetValue() != 2048) {
                    board.Tiles[i][j] = new Tile(board.Tiles[i][j].GetValue() + board.Tiles[i - 1][j].GetValue());
                    board.Tiles[i][j].merged = true;
                    board.Tiles[i][j].sliding = board.Tiles[i - 1][j].sliding;
                    board.SetScore(board.GetScore() + board.Tiles[i][j].GetValue());
                    if (board.Tiles[i][j].sliding) {
                        board.Tiles[i][j].SetMovement(board.Tiles[i - 1][j].movingFromTo[0], board.Tiles[i - 1][j].movingFromTo[1], board.Tiles[i - 1][j].movingFromTo[2], board.Tiles[i - 1][j].movingFromTo[3]);
                        board.Tiles[i][j].preValue = board.Tiles[i][j].GetValue() / 2;
                        board.Tiles[i][j].willMergeAfterSlide = true;
                    }
                    board.Tiles[i - 1][j] = new Tile();
                    somethingChanged = true;
                }
            }
        }

        SortDown(board);

        if (somethingChanged) {
            canAddTile = true;
            somethingChanged = false;
        }

    }
    /**
     * Brings left all of a {@code Board}'s tiles.
     * @param board the {@code Board} to bring left the tiles on
     */
    public void SortLeft(Board board) {

        for (int i = 0; i < board.Size(); i++) {
            for (int j = 0; j < board.Size() - 1; j++) {
                if (board.Tiles[i][j].IsEmpty()) {
                    for (int k = j; k < board.Size(); k++) {
                        if (!board.Tiles[i][k].IsEmpty()) {
                            board.Tiles[i][j] = new Tile(board.Tiles[i][k].GetValue());
                            board.Tiles[i][j].merged = board.Tiles[i][k].merged;
                            board.Tiles[i][j].sliding = true;
                            if (board.Tiles[i][k].sliding) {
                                board.Tiles[i][j].SetMovement(board.Tiles[i][k].movingFromTo[0], board.Tiles[i][k].movingFromTo[1], i, j);
                            } else {
                                board.Tiles[i][j].SetMovement(i, k, i, j);
                            }
                            board.Tiles[i][k] = new Tile();
                            somethingChanged = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     *  Brings left all of a {@code Board}'s tiles and
     *  merges tiles with the same values, next to each other, in the same row, leftwards on this {@code Board}.
     * 	@param board the {@code Board} to check and merge tiles on
     */
    public void Left(Board board) {

        SortLeft(board);

        for (int i = 0; i < board.Size(); i++) {
            for (int j = 0; j < board.Size() - 1; j++) {
                if (board.Tiles[i][j].GetValue() == board.Tiles[i][j + 1].GetValue() && !board.Tiles[i][j].IsEmpty() && board.Tiles[i][j].GetValue() != 2048) {
                    board.Tiles[i][j] = new Tile(board.Tiles[i][j].GetValue() + board.Tiles[i][j + 1].GetValue());
                    board.Tiles[i][j].merged = true;
                    board.Tiles[i][j].sliding = board.Tiles[i][j + 1].sliding;
                    board.SetScore(board.GetScore() + board.Tiles[i][j].GetValue());
                    if (board.Tiles[i][j].sliding) {
                        board.Tiles[i][j].SetMovement(board.Tiles[i][j + 1].movingFromTo[0], board.Tiles[i][j + 1].movingFromTo[1], board.Tiles[i][j + 1].movingFromTo[2], board.Tiles[i][j + 1].movingFromTo[3]);
                        board.Tiles[i][j].preValue = board.Tiles[i][j].GetValue() / 2;
                        board.Tiles[i][j].willMergeAfterSlide = true;
                    }
                    board.Tiles[i][j + 1] = new Tile();
                    somethingChanged = true;
                }
            }
        }

        SortLeft(board);

        if (somethingChanged) {
            canAddTile = true;
            somethingChanged = false;
        }

    }
    /**
     * Brings right all of a {@code Board}'s tiles.
     * @param board the {@code Board} to bring right the tiles on
     */
    public void SortRight(Board board) {

        for (int i = 0; i < board.Size(); i++) {
            for (int j = board.Size() - 1; j > 0; j--) {
                if (board.Tiles[i][j].IsEmpty()) {
                    for (int k = j; k >= 0; k--) {
                        if (!board.Tiles[i][k].IsEmpty()) {
                            board.Tiles[i][j] = new Tile(board.Tiles[i][k].GetValue());
                            board.Tiles[i][j].merged = board.Tiles[i][k].merged;
                            board.Tiles[i][j].sliding = true;
                            if (board.Tiles[i][k].sliding) {
                                board.Tiles[i][j].SetMovement(board.Tiles[i][k].movingFromTo[0], board.Tiles[i][k].movingFromTo[1], i, j);
                            } else {
                                board.Tiles[i][j].SetMovement(i, k, i, j);
                            }
                            board.Tiles[i][k] = new Tile();
                            somethingChanged = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     *  Brings right all of a {@code Board}'s tiles and
     *  merges tiles with the same values, next to each other, in the same row, rightwards on this {@code Board}.
     * 	@param board the {@code Board} to check and merge tiles on
     */
    public void Right(Board board) {

        SortRight(board);

        for (int i = 0; i < board.Size(); i++) {
            for (int j = board.Size() - 1; j > 0; j--) {
                if (board.Tiles[i][j].GetValue() == board.Tiles[i][j - 1].GetValue() && !board.Tiles[i][j].IsEmpty() && board.Tiles[i][j].GetValue() != 2048) {
                    board.Tiles[i][j] = new Tile(board.Tiles[i][j].GetValue() + board.Tiles[i][j - 1].GetValue());
                    board.Tiles[i][j].merged = true;
                    board.Tiles[i][j].sliding = board.Tiles[i][j - 1].sliding;
                    board.SetScore(board.GetScore() + board.Tiles[i][j].GetValue());
                    if (board.Tiles[i][j].sliding) {
                        board.Tiles[i][j].SetMovement(board.Tiles[i][j - 1].movingFromTo[0], board.Tiles[i][j - 1].movingFromTo[1], board.Tiles[i][j - 1].movingFromTo[2], board.Tiles[i][j - 1].movingFromTo[3]);
                        board.Tiles[i][j].preValue = board.Tiles[i][j].GetValue() / 2;
                        board.Tiles[i][j].willMergeAfterSlide = true;
                    }
                    board.Tiles[i][j - 1] = new Tile();
                    somethingChanged = true;
                }
            }
        }

        SortRight(board);

        if (somethingChanged) {
            canAddTile = true;
            somethingChanged = false;
        }

    }
    /**
     * Resets the managed {@code Game} if the ESCAPE key is pressed.
     */
    public void ResetGame() {
        if (atmActiveKeys.contains(("ESCAPE")) && keyReleased) {
            Game.board = new Board(Game.board.Size());
            this.board=Game.board;
            addTile(board);
            addTile(board);
            keyReleased = false;
        }
        CheckIfKeyReleased();
    }
    /**
     * Brings the managed {@code Game}'s tiles into a direction and merges them if necessary
     * based on which of the UP or DOWN or LEFT or RIGHT keys is being pressed.
     * (Players can only press one key at a time)
     */
    public void Movement() {
        if (atmActiveKeys.contains("UP") && keyReleased) {
            Up(board);
            keyReleased = false;
        }
        if (atmActiveKeys.contains("DOWN") && keyReleased) {
            Down(board);
            keyReleased = false;
        }
        if (atmActiveKeys.contains("LEFT") && keyReleased) {
            Left(board);
            keyReleased = false;
        }
        if (atmActiveKeys.contains("RIGHT") && keyReleased) {
            Right(board);
            keyReleased = false;
        }
        CheckIfKeyReleased();
    }
    /**
     * Constructs a {@code GameManager} object
     * and also initializes the managed {@code Game}'s {@code Board}
     * and prepares it's action handlers.
     * @param gameScene the {@code Scene} to prepare the action handlers on
     * @param boardSize the size of the managed {@code Game}'s {@code Board}
     * @param loadthis the game to load if wanted (put null value for new game)
     */
     GameManager(Scene gameScene, int boardSize, GameLoader loadthis){
        PrepareActionHandlers(gameScene);
        this.keyReleased = true;
        if(loadthis==null) {
            Game.board = new Board(boardSize);
            this.board = Game.board;
            addTile(board);
            addTile(board);
        } else {
            Game.board = new Board(loadthis.boardSize);
            this.board = Game.board;
            board.SetScore(loadthis.score);
            board.Tiles = loadthis.Tiles;
        }
        this.canAddTile=false;
    }
    /**
     * Constructs a {@code GameManager} object with a board to use it's game managing methods on.
     * (used for unit tests)
     * @param board the test {@code Board}.
     */
    public GameManager(Board board){
        this.keyReleased = true;
        this.canAddTile = true;
        this.board = board;
    }
    /**
     * Constructs a {@code GameManager} object to use it's board managing methods.
     * (used for unit tests)
     */
    public GameManager(){
        this.keyReleased = true;
        this.canAddTile = true;
    }

}
