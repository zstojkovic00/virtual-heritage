package com.zeljko.ui;


import com.zeljko.core.Actuator;
import com.zeljko.utils.ShapeType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static com.zeljko.utils.Constants.*;

public class Gui {
    private JFrame frame;
    private JPanel panel;
    private JMenuBar menuBar;
    private Actuator actuator;

    public Gui(JFrame frame, ActionListener actionListener, Actuator actuator) {
        this.frame = frame;
        this.actuator = actuator;
        initUI(actionListener);
    }

    private void initUI(ActionListener actionListener) {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        JButton checkAlignmentButton = new JButton("Check Alignment");

        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);


        checkAlignmentButton.addActionListener(e -> {
            boolean aligned = actuator.checkAlignment();
            JOptionPane.showMessageDialog(frame,
                    aligned ? "All models are aligned with the blueprint!" : "Some models are not aligned!",
                    "Alignment Check",
                    aligned ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        });


        panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel rectangle = createPanel(resizeImage(createImagePath(IMAGE_PATH, ShapeType.RECTANGLE),
                150, 150), actionListener, ShapeType.RECTANGLE.name());

        JPanel cylinder = createPanel(resizeImage(createImagePath(IMAGE_PATH, ShapeType.CYLINDER),
                225, 135), actionListener, ShapeType.CYLINDER.name());

        panel.add(rectangle);
        panel.add(cylinder);
        panel.add(checkAlignmentButton);
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

    public String createImagePath(String path, ShapeType type) {
        return path + type.name().toLowerCase() + ".png";
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