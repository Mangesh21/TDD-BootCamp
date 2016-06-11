package com.thoughtworks.conference.presenter;

import android.support.annotation.NonNull;

import com.thoughtworks.conference.apiclient.APIClient;
import com.thoughtworks.conference.apiclient.APIClientCallback;
import com.thoughtworks.conference.view.ConfereceView;

/**
 * Created by mangesh on 10/6/16.
 */
public class ConferencePresenter {

    private final APIClient apiClient;
    private String url;
    private ConfereceView mConferenceView;

    public ConferencePresenter(APIClient apiClient, String url, ConfereceView confereceView) {
        this.apiClient = apiClient;
        this.url = url;
        this.mConferenceView = confereceView;
    }

    public void getConferenceData() {
        apiClient.get(url, new APIClientCallback<Object>() {
            @Override
            public void onSuccess(Object success) {
                mConferenceView.showData();
            }

            @Override
            public void onFailure(Exception e) {

            }

            @NonNull
            @Override
            public Class<Object> getClassOfType() {
                return null;
            }
        });
    }
}
