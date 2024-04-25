package com.example.xavfsizbolajon.ui.dashboard;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class LongModel {
    public  String documentId;
    public  String name, id, img;
//    public   List<PartModel> part;

    public LongModel() {
        this.id = id;
        this.img = img;
        this.name = name;
//        this.part = part;
    }

    public String getName() { return name; }
    public String getId() {
        return id;
    }
    public String getImg() {
        return img;
    }
//    public void setDocumentId(String documentId) {
//        this.documentId = documentId;
//    }
//    @Exclude
//    public String getDocumentId() {
//        return documentId;
//    }



//    class PartModel {
//        public  String id, name, img;
//        public  PartModel(){
//            this.id = id;
//            this.img = img;
//            this.name = name;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getImg() {
//            return img;
//        }
//    }
}


