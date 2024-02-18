package com.example.xavfsizbolajon.ui.dashboard;

import java.util.Map;

public class Activity2Model {

    private String title;
    private String idUrl;
    private String imageUrl;
    Map<String, String> tags;

    public Activity2Model() {
        this.idUrl = idUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getIdUrl() {
        return idUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public Map<String, String> getTags() {
        return tags;
    }

}
