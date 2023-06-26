import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.managementapp.OpeningScreen;
import com.example.managementapp.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateSurveyTest {

    @Rule
    public ActivityScenarioRule<OpeningScreen> activityRule = new ActivityScenarioRule<>(OpeningScreen.class);

    @Test
    public void testCreateSurvey() {
        // Wait for OpeningScreen activity to load
        Espresso.onView(ViewMatchers.withId(R.id.lgnButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click on the login button
        Espresso.onView(ViewMatchers.withId(R.id.lgnButton)).perform(ViewActions.click());

        // Wait for LoginPage activity to load
        Espresso.onView(ViewMatchers.withId(R.id.login_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Enter email
        Espresso.onView(ViewMatchers.withId(R.id.login_username)).perform(ViewActions.typeText("a@gmail.com"), ViewActions.closeSoftKeyboard());

        // Enter password
        Espresso.onView(ViewMatchers.withId(R.id.login_password)).perform(ViewActions.typeText("1"), ViewActions.closeSoftKeyboard());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click the login button on the LoginPage
        Espresso.onView(ViewMatchers.withId(R.id.login_submitBtn))
                .perform(ViewActions.click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the A building
        Espresso.onView(ViewMatchers.withText("A")).perform(ViewActions.click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for CreateSurvey activity to load
        Espresso.onView(ViewMatchers.withId(R.id.createSurveyAdmin)).perform(ViewActions.click());

        // Enter title
        Espresso.onView(ViewMatchers.withId(R.id.editTextTitle)).perform(ViewActions.typeText("Test survey"));

        // Enter question
        Espresso.onView(ViewMatchers.withId(R.id.editTextQuestion)).perform(ViewActions.typeText("Will the test work"));

        // Select deadline
        // You may need to adjust the date picker interaction based on your implementation
        Espresso.onView(ViewMatchers.withId(R.id.datePickerDeadline)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 7, 7));
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click());

        // Add options
        Espresso.onView(ViewMatchers.withId(R.id.buttonAddOption)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.optionContainer)).check(ViewAssertions.matches(ViewMatchers.hasChildCount(2)));
        Espresso.onView(ViewMatchers.withId(R.id.optionContainer)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.optionContainer)).perform(ViewActions.typeText("Yes"));
        Espresso.onView(ViewMatchers.withId(R.id.optionContainer)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.optionContainer)).perform(ViewActions.typeText("No"));

        // Close soft keyboard
        Espresso.closeSoftKeyboard();

        // Click on the create survey button
        Espresso.onView(ViewMatchers.withId(R.id.buttonCreateSurvey)).perform(ViewActions.click());

        // Wait for MainMenuAdmin activity to load
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // You can add assertions or perform further actions if needed
        Espresso.onView(ViewMatchers.withId(R.id.welcomeAdmin)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

