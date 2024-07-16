package com.zeljko.lego.ui;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class LegoGUI {

    private JFrame frame;
    private JPanel legoFigures;
    private JMenuBar menuBar;

    private static final int BRICK_SIZE = 120;
    private static final Color NORMAL_COLOR = new Color(200, 200, 200);
    private static final Color HOVER_COLOR = new Color(100, 100, 100);
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);

    public LegoGUI(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        legoFigures = new JPanel(new GridLayout(4, 2, 10, 10));
        legoFigures.setBackground(BACKGROUND_COLOR);
        legoFigures.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 8; i++) {
            JPanel legoPanel = createLegoPanel(resizeImage());
            legoFigures.add(legoPanel);
        }
    }

    private JPanel createLegoPanel(ImageIcon image) {
        JPanel legoPanel = new JPanel();

        JLabel imageLabel = new JLabel(image);
        legoPanel.add(imageLabel, BorderLayout.CENTER);
        legoPanel.setBorder(BorderFactory.createLineBorder(NORMAL_COLOR, 2));

        legoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                legoPanel.setBorder(BorderFactory.createLineBorder(HOVER_COLOR, 2));
                legoPanel.revalidate();
                legoPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                legoPanel.setBorder(BorderFactory.createLineBorder(NORMAL_COLOR, 2));
                legoPanel.revalidate();
                legoPanel.repaint();
            }
        });

        return legoPanel;
    }

    private ImageIcon resizeImage() {
        ImageIcon originalIcon = new ImageIcon("src/main/java/resources/images/lego.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}