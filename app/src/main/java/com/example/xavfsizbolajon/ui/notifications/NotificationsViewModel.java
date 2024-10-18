package com.example.xavfsizbolajon.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel  {
    int Id;
    String  vieoId, videoName;

    public NotificationsViewModel(int id, String vieoId, String videoName) {
        this.Id = id;
        this.vieoId = vieoId;
        this.videoName = videoName;
    }

    public int getId() {
        return Id;
    }

    public String getVieoId() {
        return vieoId;
    }

    public String getVideoName() {
        return videoName;
    }
}