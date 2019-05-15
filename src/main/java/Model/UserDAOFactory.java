package Model;

import org.jasypt.util.text.StrongTextEncryptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to make {@code UserDAOFactory} objects and connection with the database.
 */
public class UserDAOFactory {
    /**
     * The current instance of this object.
     */
    private static UserDAOFactory instance;
    /**
     * The {@code EntityManager} of the {@code UserDAO}.
     */
    private static EntityManager em;
    /**
     * The {@code EntityManagerFactory} of to create an {@code EntityManager}.
     */
    private static EntityManagerFactory f;
    /**
     * Creates a new instance of this object.
     */
    static {
        instance = new UserDAOFactory();
        f = Persistence.createEntityManagerFactory("USERUNIT", getProperties());
        em = f.createEntityManager();
    }
    /**
     * Returns the properties of the database connection.
     * @return the properties of the database connection
     */
    private static Map<String, String> getProperties() {
/*        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword("progkorny_2048");
        Reader reader = new Reader();
        String myEncryptedText = textEncryptor.decrypt(reader.getPassword());*/
        String myEncryptedText = "";
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.connection.password", myEncryptedText);
        return properties;
    }
    /**
     * Constructs a {@code UserDAOFactory} object.
     */
    private UserDAOFactory(){ }
    /**
     * Returns an instance of the factory.
     * @return an instance of the factory.
     */
    public static UserDAOFactory getInstance(){
        return instance;
    }
    /**
     * Creates a new {@code UserDAO} object.
     * @return a {@code UserDAO} object
     * @see UserDAO
     */
    public UserDAO createUserDAO(){
        return new UserDAOImpl(em);
    }
    /**
     * Closes the database connection.
     */
    public void close(){
        em.close();
        f.close();
    }
}
