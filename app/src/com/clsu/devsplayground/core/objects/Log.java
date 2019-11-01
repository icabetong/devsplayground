package com.clsu.devsplayground.core.objects;

public class Log {

    private String itemID;
    private String chapterID;
    private int score;

    public String getItemID() { return itemID; }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getChapterID() {
        return chapterID;
    }

    public void setChapterID(String chapterID) {
        this.chapterID = chapterID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
