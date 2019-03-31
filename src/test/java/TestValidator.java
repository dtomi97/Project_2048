import Model.UserDAO;
import Model.UserDAOFactory;
import Model.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestValidator {

    private UserDAO ud = UserDAOFactory.getInstance().createUserDAO();
    private Validator v = new Validator(ud);

    @Test
    public void RegisterUsernameTest(){
        assertTrue(v.regUsernameValidate("colaco"), "Username should be 6-16 characters long");
        assertFalse(v.regUsernameValidate("cola"), "Username should be 6-16 characters long");
        assertFalse(v.regUsernameValidate(""), "Username should be 6-16 characters long");
        assertFalse(v.regUsernameValidate("colacolacolacolaa"), "Username should be 6-16 characters long");
        assertTrue(v.regUsernameValidate("colacolacolacola"), "Username should be 6-16 characters long");
    }

    @Test
    public void RegisterUsernameIsUniqueTest(){
        assertFalse(v.regUsernameisUnique(""), "Username should be unavailable");
        assertTrue(v.regUsernameisUnique("asfasdö13213sda"), "Username should be available");
    }

    @Test
    public void RegisterPasswordTest(){
        assertFalse(v.regPasswordValidate("cola"), "This password should be invalid");
        assertFalse(v.regPasswordValidate("colaa1"), "This password should be invalid");
        assertFalse(v.regPasswordValidate("colaaa1A"), "This password should be invalid");
        assertTrue(v.regPasswordValidate("colaLa1!"), "This password should be valid");
    }

    @Test
    public void LoginValidateTest(){
        String username="TEST1363254255", password="testPassword1!";
        ud.createUser(username, password);
        assertTrue(v.loginValidate(username, password), "This user should be able to log in");
        assertFalse(v.loginValidate("Test", "test"), "This user should not be able to log in");
        assertFalse(v.loginValidate("", ""), "This user should not be able to log in");
        assertFalse(v.loginValidate("r8h1r31esjadbf1e12ü12edad", "od1i21didosaoiajiwqisda1"), "This user should not be able to log in");
        ud.deleteUser(ud.find(username).get(0));
    }
}
