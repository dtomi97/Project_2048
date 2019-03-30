package Model;

import javax.persistence.*;

/**
 * Class representing a User.
 */
@Entity
@Table (name="Users")
public class User {
    /**
     * The id of this user (it is Auto generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;
    /**
     * The username of this user.
     */
    private String username;
    /**
     * The password of this user.
     */
    private String password;
    /**
     * Constructs a {@code User} object.
     * @param username the username of this user
     * @param password the password of this user
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
    /**
     * Constructs a {@code User} object.
     */
    protected User() {
    }
    /**
     * Returns the id of this user.
     * @return the id of this user.
     */
    public short getId() {
        return id;
    }

    /**
     * Returns the username of this user.
     * @return the username of this user.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username of this user.
     * @param username the new username of this user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Returns the password of this user.
     * @return the password of this user.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password of this user.
     * @param password the new password of this user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
