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
        this.accountUser.setName("Marti");
        Assert.assertEquals(true, LoginUtils.checkName(accountUser.getName(), null));
    }

}
