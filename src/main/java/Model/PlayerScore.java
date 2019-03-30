package Model;

/**
 * Class representing a user and one of his\her scores.
 */
public class PlayerScore {
    /**
     * This player's username.
     */
    public String username;
    /**
     * This player's score.
     */
    public String score;
    /**
     * Constructs a {@code PlayerScore} object.
     * @param username the username of this player
     * @param score the score of this player.
     */
    public PlayerScore(String username, String score){
        this.username=username;
        this.score=score;
    }
}