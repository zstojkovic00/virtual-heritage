package com.zeljko.lego.ui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class LegoGUI {
    @Getter
    private JFrame frame;
    private JPanel windowPanel, topPanel, bottomPanel;
    private JButton removeButton, addButton, finishButton, quitButton, helpButton, newGameButton;
    private JCheckBox lightOnOff, ambientLight, globalAmbientLight, specularLight, diffuseLight;
    private JLabel label;

    public LegoGUI(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        windowPanel = new JPanel(new GridLayout(2, 1));
        topPanel = new JPanel();
        bottomPanel = new JPanel();

        // Initialize buttons
        removeButton = new JButton("Remove");
        addButton = new JButton("Add");
        finishButton = new JButton("Finish");
        quitButton = new JButton("Quit");
        helpButton = new JButton("Help");
        newGameButton = new JButton("New Game");

        // Initialize checkboxes
        lightOnOff = new JCheckBox("Turn Light On/Off", true);
        ambientLight = new JCheckBox("Ambient Light", false);
        globalAmbientLight = new JCheckBox("Global Ambient Light", false);
        specularLight = new JCheckBox("Specular Light", false);
        diffuseLight = new JCheckBox("Diffuse Light", false);

        // Initialize label
        label = new JLabel("Click On The Help Button To Read Game Instructions");

        // Add components to panels
        topPanel.add(removeButton);
        topPanel.add(addButton);
        topPanel.add(finishButton);
        topPanel.add(quitButton);
        topPanel.add(helpButton);
        topPanel.add(newGameButton);
        topPanel.add(lightOnOff);
        topPanel.add(ambientLight);
        topPanel.add(globalAmbientLight);
        topPanel.add(specularLight);
        topPanel.add(diffuseLight);

        bottomPanel.add(label);

        windowPanel.add(topPanel);
        windowPanel.add(bottomPanel);

        frame.add(windowPanel, BorderLayout.SOUTH);
    }

}
