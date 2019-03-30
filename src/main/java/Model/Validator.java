package Model;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
/**
 * Class for validating {@code User}.
 * @see User
 */
public class Validator {
    /**
     * The {@code UserDAO} of this object.
     * @see UserDAO
     */
    private  UserDAO ud;
    /**
     * Constructs a {@code Validator} object.
     * @param ud UserDAO of this object
     */
    public Validator(UserDAO ud) {
        this.ud = ud;
    }
    /**
     * Validates an authenticating User.
     * @param username the username of the authenticating user
     * @param password the password of the authenticating user
     * @return if the user names and the passwords matched.
     */
    public boolean loginValidate(String username, String password){
        if(username.isEmpty() || password.isEmpty() || username.length()<6 || username.length()>16 || password.length()<6 || password.length()>16)
            return false;
        List<User> userLista = ud.find(username);

        return !userLista.isEmpty()&& BCrypt.checkpw(password, userLista.get(0).getPassword());
    }
    /**
     * Validates if the registering user's username's length is legal.
     * @param username the username of the registering user
     * @return if the user name's length is legal.
     */
    public boolean regUsernameValidate(String username){
        return username.length()>=6 && username.length() <=16;
    }
    /**
     * Checks if the registering user's username is unique.
     * @param username the username of the registering user
     * @return if the user name is unique.
     */
    public boolean regUsernameisUnique(String username){
        List<User> userLista = ud.find(username);

        return userLista.isEmpty() && !username.isEmpty();
    }
    /**
     * Validates if the registering user's password is legal.
     * @param password the password of the registering user
     * @return if the password is legal
     */
    public boolean regPasswordValidate(String password){
        if(password.contains("!") || password.contains("$") || password.contains("#") || password.contains("+") || password.contains("-") || password.contains("%"))
            for(int i=0; i<10; i++){
                if(password.contains(String.valueOf(i)))
                    return !password.equals(password.toLowerCase()) && password.length()>=6 && password.length()<=16;
            }
        return false;
    }
}
