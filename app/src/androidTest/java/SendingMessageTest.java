import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.managementapp.ChooseBuilding;
import com.example.managementapp.LoginPage;
import com.example.managementapp.MainMenu;
import com.example.managementapp.OpeningScreen;
import com.example.managementapp.PendingTenants;
import com.example.managementapp.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SendingMessageTest {

    @Rule
    public ActivityScenarioRule<OpeningScreen> activityRule = new ActivityScenarioRule<>(OpeningScreen.class);

    @Test
    public void testSendMessage() {
        // Start at OpeningPageActivity
        Espresso.onView(ViewMatchers.withId(R.id.lgnButton)).perform(ViewActions.click());

        // Enter username and password
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.typeText("a@gmail.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.typeText("1"), ViewActions.closeSoftKeyboard());
        // Click on login button
        Espresso.onView(ViewMatchers.withId(R.id.login_submitBtn))
                .perform(ViewActions.click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for BuildingAActivity to load
//        Espresso.onView(ViewMatchers.withId(R.id.listName)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click on Building A button
        Espresso.onView(ViewMatchers.withText("A"))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for ChatScreen to load
        Espresso.onView(ViewMatchers.withId(R.id.chatBtnAdmin)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click on Chat button
        Espresso.onView(ViewMatchers.withId(R.id.chatBtnAdmin)).perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for input field to load
        //Espresso.onView(ViewMatchers.withId(R.id.inputMessage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Enter message
        Espresso.onView(ViewMatchers.withId(R.id.inputMessage)).perform(ViewActions.typeText("Test Test"));

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on Send button
        Espresso.onView(ViewMatchers.withId(R.id.layoutSend)).perform(ViewActions.click());

        // Wait for message to be sent
        // You may need to add a delay or synchronization here to ensure the message is sent and received
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if message is displayed in the RecyclerView
        //Espresso.onView(ViewMatchers.withText("Test Test")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
