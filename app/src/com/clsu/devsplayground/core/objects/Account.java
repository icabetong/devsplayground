package com.clsu.devsplayground.core.objects;

public class Account {

    private String userID;
    private String username;

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

    @Override
    public String toString() {
        return username;
    }
}
