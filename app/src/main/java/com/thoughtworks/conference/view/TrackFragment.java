package com.thoughtworks.conference.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thoughtworks.conference.R;
import com.thoughtworks.conference.model.Session;
import com.thoughtworks.conference.presenter.TrackPresenter;
import com.thoughtworks.conference.presenter.TrackView;
import com.thoughtworks.conference.viewmodel.SessionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mangesh on 10/6/16.
 */
public class TrackFragment extends Fragment implements TrackView {

    public static final String SESSION_LIST_KEY = "LIST_SESSIONS";
    TextView mTextView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.session, null);
        mTextView = (TextView) rootView.findViewById(R.id.date);
        ArrayList<Session> sessions = getArguments().getParcelableArrayList(SESSION_LIST_KEY);

        TrackPresenter trackPresenter = new TrackPresenter(this, sessions);
        trackPresenter.presentSessions();
        return rootView;
    }

    @Override
    public void render(List<SessionViewModel> eq) {
        SessionViewModel sessionViewModel = eq.get(0);
        mTextView.setText(sessionViewModel.getDisplayTime());
    }
}
