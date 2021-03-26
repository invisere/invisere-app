package dam.invisere.gtidic.udl.cat.invisereapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.LoginUtils;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class SignUpTest {

    Account account;

    @Before
    public void setUp() throws Exception {
        this.account = new Account();
    }

    @Test
    public void isValidName()  {
        this.account.setName("Juan");
        Assert.assertEquals(true, LoginUtils.checkName(account.getName(), null));
    }

    @Test
    public void isValidUsername() {
        this.account.setUsername("JuanHernandez");
        Assert.assertEquals(true, LoginUtils.checkUsername(account.getUsername(), null));
    }

    @Test
    public void isValidEmail() {
        this.account.setEmail("juan21@gmail.com");
        Assert.assertEquals(true, LoginUtils.isValidEmailAddress(account.getEmail(), null));
    }

    @Test
    public void isValidPassword() {
        this.account.setPassword("Asd23$sdfg");
        Assert.assertEquals(true, LoginUtils.checkPassword(account.getPassword(), null));
    }

}
