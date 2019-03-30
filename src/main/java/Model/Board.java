package Model;

/**
 * Class representing a {@code Game}'s board.
 */

public final class Board {
    /**
     * The score of the {@code Game} played on this board.
     */
    private long score;
    /**
     * The Tiles of this board.
     */
    public Tile[][] Tiles;
    /**
     * Constructs a {@code Board} object.
     *
     * @param boardSize the size of the board
     */
    public Board(int boardSize){
        score=0;
        Tiles=new Tile[boardSize][boardSize];
        for (int i = 0; i < Tiles.length; i++) {
            for (int j = 0; j < Tiles.length; j++) {
                Tiles[i][j] = new Tile();
            }
        }
    }

    /**
     * Returns the score of the game played on this board.
     *
     * @return the score of the game.
     */
    public long GetScore() {
        return score;
    }
    /**
     * Sets the score of the game played on this board.
     *
     * @param score the score of the game
     */
    public void SetScore(long score){
        this.score=score;
    }
    /**
     * Returns this board's Tiles.
     *
     * @return this board's Tiles
     */
    public  Tile[][] GetBoard() {
        return Tiles;
    }
    /**
     * Returns the size of this board.
     *
     * @return the size of this board
     */
    public int Size(){
        return Tiles.length;
    }

}
