package Model;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
/**
 * Class to implement the {@code UserDAO} class's methods.
 */
public class UserDAOImpl implements UserDAO {
    /**
     * The entity manager of the {@code UserDAO}.
     */
    private EntityManager em;
    /**
     * Constructs a {@code UserDAO} object.
     * @param em the {@code EntityManager} of this object
     * @see EntityManager
     */
    UserDAOImpl(EntityManager em){
        this.em=em;
    }
    /**
     * Adds a new {@code User} object to the database.
     * @param username username of the new user
     * @param password password of the new user
     */
    @Override
    public void createUser(String username, String password) {
        em.getTransaction().begin();

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        User u = new User(username, hashed);

        em.persist(u);

        em.getTransaction().commit();
    }
    /**
     * Deletes a {@code User} from the database.
     * @param u the user to delete
     */
    @Override
    public void deleteUser(User u) {

        em.getTransaction().begin();

        em.remove(u);

        em.getTransaction().commit();

    }
    /**
     * Searches for a given user in the database.
     * @param username the username of the user to search for
     */
    @Override
    public List<User> find(String username) {

        TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username='" + username + "'", User.class);

        return q.getResultList();
    }
}
