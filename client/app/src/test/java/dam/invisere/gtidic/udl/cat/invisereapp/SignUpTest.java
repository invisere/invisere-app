package dam.invisere.gtidic.udl.cat.invisereapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountUser;
import dam.invisere.gtidic.udl.cat.invisereapp.models.LoginUtils;

public class SignUpTest {

    AccountUser accountUser;

    @Before
    public void setUp() throws Exception {
        this.accountUser = new AccountUser();
    }


    @Test
    public void isValidName() {
        this.accountUser.setName("Juan");
        Assert.assertEquals(true, LoginUtils.checkName(accountUser.getName(), null));
    }

    @Test
    public void isValidUsername() {
        this.accountUser.setUsername("JuanHernandez");
        Assert.assertEquals(true, LoginUtils.checkName(accountUser.getUsername(), null));
    }

    @Test
    public void isValidEmail() {
        this.accountUser.setUsername("mcnejsn@gmail.com");
        Assert.assertEquals(true, LoginUtils.checkName(accountUser.getMail(), null));
    }

    @Test
    public void isValidPassword() {
        this.accountUser.setPassword("Asd23$sdfg");
        Assert.assertEquals(true, LoginUtils.checkName(accountUser.getPassword(), null));
    }
    
}
