package com.zeljko.lego;

import com.zeljko.lego.core.LegoActuator;
import com.zeljko.lego.ui.LegoGUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 562;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lego Builder");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LegoGUI gui = new LegoGUI(frame);
        LegoActuator actuator = new LegoActuator(gui.getFrame());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actuator.stop();
            }
        });

        frame.pack();
        frame.setVisible(true);
        actuator.start();
    }
}
