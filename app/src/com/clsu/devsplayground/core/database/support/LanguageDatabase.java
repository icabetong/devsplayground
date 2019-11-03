package com.clsu.devsplayground.core.database.support;

import com.clsu.devsplayground.core.database.SQLiteDatabase;
import com.clsu.devsplayground.core.objects.Activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LanguageDatabase extends SQLiteDatabase {

    public static final String KEY_C_LANGUAGE = "lang_C";
    public static final String KEY_JAVA_LANGUAGE = "lang_java";
    public static final String KEY_HTML_LANGUAGE = "lang_html";
    public static final String KEY_ANDROID_LANGUAGE = "lang_android";

    public static final String CATEGORIES_C = "categories_c";
    public static final String CATEGORIES_JAVA = "categories_java";
    public static final String CATEGORIES_HTML = "categories_html";
    public static final String CATEGORIES_ANDROID = "categories_android";

    public static final String DATABASE_RESERVED = "reserved";
    public static final String COLUMN_RESERVED = "keyword";

    public LanguageDatabase() { super(); }

    public LanguageDatabase(String subjectKey) {
        super();
        connect(subjectKey);
    }

    public ArrayList<String> generateChoices(String lang, int number) throws Exception {
        String randomString = String.format("SELECT * FROM %s ORDER BY RANDOM() LIMIT %d", lang, 5 - number);

        ResultSet resultSet = retrieveResults(randomString);
        ArrayList<String> result = new ArrayList<>();
        while (resultSet.next())
            result.add(resultSet.getString(COLUMN_RESERVED));

        return result;
    }

    public ArrayList<Activity> getRandomSet(int amount, String categoryID) throws Exception {
        String retrievalString = String.format("SELECT * FROM %s ORDER BY RANDOM() LIMIT %d", categoryID, amount);

        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(retrievalString);

        ArrayList<Activity> activities = new ArrayList<>();
        while (set.next()){
            String currentQuestion = set.getString(Activity.COLUMN_ACTIVITY);

            Activity currentActivity = new Activity();
            currentActivity.setActivity(currentQuestion);
            activities.add(currentActivity);
        }
        return activities;
    }

    public Activity getRandom(String subjectKey) throws SQLException {
        String retrievalString = String.format("SELECT * FROM %s ORDER BY RANDOM() LIMIT 1", subjectKey);

        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(retrievalString);

        Activity currentActivity = new Activity();
        currentActivity.setInstruction(set.getString(Activity.COLUMN_INSTRUCTION));
        currentActivity.setActivity(set.getString(Activity.COLUMN_ACTIVITY));
        currentActivity.setId(set.getInt(Activity.COLUMN_ID));
        currentActivity.setAnswers(set.getString(Activity.COLUMN_ANSWER));

        return currentActivity;
    }

    public static String determineCategoryKey(String subjectKey){
        switch(subjectKey){
            case KEY_C_LANGUAGE:
                return CATEGORIES_C;
            case KEY_JAVA_LANGUAGE:
                return CATEGORIES_JAVA;
            case KEY_HTML_LANGUAGE:
                return CATEGORIES_HTML;
            case KEY_ANDROID_LANGUAGE:
                return CATEGORIES_ANDROID;
            default: return "";
        }
    }
}
