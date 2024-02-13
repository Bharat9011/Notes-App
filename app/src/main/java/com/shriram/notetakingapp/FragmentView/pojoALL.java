package com.shriram.notetakingapp.FragmentView;

public class pojoALL {

    private int id;
    private String day;
    private String title;

    public pojoALL(int id,String title,String day) {
        this.id = id;
        this.day = day;
        this.title = title;
    }

    public pojoALL() {
    }


    public String getDay() {
        return day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
