package Model;

/**
 * Class representing a saved {@code Game} to be loaded.
 */
public class GameLoader{
    /**
     * The Tiles of this game's {@code Board}.
     */
    public Tile[][] Tiles;
    /**
     * Score of this game.
     */
    public long score;
    /**
     * Size of this game's board.
     */
    public int boardSize;
    /**
     * Constructs a {@code GameLoader} object.
     * @param Tiles the tiles of the loadable game's {@code Board}.
     * @param score the score of the loadable game.
     * @param boardSize the size of the loadable game's {@code Board}.
     * @see Board
     */
    GameLoader(Tile[][] Tiles, long score, int boardSize){
        this.Tiles = Tiles;
        this.score=score;
        this.boardSize = boardSize;
    }
}
