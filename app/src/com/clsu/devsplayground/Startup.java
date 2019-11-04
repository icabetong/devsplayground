package com.clsu.devsplayground;

import com.clsu.devsplayground.core.database.SQLiteDatabase;
import com.clsu.devsplayground.ui.AccountsFrame;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;

public class Startup {

    public static void main(String[] args) throws Exception {

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception ignored) { }

        SQLiteDatabase database = new SQLiteDatabase();
        database.connect();
        database.executeSQL("CREATE TABLE IF NOT EXISTS users (userID VARCHAR PRIMARY KEY, username VARCHAR NOT NULL, xp INT)");
        database.endTransaction();
        database.disconnect();

        AccountsFrame form = new AccountsFrame();
        form.invoke();
    }

}
