import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
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

@RunWith(AndroidJUnit4.class)
public class AddingBuilding {

    @Rule
    public ActivityScenarioRule<OpeningScreen> activityRule = new ActivityScenarioRule<>(OpeningScreen.class);

    @Test
    public void testBuildingFlow() {
        // Click the login button on the OpeningScreen
        Espresso.onView(ViewMatchers.withId(R.id.lgnButton))
                .perform(ViewActions.click());
        // Login with "a@gmail.com"
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.typeText("a@gmail.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.typeText("1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_submitBtn))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create a new building as the admin
        Espresso.onView(ViewMatchers.withId(R.id.addBuildingBtn))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.contact_username))
                .perform(ViewActions.typeText("C"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.committeeRadioButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.add_contact_submitBtn))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Go back to the login page
        Espresso.pressBack();


        // Login with "b@gmail.com"
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.typeText("b@gmail.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.typeText("1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_submitBtn))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Add the "C" building as a tenant
        Espresso.onView(ViewMatchers.withId(R.id.addBuildingBtn))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.contact_username))
                .perform(ViewActions.typeText("C"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.tenantRadioButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.add_contact_submitBtn))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Go back using the phone's back button
        Espresso.pressBack();


        // Login with "a@gmail.com"
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.login_username))
                .perform(ViewActions.typeText("a@gmail.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_password))
                .perform(ViewActions.typeText("1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_submitBtn))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the "C" building
        Espresso.onView(ViewMatchers.withText("C"))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the "Pending Tenants" button
        Espresso.onView(ViewMatchers.withId(R.id.pendingTenants))
                .perform(ViewActions.click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int itemPosition = 0;

        //Espresso.onView(ViewMatchers.withId(R.id.button1)).perform(ViewActions.click());
        // Simulate clicking the "Approve" button at the specified position
        //Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        //Espresso.onView(ViewMatchers.withId(R.id.button1)).perform(ViewActions.click());
        Espresso.pressBack();
    }
}

    // Click on the "Button1" in the tenant adapter page
//        Espresso.onView(ViewMatchers.withText("button1"))
//                .perform(ViewActions.click());

