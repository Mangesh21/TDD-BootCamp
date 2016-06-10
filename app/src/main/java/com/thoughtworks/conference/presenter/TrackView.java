package com.thoughtworks.conference.presenter;

import com.thoughtworks.conference.viewmodel.SessionViewModel;

import java.util.List;

/**
 * Created by mangesh on 10/6/16.
 */
public interface TrackView {
    void render(List<SessionViewModel> eq);
}
