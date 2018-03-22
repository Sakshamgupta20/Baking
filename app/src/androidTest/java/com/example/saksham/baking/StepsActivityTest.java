package com.example.saksham.baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Saksham on 22-03-2018.
 */

@RunWith(AndroidJUnit4.class)
public class StepsActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {


        onView(withId(R.id.recard)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
    }

}