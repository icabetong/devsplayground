package com.clsu.devsplayground.ui;

import com.clsu.devsplayground.core.Localization;
import com.clsu.devsplayground.core.components.CoreFrame;
import com.clsu.devsplayground.core.database.support.AccountDatabase;
import com.clsu.devsplayground.core.database.support.LanguageDatabase;
import com.clsu.devsplayground.core.objects.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LanguagesFrame extends CoreFrame implements ActionListener {

    private Account currentAccount;
    private AccountDatabase accountDatabase;

    public LanguagesFrame(JFrame ancestorFrame, String currentUserID){
        super(ancestorFrame);
        setContentPane(rootPanel);
        setSize(new Dimension(500, 450));
        setResizable(false);
        setLocationRelativeTo(null);
        cButton.addActionListener(this);
        javaButton.addActionListener(this);

        try {
            accountDatabase = new AccountDatabase();
            if (currentUserID != null){
                currentAccount = accountDatabase.retrieveByID(currentUserID);
                userLabel.setText(String.format("Hello, %s", currentAccount.getUsername()));
                xpLabel.setText(String.valueOf(currentAccount.getExperiencePoints()));
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
        }
    }

    private JPanel rootPanel;
    private JButton cButton;
    private JButton javaButton;
    private JLabel userLabel;
    private JLabel xpLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (accountDatabase.isDatabaseOpened())
                accountDatabase.disconnect();
        } catch (Exception ignore) {}

        String key = "";
        if (e.getSource() == cButton) key = LanguageDatabase.KEY_C_LANGUAGE;
        else if (e.getSource() == javaButton) key = LanguageDatabase.KEY_JAVA_LANGUAGE;

        ActivityFrame activityForm = new ActivityFrame(this, currentAccount.getUserID(), key);
        activityForm.invoke();
        this.setVisible(false);
    }

}
