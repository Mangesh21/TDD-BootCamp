package com.thoughtworks.conference.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.thoughtworks.conference.ConferenceAppAndroidJUnitRunner;
import com.thoughtworks.conference.R;
import com.thoughtworks.conference.apiclient.APIClient;
import com.thoughtworks.conference.apiclient.APIClientCallback;
import com.thoughtworks.conference.model.Category;
import com.thoughtworks.conference.model.Conference;
import com.thoughtworks.conference.model.Session;
import com.thoughtworks.conference.presenter.AgendaPresenter;
import com.thoughtworks.conference.rules.ActivityUnitTestRule;
import com.thoughtworks.conference.viewmodel.SessionViewModel;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.thoughtworks.conference.testdata.TestDataUtil.parseDate;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.Matchers.instanceOf;

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
    final Session session1 = new Session("Create_Session", "description1", parseDate("2016-06-26T03:30:00+05:30"),
            parseDate("2016-06-26T05:30:00+05:30"), Category.CREATE, "location1");
    final Session session2 = new Session("Belong_Session", "description2", parseDate("2016-06-26T04:00:00+05:30"),
            parseDate("2016-06-26T05:30:00+05:30"), Category.BELONG, "location2");
    final Session session3 = new Session("Belong_Session", "description2", parseDate("2016-06-26T04:00:00+05:30"),
            parseDate("2016-06-26T05:30:00+05:30"), Category.ASPIRE, "location2");
    final Conference conference = new Conference(session1, session2, session3);

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final APIClientCallback<Conference> callback = (APIClientCallback) invocation.getArguments()[1];
        callback.onSuccess(conference);
        return null;
      }
    }).when(apiClient).get(eq(AgendaPresenter.CONFERENCE_ENDPOINT), any(APIClientCallback.class));
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

  @Test
  public void testSwipe() {
    activityTestRule.launchActivity(new Intent());
    onView(withId(R.id.viewpager)).perform(swipeLeft());
    onData(allOf(instanceOf(SessionViewModel.class))).inAdapterView(withId(R.id.list_view))
            .atPosition(0).onChildView(withId(R.id.date)).check(matches(withText("03:30 AM - 04:30 AM (1h)")));
  }
}
