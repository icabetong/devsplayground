package com.clsu.devsplayground.ui;

import com.clsu.devsplayground.core.Localization;
import com.clsu.devsplayground.core.components.CoreFrame;
import com.clsu.devsplayground.core.database.support.LanguageDatabase;
import com.clsu.devsplayground.core.objects.Activity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class ActivityFrame extends CoreFrame implements ActionListener {

    private String subjectKey;
    private LanguageDatabase database;

    private int requiredResponses;
    private int itemClicks;
    private String[] correctResponses;
    private ArrayList<String> responses = new ArrayList<>();
    private Activity currentActivity;

    public ActivityFrame(JFrame ancestorFrame, String subjectKey){
        super(ancestorFrame);
        setContentPane(rootPanel);
        setSize(new Dimension(850, 600));
        setResizable(false);

        this.subjectKey = subjectKey;
        database = new LanguageDatabase();
        database.connect();

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

    private void obtainAssets(){
        try {
            currentActivity = database.getRandom(subjectKey);
            correctResponses = currentActivity.getAnswerArray();

            int answerSize = responses.size();
            LanguageDatabase keywordDatabase = new LanguageDatabase();
            keywordDatabase.connect(LanguageDatabase.DATABASE_RESERVED);
            JButton[] buttons = { firstButton, secondButton, thirdButton, fourthButton,
                    fifthButton, sixthButton };
            String[] correctItems = currentActivity.getAnswerArray();
            int[] indexes = { 0, 1, 2, 3, 4, 5};
            Random random = new Random();
            int[] randomIndexes = new int[answerSize];
            for (int i = 0; i < answerSize; i++)
                randomIndexes[i] = indexes[random.nextInt(indexes.length)];

            ArrayList<String> choices = new ArrayList<>(keywordDatabase.generateChoices(subjectKey, answerSize));
            for (int j = 0; j < answerSize; j++)
                choices.add(randomIndexes[j], correctItems[j]);

            for (int k = 0; k < choices.size(); k++)
                buttons[k].setText(choices.get(k));

            setActivityAssets();
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
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
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == firstButton){
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(firstButton.getText());
                setActivityAssets();
            } else responses.clear();
        } else if (e.getSource() == secondButton){
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(secondButton.getText());
                setActivityAssets();
            } else responses.clear();
        } else if (e.getSource() == thirdButton) {
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(thirdButton.getText());
                setActivityAssets();
            } else responses.clear();
        } else if (e.getSource() == fourthButton) {
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(fourthButton.getText());
                setActivityAssets();
            } else responses.clear();
        } else if (e.getSource() == fifthButton){
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(fifthButton.getText());
                setActivityAssets();
            } else responses.clear();
        } else if (e.getSource() == sixthButton) {
            if (responses.size() < currentActivity.getAnswerArray().length) {
                responses.add(sixthButton.getText());
                setActivityAssets();
            } else responses.clear();
        } else if (e.getSource() == backButton) {
            back();
        } else if (e.getSource() == resetButton) {
            setActivityAssets();
            responses.clear();
        }
    }

    private boolean contains(String[] ar, String a){
        for (String s: ar)
            if (s.equals(a)) return true;
        return false;
    }

    private JPanel rootPanel;
    private JPanel childPanel;
    private JPanel answerPanel;
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

}
