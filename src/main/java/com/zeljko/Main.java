package com.zeljko;

import com.zeljko.core.Actuator;
import com.zeljko.utils.Constants;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Virtual Heritage");
        frame.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Actuator actuator = new Actuator(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actuator.stop();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
