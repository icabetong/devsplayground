package com.clsu.devsplayground.core.components;

import com.clsu.devsplayground.core.Localization;

import javax.swing.*;

public class CoreFrame extends JFrame {

    private JFrame ancestorFrame;

    public CoreFrame(JFrame ancestorFrame) {
        super(Localization.TITLE_GENERIC);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ancestorFrame = ancestorFrame;
    }

    protected void back() {
        if (ancestorFrame != null){
            ancestorFrame.setVisible(true);
            this.dispose();
        }
    }

    public void invoke(){
        setVisible(true);
    }

}
