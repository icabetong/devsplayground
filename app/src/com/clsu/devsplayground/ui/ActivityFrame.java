package com.clsu.devsplayground.ui;

import com.clsu.devsplayground.core.Localization;
import com.clsu.devsplayground.core.components.CoreFrame;
import com.clsu.devsplayground.core.database.support.LanguageDatabase;
import com.clsu.devsplayground.core.objects.Activity;
import sas.swing.plaf.MultiLineLabelUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Activity currentActivity;

    private static final int numberOfActivities = 10;

    public ActivityFrame(JFrame ancestorFrame, String userID, String subjectKey){
        super(ancestorFrame);
        setContentPane(rootPanel);
        setSize(new Dimension(850, 600));
        setResizable(false);

        this.userID = userID;
        this.subjectKey = subjectKey;

        database = new LanguageDatabase();
        database.connect();

        questionLabel.setUI(MultiLineLabelUI.labelUI);

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

    private void validateResponses(){

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
            if (itemsShown == numberOfActivities){
                CompletedFrame complete = new CompletedFrame(this, userID, subjectKey, score);
                complete.invoke();
                reset();
                this.setVisible(false);
            }
        }
    }

    private void obtainAssets(){
        try {
            currentActivity = database.getRandom(subjectKey);
            correctResponses = currentActivity.getAnswerArray();

            int answerSize = responses.size();
            LanguageDatabase keywordDatabase = new LanguageDatabase();
            keywordDatabase.connect(LanguageDatabase.DATABASE_RESERVED);
            JButton[] buttons = { firstButton, secondButton, thirdButton, fourthButton,
                    fifthButton, sixthButton };
            ArrayList<String> responses = new ArrayList<>(6);
            responses.addAll(Arrays.asList(correctResponses));
            responses.addAll(keywordDatabase.generateChoices(subjectKey, answerSize));
            Collections.shuffle(responses);

            for (int i = 0; i < buttons.length; i++){
                buttons[i].setText(responses.get(i));
            }

            itemsShown++;
            setActivityAssets();
        } catch (Exception ex) {
            System.out.println(currentActivity.getId());
            ex.printStackTrace();

            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
            back();
            this.dispose();
        }
    }

    private void setActivityAssets() {
        try {
            if (currentActivity != null){
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
            e.printStackTrace();

            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
            back();
            this.dispose();
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

}
