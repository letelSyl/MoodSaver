package com.sylvainletellier.moodsaver.model;

public class ItemHistory {
    private int moodIndex;
    private String date, comment;

    public ItemHistory(int moodIndex, String date, String comment) {
        this.moodIndex = moodIndex;
        this.date = date;
        this.comment = comment;
    }

    public int getMoodIndex() {
        return moodIndex;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public void setMoodIndex(int moodIndex) {
        this.moodIndex = moodIndex;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

