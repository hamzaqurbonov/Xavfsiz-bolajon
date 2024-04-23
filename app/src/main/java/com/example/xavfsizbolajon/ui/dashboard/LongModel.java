package com.example.xavfsizbolajon.ui.dashboard;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class LongModel {
    private String documentId;
    private String name;
    private String id;
    private String img;
//    List<String> tags;

    public LongModel() {
        this.id = id;
        this.img = img;
        this.name = name;
//        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
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
