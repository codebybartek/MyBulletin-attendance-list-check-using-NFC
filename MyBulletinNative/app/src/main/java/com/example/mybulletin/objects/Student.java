package com.example.mybulletin.objects;

public class Student {

    private String name;
    private String ID;
    private String tagId;
    private String tagData;
    public boolean status;

    public Student(String name, String ID, String tagId, boolean status, String tagData) {
        this.name = name;
        this.ID = ID;
        this.status = status;
        this.tagId = tagId;
        this.tagData = tagData;
    }

    public void setTagId(String tagId) {
        tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getTagId() {
        return tagId;
    }

    public boolean getStatus() {
        return status;
    }

    public String getTagData() { return tagData; }

    public void setTagData(String tagData) {
        this.tagData = tagData;
    }
}
