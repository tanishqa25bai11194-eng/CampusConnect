package com.campusconnect.ui;

import javax.swing.SwingUtilities;

public class CampusConnectGUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainFrame frame = new MainFrame();

            frame.setVisible(true);
        });
    }
}