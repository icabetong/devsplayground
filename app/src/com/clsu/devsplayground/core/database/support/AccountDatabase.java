package com.clsu.devsplayground.core.database.support;

import com.clsu.devsplayground.core.database.SQLiteDatabase;
import com.clsu.devsplayground.core.objects.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDatabase extends SQLiteDatabase {

    public static final String COLUMN_ID = "userID";
    public static final String COLUMN_NAME = "username";

    public AccountDatabase() {
        super();
        connect();
    }

    public void insert(Account account) throws SQLException {
        if (!isDatabaseOpened()) connect();

        String insertQuery = String.format("INSERT OR IGNORE INTO users VALUES ('%s', '%s', %d)",
                account.getUserID(), account.getUsername(), 0);

        executeSQL(insertQuery);
    }

    public void changeUsername(String username, String userID) throws SQLException {
        String updateQuery = String.format("UPDATE users SET username = '%s' WHERE userID = '%s'", username, userID);

        executeSQL(updateQuery);
    }

    public void updateXP(String userID, int xp) throws SQLException {
        Account account = retrieveByID(userID);
        int currentPoints = account.getExperiencePoints();
        currentPoints += xp;

        String updateQuery = String.format("UPDATE users SET xp = %d WHERE userID = '%s'", currentPoints, userID);
        executeSQL(updateQuery);
    }

    public void remove(Account account) throws SQLException {
        String removeQuery = String.format("DELETE FROM users WHERE userID = '%s'", account.getUserID());

        executeSQL(removeQuery);
    }

    public Account retrieveByID(String userID) throws SQLException {
        String retrievalQuery = String.format("SELECT * FROM users WHERE userID = '%s'", userID);
        ResultSet set = retrieveResults(retrievalQuery);

        Account account = new Account();
        account.setUserID(set.getString(Account.COLUMN_USER_ID));
        account.setUsername(set.getString(Account.COLUMN_USERNAME));
        account.setExperiencePoints(set.getInt(Account.COLUMN_XP));
        return account;
    }

    public ResultSet retrieveAll() throws Exception {
        String retrievalQuery = "SELECT * FROM users";
        return retrieveResults(retrievalQuery);
    }

}
