package com.thoughtworks.conference.view;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.thoughtworks.conference.R;
import com.thoughtworks.conference.model.Category;
import com.thoughtworks.conference.model.Session;
import com.thoughtworks.conference.rules.FragmentTestRule;
import com.thoughtworks.conference.testdata.TestDataUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mangesh on 10/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class TrackFragmentTest {

    @Rule
    public FragmentTestRule mFragmentTestRule = new FragmentTestRule();

    @Test
    public void renderSessionTimings() {
        final ArrayList<Session> sessions = new ArrayList<>();

        Session session1 = new Session("Craft", "Try your hand at craft", TestDataUtil.parseDate("2016-09-24T04:30:00+05:30"), TestDataUtil.parseDate("2016-09-24T06:30:00+05:30"), Category.BELONG, "Ballroom");
        Session session2 = new Session("Craft", "Try your hand at craft", null, null, Category.CREATE, "Pre function area");

        sessions.add(session1);
        sessions.add(session2);

        TrackFragment trackFragment = new  TrackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TrackFragment.SESSION_LIST_KEY, sessions);
        trackFragment.setArguments(bundle);
        mFragmentTestRule.launch(trackFragment);
        onView(withId(R.id.date)).check(matches(withText("04:30 AM - 06:30 AM (2h)")));

    }
}
