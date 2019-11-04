package com.clsu.devsplayground.core.objects;

public class Activity {

    private int id;
    private String instruction;
    private String activity;
    private int type;
    private String answers;

    public static final int TYPE_CODING_ACTIVITY = 1;
    public static final int answersSize = 3;
    private static final String emptyResponse = "_______";

    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_ANSWER = "answers";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ID = "id";

    public Activity() {  }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAnswers() {
        return answers;
    }

    public String[] getAnswerArray() { return convertStringToArray(answers); }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public static String formatSnippet(String snippet){
        return "<html>" + String.format(snippet, emptyResponse, emptyResponse, emptyResponse) + "</html>";
    }

    public static String formatSnippet(String snippet, String firstAnswer){
        return "<html>" + String.format(snippet, firstAnswer, emptyResponse, emptyResponse) + "</html>";
    }

    public static String formatSnippet(String snippet, String firstAnswer, String secondAnswer){
        return "<html>" + String.format(snippet, firstAnswer, secondAnswer, emptyResponse) + "</html>";
    }

    public static String formatSnippet(String snippet, String firstAnswer, String secondAnswer, String thirdAnswer){
        return "<html>" + String.format(snippet, firstAnswer, secondAnswer, thirdAnswer) + "</html>";
    }

    public static final String separator = "__,__";
    public static String convertArrayToString(String[] array){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < array.length; i++){
            str.append(array[i]);
            if (i < array.length - 1)
                str.append(separator);
        }
        return str.toString();
    }

    public static String[] convertStringToArray(String s){
        return s.split(separator);
    }

}
