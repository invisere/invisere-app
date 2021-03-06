package dam.invisere.gtidic.udl.cat.invisereapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ValidationResultI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class AccountValidatorTest  {

    Account account;
    ValidationResultI validationResultI;

    @Before
    public void setUp() throws Exception {
        this.account = new Account();
    }

    @Test
    public void isValidName()  {
        String name = "Juan";
        this.validationResultI = AccountValidator.checkName(name);
        Assert.assertTrue(validationResultI.isSuccess());
    }

    @Test
    public void isNotValidNameIfContainNumbers()  {
        String name = "5454Juan";
        this.validationResultI = AccountValidator.checkName(name);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_name_cannot_contain_number),
                validationResultI.getMessage());
    }

    @Test
    public void isNotValidNameIfContainSpecialCharacters()  {
        String name = "Juan%Rodriguez";
        this.validationResultI = AccountValidator.checkName(name);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_name_cannot_contain_special_characters),
                validationResultI.getMessage());
    }

    @Test
    public void isNotValidNameIfIsInvalidLength()  {
        String name = "Juan Rodriguez Requena Salgado Martinez";
        this.validationResultI = AccountValidator.checkName(name);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_name_has_invalid_length),
                validationResultI.getMessage());
    }


    @Test
    public void isValidUsername()  {
        String username = "JuanRa";
        this.validationResultI = AccountValidator.checkUsername(username);
        Assert.assertTrue(validationResultI.isSuccess());
    }

    @Test
    public void isNotValidUsernameIfContainNumbers()  {
        String username = "JuanRa23";
        this.validationResultI = AccountValidator.checkUsername(username);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_username_cannot_contain_number),
                validationResultI.getMessage());
    }

    @Test
    public void isNotValidUsernameIfContainSpecialCharacters()  {
        String username = "Juan&Ra";
        this.validationResultI = AccountValidator.checkUsername(username);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_username_cannot_contain_special_characters),
                validationResultI.getMessage());
    }

    @Test
    public void isNotValidUsernameIfIsInvalidLength()  {
        String username = "Juan Rodriguez Reque Gallego";
        this.validationResultI = AccountValidator.checkUsername(username);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_username_has_invalid_length),
                validationResultI.getMessage());
    }


    @Test
    public void isValidPassword()  {
        String password = "Juan21%Rodri";
        this.validationResultI = AccountValidator.checkPassword(password);
        Assert.assertTrue(validationResultI.isSuccess());
    }

    @Test
    public void isNotValidPasswordIfNotContainCapitalLetters()  {
        String password = "juan21%rodri";
        this.validationResultI = AccountValidator.checkPassword(password);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_password_does_not_contain_capital_letters),
                validationResultI.getMessage());
    }

    @Test
    public void isNotValidPasswordIfNotContainNumbers()  {
        String password = "Juan%Rodri";
        this.validationResultI = AccountValidator.checkPassword(password);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_password_does_not_contain_number),
                validationResultI.getMessage());
    }

    @Test
    public void isNotValidPasswordIfNotContainSpecialCharacters()  {
        String password = "Juan21Rodri";
        this.validationResultI = AccountValidator.checkPassword(password);
        assertFalse(validationResultI.isSuccess());
        assertEquals((R.string.Message_error_password_does_not_contain_special_characters),
                validationResultI.getMessage());
    }


    @Test
    public void isValidEmail()  {
        String email = "ferfefefe@gmail.com";
        this.validationResultI = AccountValidator.checkEmail(email);
        Assert.assertTrue(validationResultI.isSuccess());
    }

}
