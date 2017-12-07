package com.tw.android.tweets.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tw.android.tweets.R;
import com.tw.android.tweets.ThoughtWorksTweetsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TweetsListRequestTest {

    @Rule
    public ActivityTestRule<ThoughtWorksTweetsActivity> mTweetsTestActivity =
            new ActivityTestRule<>(ThoughtWorksTweetsActivity.class);

    /**
     * 1) test when requestTweetsFailed, should click retryButton for retry request tweets list
     * 2)   Button before run this test method, you must cut down the connected internet,
     *          otherwise you won't see the correct result
     */
    @Test
    public void requestTweetsListRetry() throws Exception{
        onView(ViewMatchers.withId(R.id.load_failed)).perform(click());
    }
}
