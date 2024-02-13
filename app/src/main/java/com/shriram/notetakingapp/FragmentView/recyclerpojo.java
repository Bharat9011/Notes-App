package com.shriram.notetakingapp.FragmentView;

public class recyclerpojo {
    private int id;
    private String day;
    private String title;

    public recyclerpojo(int id, String title, String day) {
        this.id = id;
        this.day = day;
        this.title = title;
    }

    public recyclerpojo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
