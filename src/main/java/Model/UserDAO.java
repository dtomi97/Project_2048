package Model;

import java.util.List;
/**
 * Interface for accesing {@code User} data.
 */
public interface UserDAO {
    /**
     * Creates a new {@code User} object.
     * @param username the username of the new user
     * @param password the password of the new user
     */
    public void createUser(String username, String password);
    /**
     * Returns a list of {@code User} with the given username.
     * @param username the name of the user to search for
     * @return a list of {@code User}
     */
    public List<User> find(String username);
    /**
     * Deletes a given {@code User} from the database.
     * @param u the user to delete
     */
    public void deleteUser(User u);

}
