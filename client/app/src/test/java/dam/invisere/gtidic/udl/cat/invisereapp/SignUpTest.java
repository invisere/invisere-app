package dam.invisere.gtidic.udl.cat.invisereapp;

import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountUser;
import dam.invisere.gtidic.udl.cat.invisereapp.models.LoginUtils;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class SignUpTest {

    AccountUser accountUser;

    @Before
    public void setUp() throws Exception {
        this.accountUser = new AccountUser();
    }

    @Test
    public void isValidName()  {
        this.accountUser.setName("Juan");
        Assert.assertEquals(true, LoginUtils.checkName(accountUser.getName(), null));
    }

    @Test
    public void isValidUsername() {
        this.accountUser.setUsername("JuanHernandez");
        Assert.assertEquals(true, LoginUtils.checkUsername(accountUser.getUsername(), null));
    }

    @Test
    public void isValidEmail() {
        this.accountUser.setMail("juan21@gmail.com");
        Assert.assertEquals(true, LoginUtils.isValidEmailAddress(accountUser.getMail(), null));
    }

    @Test
    public void isValidPassword() {
        this.accountUser.setPassword("Asd23$sdfg");
        Assert.assertEquals(true, LoginUtils.checkPassword(accountUser.getPassword(), null));
    }

}
