import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.managementapp.LoginPage;
import com.example.managementapp.OpeningScreen;
import com.example.managementapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginPageTest {

    @Rule
    public ActivityScenarioRule<OpeningScreen> activityRule = new ActivityScenarioRule<>(OpeningScreen.class);

    @Test
    public void testLoginButton() {
        // Click the login button on the OpeningScreen
        Espresso.onView(ViewMatchers.withId(R.id.lgnButton))
                .perform(ViewActions.click());

        // Find and enter the username on the LoginPage
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.typeText("a@gmail.com"), ViewActions.closeSoftKeyboard());

        // Find and enter the password on the LoginPage
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.typeText("1"), ViewActions.closeSoftKeyboard());

        // Click the login button on the LoginPage
        Espresso.onView(ViewMatchers.withId(R.id.login_submitBtn))
                .perform(ViewActions.click());
    }
}

