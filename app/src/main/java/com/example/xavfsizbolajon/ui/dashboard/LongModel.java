package com.example.xavfsizbolajon.ui.dashboard;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class LongModel {
    private String documentId;
    private String title;
    private String idUrl;
    private String imageUrl;
//    List<String> tags;

    public LongModel() {
        this.idUrl = idUrl;
        this.imageUrl = imageUrl;
        this.title = title;
//        this.tags = tags;
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

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

//    public List<String> getTags() {
//        return tags;
//    }
}
