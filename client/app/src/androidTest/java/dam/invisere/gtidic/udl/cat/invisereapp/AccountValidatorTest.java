package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ValidationResultI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(AndroidJUnit4.class)
public class AccountValidatorTest {

    ValidationResultI validation;
    private Context context;

    @Rule
    public ActivityTestRule<EntryActivity> mActivityRule =
            new ActivityTestRule<>(EntryActivity.class);

    @Before
    public void setUp() throws IOException {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void isValidName()  {
        String name = "Juan";
        this.validation = AccountValidator.checkName(name);
        Assert.assertTrue(validation.isSuccess());
    }

    @Test
    public void isNotValidNameIfContainNumbers()  {
        String name = "5454Juan";
        this.validation = AccountValidator.checkName(name);
        assertFalse(validation.isSuccess());
        assertEquals(context.getResources().getString(R.string.Message_error_name_cannot_contain_number),
                validation.getMessage());
    }

}
