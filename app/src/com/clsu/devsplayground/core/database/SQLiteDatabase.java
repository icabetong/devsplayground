package com.clsu.devsplayground.core.database;

import java.sql.*;

public class SQLiteDatabase {

    private boolean databaseOpened;

    protected Connection connection;

    private static final String databaseOpenException = "Error Loading Database";

    public boolean isDatabaseOpened() { return databaseOpened; }

    public SQLiteDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ignored) { }
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            connection.setAutoCommit(false);
            databaseOpened = true;
        } catch (SQLException e) {
            System.out.println(databaseOpenException);
        }
    }

    public void connect(String database) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
            connection.setAutoCommit(false);
            databaseOpened = true;
        } catch (SQLException e) {
            System.out.println(databaseOpenException);
        }
    }

    public void endTransaction() throws SQLException {
        connection.commit();
    }

    public void disconnect() throws SQLException {
        connection.close();
        databaseOpened = false;
    }

    public void executeSQL(String command) throws SQLException {
        if (databaseOpened){
            Statement statement = connection.createStatement();
            statement.executeUpdate(command);
            statement.close();
        } else
            throw new SQLException("Database not opened yet");
    }

    public ResultSet retrieveResults(String command) throws SQLException {
        ResultSet result;
        if (databaseOpened){
            Statement retrievalStatement = connection.createStatement();
            result = retrievalStatement.executeQuery(command);
        } else
            throw new SQLException("Database not opened yet");

        return result;
    }

}
