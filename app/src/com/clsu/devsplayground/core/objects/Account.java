package com.clsu.devsplayground.core.objects;

public class Account {

    private String userID;
    private String username;
    private int experiencePoints;

    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_XP = "xp";

    public Account () { }

    public Account (String userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    @Override
    public String toString() {
        return username;
    }
}
