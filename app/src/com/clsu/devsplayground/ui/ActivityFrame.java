package com.clsu.devsplayground.ui;

import com.clsu.devsplayground.core.Localization;
import com.clsu.devsplayground.core.components.CoreFrame;
import com.clsu.devsplayground.core.database.support.AccountDatabase;
import com.clsu.devsplayground.core.database.support.LanguageDatabase;
import com.clsu.devsplayground.core.objects.Activity;
import sas.swing.plaf.MultiLineLabelUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ActivityFrame extends CoreFrame implements ActionListener {

    private String userID;
    private String subjectKey;
    private LanguageDatabase database;

    private int score = 0;
    private int itemsShown = 0;
    private String[] correctResponses;
    private ArrayList<String> responses = new ArrayList<>();
    private ArrayList<Integer> shownActivityIDs = new ArrayList<>();
    private Activity currentActivity;

    private static final int numberOfActivities = 5;

    public ActivityFrame(JFrame ancestorFrame, String userID, String subjectKey){
        super(ancestorFrame);
        setContentPane(rootPanel);
        setSize(new Dimension(850, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        this.userID = userID;
        this.subjectKey = subjectKey;

        database = new LanguageDatabase();

        instructionLabel.setUI(MultiLineLabelUI.labelUI);

        backButton.addActionListener(this);
        resetButton.addActionListener(this);

        firstButton.addActionListener(this);
        secondButton.addActionListener(this);
        thirdButton.addActionListener(this);
        fourthButton.addActionListener(this);
        fifthButton.addActionListener(this);
        sixthButton.addActionListener(this);

        obtainAssets();
    }

    private void validateResponses() {

        ArrayList<String> correctResponsesList = new ArrayList<>(Arrays.asList(correctResponses));

        int totalResponsesSize = responses.size();
        int totalCorrectResponsesSize = correctResponsesList.size();

        if (totalCorrectResponsesSize == totalResponsesSize){
            if (correctResponsesList.containsAll(responses)){
                score++;
                JOptionPane.showMessageDialog(this, Localization.DIALOG_ACTIVITY_FINISHED);

                obtainAssets();
            } else {
                score--;
                JOptionPane.showMessageDialog(this, Localization.DIALOG_ACTIVITY_WRONG);
            }
            if (itemsShown > numberOfActivities){
                try {
                    database.disconnect();

                    AccountDatabase accountDatabase = new AccountDatabase();
                    accountDatabase.updateXP(userID, score);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            Localization.TITLE_GENERIC, JOptionPane.ERROR_MESSAGE);
                }
                reset();

                CompletedFrame complete = new CompletedFrame(this, userID, subjectKey, score);
                complete.invoke();

                this.setVisible(false);
            }
        }
    }

    private void obtainAssets(){
        try {
            responses.clear();

            Activity activity = database.getRandom(subjectKey);
            System.out.println(activity.getId());
            correctResponses = activity.getAnswerArray();

            int answerSize = correctResponses.length;
            LanguageDatabase keywordDatabase = new LanguageDatabase();
            keywordDatabase.connect(LanguageDatabase.DATABASE_RESERVED);
            JButton[] buttons = { firstButton, secondButton, thirdButton, fourthButton,
                    fifthButton, sixthButton };
            ArrayList<String> responses = new ArrayList<>(6);
            responses.addAll(Arrays.asList(correctResponses));
            responses.addAll(keywordDatabase.generateChoices(subjectKey, answerSize));

            Collections.shuffle(responses);

            if (!shownActivityIDs.contains(activity.getId())){
                itemsShown++;
                currentActivity = activity;
                shownActivityIDs.add(activity.getId());
                setActivityAssets();

                for (int i = 0; i < buttons.length; i++){
                    buttons[i].setText(responses.get(i));
                }
            } else
                obtainAssets();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
        }
    }

    private void setActivityAssets() {
        try {
            if (currentActivity != null){
                scoreLabel.setText(String.valueOf(score));
                categoryLabel.setText(database.getCategory(LanguageDatabase.determineCategoryKey(subjectKey), currentActivity.getType()));
                instructionLabel.setText(currentActivity.getInstruction());

                switch (responses.size()){
                    case 0:
                        questionLabel.setText(Activity.formatSnippet(currentActivity.getActivity()));
                        break;
                    case 1:
                        questionLabel.setText(Activity.formatSnippet(currentActivity.getActivity(),
                                responses.get(0)));
                        break;
                    case 2:
                        questionLabel.setText(Activity.formatSnippet(currentActivity.getActivity(),
                                responses.get(0), responses.get(1)));
                        break;
                    case 3:
                        questionLabel.setText(Activity.formatSnippet(currentActivity.getActivity(),
                                responses.get(0), responses.get(1), responses.get(2)));
                        break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == firstButton){
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(firstButton.getText());
                setActivityAssets();

                validateResponses();
            } else reset();
        } else if (e.getSource() == secondButton){
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(secondButton.getText());
                setActivityAssets();

                validateResponses();
            } else reset();
        } else if (e.getSource() == thirdButton) {
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(thirdButton.getText());
                setActivityAssets();

                validateResponses();
            } else reset();
        } else if (e.getSource() == fourthButton) {
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(fourthButton.getText());
                setActivityAssets();

                validateResponses();
            } else reset();
        } else if (e.getSource() == fifthButton){
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(fifthButton.getText());
                setActivityAssets();

                validateResponses();
            } else reset();
        } else if (e.getSource() == sixthButton) {
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(sixthButton.getText());
                setActivityAssets();

                validateResponses();
            } else reset();
        } else if (e.getSource() == backButton) {
            back();
        } else if (e.getSource() == resetButton) {
            reset();
        }
    }

    private void reset(){
        responses.clear();
        setActivityAssets();
    }

    private JPanel rootPanel;
    private JPanel childPanel;
    private JLabel questionLabel;
    private JButton fourthButton;
    private JButton fifthButton;
    private JButton sixthButton;
    private JButton firstButton;
    private JButton secondButton;
    private JButton thirdButton;
    private JLabel instructionLabel;
    private JButton backButton;
    private JButton resetButton;
    private JPanel answerPanel;
    private JLabel scoreLabel;
    private JLabel categoryLabel;

}
