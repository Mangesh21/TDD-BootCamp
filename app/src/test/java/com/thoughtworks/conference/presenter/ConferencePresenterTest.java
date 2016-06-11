package com.thoughtworks.conference.presenter;

import com.thoughtworks.conference.apiclient.APIClient;
import com.thoughtworks.conference.apiclient.APIClientCallback;
import com.thoughtworks.conference.model.Conference;
import com.thoughtworks.conference.view.ConfereceView;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Created by mangesh on 10/6/16.
 */
public class ConferencePresenterTest {

    @Test
    public void testAPIData() {
        APIClient apiClient = Mockito.mock(APIClient.class);
        ConfereceView confereceView = Mockito.mock(ConfereceView.class);
        ConferencePresenter conferencePresenter = new ConferencePresenter(apiClient, "https://intense-fire-9666.firebaseio.com", confereceView);

        conferencePresenter.getConferenceData();
        Mockito.verify(apiClient).get(eq("https://intense-fire-9666.firebaseio.com"), any(APIClientCallback.class));
    }

    @Test
    public void testAPISuccess() {
        APIClient apiClient = Mockito.mock(APIClient.class);
        ConfereceView confereceView = Mockito.mock(ConfereceView.class);
        ConferencePresenter conferencePresenter = new ConferencePresenter(apiClient, "https://intense-fire-9666.firebaseio.com", confereceView);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                final APIClientCallback<Conference> callback = (APIClientCallback<Conference>) invocation.getArguments()[1];
                callback.onSuccess(null);
                return null;
            }
        }).when(apiClient).get(eq("https://intense-fire-9666.firebaseio.com"), any(APIClientCallback.class));
        conferencePresenter.getConferenceData();
        Mockito.verify(confereceView).showData();
    }


}