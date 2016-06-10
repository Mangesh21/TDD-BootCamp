package com.thoughtworks.conference.presenter;

import com.thoughtworks.conference.model.Session;
import com.thoughtworks.conference.viewmodel.SessionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mangesh on 10/6/16.
 */
public class TrackPresenter {

    private final TrackView trackView;
    private final List<Session> sessions;

    public TrackPresenter(TrackView trackView, List<Session> sessions) {
        this.trackView = trackView;
        this.sessions = sessions;
    }

    public void presentSessions() {
        List<SessionViewModel> sessionViewModels = new ArrayList<>();
        for(Session session : sessions) {
            sessionViewModels.add(new SessionViewModel(session));
        }

        trackView.render(sessionViewModels);

    }
}
