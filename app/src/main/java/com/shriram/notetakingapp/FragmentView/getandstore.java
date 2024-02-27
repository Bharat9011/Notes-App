package com.shriram.notetakingapp.FragmentView;

public class getandstore {
    private int id;
    private String title;
    private String Content;
    private String timeandDay;

    public getandstore(int id, String title, String content, String timeandDay) {
        this.id = id;
        this.title = title;
        Content = content;
        this.timeandDay = timeandDay;
    }

    public getandstore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTimeandDay() {
        return timeandDay;
    }

    public void setTimeandDay(String timeandDay) {
        this.timeandDay = timeandDay;
    }
}