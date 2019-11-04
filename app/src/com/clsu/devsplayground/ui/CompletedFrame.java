package com.clsu.devsplayground.ui;

import com.clsu.devsplayground.core.components.CoreFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompletedFrame extends CoreFrame implements ActionListener {

    private String currentUserID;
    private JFrame ancestorFrame;

    public CompletedFrame(JFrame ancestorFrame, String currentUserID, String subjectKey, int score){
        super(ancestorFrame);
        setContentPane(rootPanel);
        setSize(new Dimension(500, 500));
        setResizable(false);
        setLocationRelativeTo(null);
        this.ancestorFrame = ancestorFrame;
        this.currentUserID = currentUserID;

        mainMenuButton.addActionListener(this);
        playAgainButton.addActionListener(this);

        scoreLabel.setText(String.valueOf(score));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton){
            LanguagesFrame frame = new LanguagesFrame(null, currentUserID);
            frame.invoke();

            this.dispose();
        } else if (e.getSource() == playAgainButton) {
            ancestorFrame.setVisible(true);
            this.dispose();
        }
    }

    private JLabel scoreLabel;
    private JButton mainMenuButton;
    private JButton playAgainButton;
    private JPanel rootPanel;
}
