package com.clsu.devsplayground.core.objects;

public class Chapter {

    private int id;
    private String desc;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "desc";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
