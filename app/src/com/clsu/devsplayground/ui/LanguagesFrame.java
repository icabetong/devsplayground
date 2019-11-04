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
        htmlButton.addActionListener(this);
        androidButton.addActionListener(this);

        try {
            accountDatabase = new AccountDatabase();
            if (currentUserID != null){
                currentAccount = accountDatabase.retrieveByID(currentUserID);
                userLabel.setText(String.format("Hello, %s", currentAccount.getUsername()));
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
        }
    }

    private JPanel rootPanel;
    private JButton cButton;
    private JButton htmlButton;
    private JButton javaButton;
    private JButton androidButton;
    private JLabel userLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (accountDatabase.isDatabaseOpened())
                accountDatabase.disconnect();
        } catch (Exception ignore) {}

        String key = "";
        if (e.getSource() == cButton) key = LanguageDatabase.KEY_C_LANGUAGE;
        else if (e.getSource() == javaButton) key = LanguageDatabase.KEY_JAVA_LANGUAGE;
        else if (e.getSource() == htmlButton) key = LanguageDatabase.KEY_HTML_LANGUAGE;
        else if (e.getSource() == androidButton) key = LanguageDatabase.KEY_ANDROID_LANGUAGE;

        ActivityFrame activityForm = new ActivityFrame(this, currentAccount.getUserID(), key);
        activityForm.invoke();
        this.setVisible(false);
    }

}
