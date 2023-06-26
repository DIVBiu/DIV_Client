import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.managementapp.OpeningScreen;
import com.example.managementapp.Register;
import com.example.managementapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Rule
    public ActivityScenarioRule<Register> activityRule = new ActivityScenarioRule<>(Register.class);

    @Test
    public void testRegistration() {
        // Wait for Register activity to load
        Espresso.onView(ViewMatchers.withId(R.id.email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Enter email
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("test3@gmail.com"), ViewActions.closeSoftKeyboard());

        // Enter full name
        Espresso.onView(ViewMatchers.withId(R.id.full_name)).perform(ViewActions.typeText("Testing_register"), ViewActions.closeSoftKeyboard());

        // Enter password
        Espresso.onView(ViewMatchers.withId(R.id.Password)).perform(ViewActions.typeText("123456789"), ViewActions.closeSoftKeyboard());

        // Enter password confirmation
        Espresso.onView(ViewMatchers.withId(R.id.Password2)).perform(ViewActions.typeText("123456789"), ViewActions.closeSoftKeyboard());


        // Click on the register button
        Espresso.onView(ViewMatchers.withId(R.id.signup_btn)).perform(ViewActions.click());

        // Wait for ChooseBuilding activity to load
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that ChooseBuilding activity is displayed
        // You can add additional assertions to verify the correctness of the activity if needed
    }
}
