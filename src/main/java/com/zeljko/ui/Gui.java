package com.zeljko.ui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static com.zeljko.utils.Constants.*;

public class Gui {
    private JFrame frame;
    private JPanel panel;
    private JMenuBar menuBar;


    public Gui(JFrame frame, ActionListener actionListener) {
        this.frame = frame;
        initUI(actionListener);
    }

    private void initUI(ActionListener actionListener) {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel rectangle = createPanel(resizeImage(IMAGE_PATH,
                150, 150), actionListener, "rectangle");

        JPanel cylinder = createPanel(resizeImage(IMAGE_PATH,
                225, 135), actionListener, "cylinder");

        panel.add(rectangle);
        panel.add(cylinder);
    }

    private JPanel createPanel(ImageIcon image, ActionListener actionListener, String type) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel imageLabel = new JLabel(image);
        JLabel numberLabel = new JLabel("N");
        numberLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(imageLabel);
        panel.add(numberLabel);
        panel.setBorder(BorderFactory.createLineBorder(GRAY, 2));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(BLACK, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(GRAY, 2));
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, type));
            }
        });

        return panel;
    }

    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }
}