package com.clsu.devsplayground.ui;

import com.clsu.devsplayground.core.Localization;
import com.clsu.devsplayground.core.components.CoreFrame;
import com.clsu.devsplayground.core.database.support.AccountDatabase;
import com.clsu.devsplayground.core.models.AccountListModel;
import com.clsu.devsplayground.core.objects.Account;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.UUID;

public class AccountsFrame extends CoreFrame implements ActionListener {

    private AccountDatabase database = new AccountDatabase();

    public AccountsFrame() {
        super(null);
        setContentPane(rootPanel);
        setSize(500, 350);
        setResizable(false);

        mainList.setFixedCellHeight(35);
        mainList.setDragEnabled(false);

        continueButton.addActionListener(this);
        newButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);

        initialize();
    }

    private void initialize() {
        try {
            ResultSet userSet = database.retrieveAll();
            AccountListModel model = new AccountListModel();
            while (userSet.next()){
                Account account = new Account();
                account.setUserID(userSet.getString(AccountDatabase.COLUMN_ID));
                account.setUsername(userSet.getString(AccountDatabase.COLUMN_NAME));
                model.addElement(account);
            }
            mainList.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
        }
    }

    private JPanel rootPanel;
    private JList<Account> mainList;
    private JButton continueButton;
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton){
            Account currentAccount = mainList.getSelectedValue();
            if (currentAccount != null) {
                try {
                    if (database.isDatabaseOpened()) database.disconnect();
                } catch (Exception ignored) { }
                finally {
                    LanguagesFrame form = new LanguagesFrame(this, currentAccount.getUserID());
                    form.invoke();

                    this.dispose();
                }
            } else
                JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_NO_SELECTED);
        } else if (e.getSource() == newButton) {
            String username = JOptionPane.showInputDialog(this, Localization.DIALOG_INPUT_USERNAME);
            String randomUserID = UUID.randomUUID().toString();

            if (username != null) {
                try {
                    Account account = new Account(randomUserID, username);
                    database.insert(account);
                    database.endTransaction();

                    initialize();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_GENERIC);
                }
            }

        } else if (e.getSource() == editButton) {
            String username = JOptionPane.showInputDialog(this, Localization.DIALOG_INPUT_NEW_USERNAME);
            Account currentAccount = mainList.getSelectedValue();

            if (currentAccount != null && username != null) {
                try {
                    database.changeUsername(username, currentAccount.getUserID());
                    database.endTransaction();

                    initialize();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
                }
            } else
                JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_NO_SELECTED);

        } else if (e.getSource() == deleteButton) {
            Account currentAccount = mainList.getSelectedValue();

            if (currentAccount != null) {
                int option = JOptionPane.showConfirmDialog(this,
                        String.format(Localization.DIALOG_CONFIRM_DELETE_DESC, currentAccount.getUsername()),
                        Localization.DIALOG_CONFIRM_DELETE_TITLE,
                        JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION){
                    try {
                        database.remove(currentAccount);
                        database.endTransaction();

                        initialize();
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_ENGINE);
                    }
                }
            } else
                JOptionPane.showMessageDialog(this, Localization.DIALOG_ERROR_NO_SELECTED);
        }
    }

}
