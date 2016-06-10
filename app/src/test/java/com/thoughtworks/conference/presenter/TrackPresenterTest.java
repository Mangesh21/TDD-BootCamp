package com.thoughtworks.conference.presenter;

import com.thoughtworks.conference.model.Category;
import com.thoughtworks.conference.model.Session;
import com.thoughtworks.conference.testdata.TestDataUtil;
import com.thoughtworks.conference.viewmodel.SessionViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by mangesh on 10/6/16.
 */
public class TrackPresenterTest {

    List<Session> sessions;
    private Session session;

    @Before
    public void setup() {
        String startTimeAsString = "2016-09-24T04:30:00+05:30";
        String endTimeAsString = "2016-09-24T05:30:00+05:30";
        Date startTime = TestDataUtil.parseDate(startTimeAsString);
        Date endTime = TestDataUtil.parseDate(endTimeAsString);
        session = new Session("Craft", "Try your hand at craft", startTime, endTime, Category.BELONG, "Ballroom");

        sessions = new ArrayList<>();
        sessions.add(session);
    }

    @Test
    public void verifyFormattedDataIsRenderederToView() {
        final TrackView trackView = mock(TrackView.class);
        final TrackPresenter trackPresenter = new TrackPresenter(trackView, sessions);
        trackPresenter.presentSessions();
        List<SessionViewModel> sessionViewModels = new ArrayList<>();
        sessionViewModels.add(new SessionViewModel(session));

        Mockito.verify(trackView).render(Mockito.eq(sessionViewModels));
    }

}