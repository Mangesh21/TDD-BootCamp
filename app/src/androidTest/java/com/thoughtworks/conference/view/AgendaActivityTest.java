package com.thoughtworks.conference.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.thoughtworks.conference.ConferenceAppAndroidJUnitRunner;
import com.thoughtworks.conference.R;
import com.thoughtworks.conference.apiclient.APIClient;
import com.thoughtworks.conference.apiclient.APIClientCallback;
import com.thoughtworks.conference.model.Category;
import com.thoughtworks.conference.model.Conference;
import com.thoughtworks.conference.model.Session;
import com.thoughtworks.conference.rules.ActivityUnitTestRule;
import com.thoughtworks.conference.viewmodel.SessionViewModel;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.thoughtworks.conference.testdata.TestDataUtil.parseDate;
import static com.thoughtworks.conference.utils.CustomEspressoMatcher.atPositionInViewGroup;
import static com.thoughtworks.conference.utils.CustomEspressoMatcher.withNumberOfChildren;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@RunWith(AndroidJUnit4.class)
public class AgendaActivityTest {
  public static final String URL = "https://intense-fire-9666.firebaseio.com/";
  public static final String THERE_IS_NO_NETWORK = "There is no network";
  protected APIClient apiClient;

  @Rule
  public ActivityUnitTestRule<AgendaActivity> activityTestRule = new
      ActivityUnitTestRule<AgendaActivity>(getActivityProvider(), AgendaActivity.class, false, false) {
      };

  @Before
  public void setup() throws Exception {
    apiClient = mock(APIClient.class);
    mockAPIClientForSuccess();
  }

  private void mockAPIClientForSuccess() {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final APIClientCallback callback = (APIClientCallback) invocation.getArguments()[1];

        Session session1 = new Session("Craft", "Try your hand at craft", parseDate("2016-05-23T19:15:00+05:30"), parseDate("2016-05-23T20:15:00+05:30"), Category.ASPIRE, "Ballroom");
        Session session2 = new Session("IOTT", "Awesome project", parseDate("2016-05-23T19:15:00+05:30"), parseDate("2016-05-23T20:15:00+05:30"), Category.BELONG, "Grand Hall");
        Session session3 = new Session("Keynote", "By Roy Singham", parseDate("2016-05-23T17:15:00+05:30"), parseDate("2016-05-23T18:15:00+05:30"), Category.CREATE, "Pre Function Area");
        Conference conference = new Conference(session1, session2, session3);

        callback.onSuccess(conference);
        return null;
      }
    }).when(apiClient).get(eq(URL), any(APIClientCallback.class));
  }

  @Test
  public void swipeAndAssertOnAspire(){
    activityTestRule.launchActivity(new Intent());
    onView(withId(R.id.viewpager)).perform(swipeLeft());
    onData(instanceOf(SessionViewModel.class)).inAdapterView(allOf(withId(R.id.list_view), isDisplayingAtLeast(70)))
        .atPosition(0).onChildView(withId(R.id.location)).check(matches(withText("Ballroom")));
  }

  @Test
  public void shouldShowCategoriesAsViewPagersTitle(){
    mockAPIClientForSuccess();

    activityTestRule.launchActivity(new Intent());

    Matcher<View> slidingTabStripViewMatcher =
        withClassName(is("android.support.design.widget.TabLayout$SlidingTabStrip"));
    onView(slidingTabStripViewMatcher).check(matches(withNumberOfChildren(3)));
    onView(atPositionInViewGroup(slidingTabStripViewMatcher, 0)).check(matches(withChild(withText("CREATE"))));
    onView(atPositionInViewGroup(slidingTabStripViewMatcher, 1)).check(matches(withChild(withText("ASPIRE"))));
    onView(atPositionInViewGroup(slidingTabStripViewMatcher, 2)).check(matches(withChild(withText("BELONG"))));
  }


  @NonNull
  private ConferenceAppAndroidJUnitRunner.ActivityProvider<AgendaActivity> getActivityProvider() {
    return new ConferenceAppAndroidJUnitRunner.ActivityProvider<AgendaActivity>() {
      @Override
      public AgendaActivity getActivity() {
        return new AgendaActivity() {
          @NonNull
          @Override
          protected APIClient getApiClient() {
            return apiClient;
          }

          @Override
          protected void onDestroy() {
            super.onDestroy();
            reset(apiClient);
          }
        };
      }
    };
  }
}
