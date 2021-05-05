package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignUpInstrumentedTest {

    @Rule
    public ActivityTestRule<EntryActivity> mActivityRule = new ActivityTestRule<>(EntryActivity.class);

    private Context context;
    private FragmentScenario<SignupFragment> scenario;

    @Before
    public void setUp() throws IOException {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void showErrorMessageUser() {
        scenario = FragmentScenario.launchInContainer(SignupFragment.class, new Bundle(), R.style.Theme_InvisereApp);

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.TextField_username_register)))).perform(typeText("3435"));
        closeSoftKeyboard();
        onView(withId(R.id.Button_register)).perform(click());
        onView(withId(R.id.TextField_username_register))
                .check(matches(hasTextInputLayoutErrorText(
                        context.getResources().getString(
                                R.string.Message_error_name_cannot_contain_number))));
    }

    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String err = error.toString();
                return expectedErrorText.equals(err);
            }

        };
    }

}