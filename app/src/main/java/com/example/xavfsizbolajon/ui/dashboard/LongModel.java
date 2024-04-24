package com.example.xavfsizbolajon.ui.dashboard;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class LongModel {
    public  String documentId;
    public  String name, id, img;
    public   List<PartModel> part;

    public LongModel(List<PartModel> part, String name, String id, String img) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.part = part;
//        this.tags = tags;
    }

    public String getName() { return name; }
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

}

class PartModel {
    public  String id, name, img;
    public  PartModel(String id, String name, String img){
        this.id = id;
        this.img = img;
        this.name = name;
    }
}
