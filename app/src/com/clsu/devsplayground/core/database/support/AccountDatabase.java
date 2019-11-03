package com.clsu.devsplayground.core.database.support;

import com.clsu.devsplayground.core.database.SQLiteDatabase;
import com.clsu.devsplayground.core.objects.Account;

import java.sql.ResultSet;

public class AccountDatabase extends SQLiteDatabase {

    public static final String COLUMN_ID = "userID";
    public static final String COLUMN_NAME = "username";

    public AccountDatabase() {
        super();
        connect();
    }

    public void insert(Account account) throws Exception {
        if (!isDatabaseOpened()) connect();

        String insertQuery = String.format("INSERT OR IGNORE INTO users VALUES ('%s', '%s')",
                account.getUserID(), account.getUsername());

        executeSQL(insertQuery);
    }

    public void changeUsername(String username, String userID) throws Exception {
        String updateQuery = String.format("UPDATE users SET username = '%s' WHERE userID = '%s'", username, userID);

        executeSQL(updateQuery);
    }

    public void remove(Account account) throws Exception {
        String removeQuery = String.format("DELETE FROM users WHERE userID = '%s'", account.getUserID());

        executeSQL(removeQuery);
    }

    public Account retrieveByID(String userID) throws Exception {
        String retrievalQuery = String.format("SELECT * FROM users WHERE userID = '%s'", userID);
        ResultSet set = retrieveResults(retrievalQuery);

        Account account = new Account();
        account.setUserID(set.getString(AccountDatabase.COLUMN_ID));
        account.setUsername(set.getString(AccountDatabase.COLUMN_NAME));
        return account;
    }

    public ResultSet retrieveAll() throws Exception {
        String retrievalQuery = "SELECT * FROM users";
        return retrieveResults(retrievalQuery);
    }

}
