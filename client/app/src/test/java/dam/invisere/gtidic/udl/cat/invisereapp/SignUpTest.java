package dam.invisere.gtidic.udl.cat.invisereapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;

public class SignUpTest {

    Account account;

    @Before
    public void setUp() throws Exception {
        this.account = new Account();
    }

    @Test
    public void isValidName()  {
        this.account.setName("Juan");
        Assert.assertEquals(true, AccountValidator.checkName(account.getName()));
    }

    @Test
    public void isValidUsername() {
        this.account.setUsername("JuanHernandez");
        Assert.assertEquals(true, AccountValidator.checkUsername(account.getUsername()));
    }

    @Test
    public void isValidEmail() {
        this.account.setEmail("juan21@gmail.com");
        Assert.assertEquals(true, AccountValidator.checkEmail(account.getEmail()));
    }

    @Test
    public void isValidPassword() {
        this.account.setPassword("Asd23$sdfg");
        Assert.assertEquals(true, AccountValidator.checkPassword(account.getPassword()));
    }

}
