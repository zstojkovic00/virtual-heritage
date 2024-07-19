package com.zeljko.lego.ui;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

@Getter
public class LegoGUI {

    private JFrame frame;
    private JPanel legoFigures;
    private JMenuBar menuBar;

    private static final int BRICK_SIZE = 120;
    private static final Color NORMAL_COLOR = new Color(200, 200, 200);
    private static final Color HOVER_COLOR = new Color(100, 100, 100);
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);

    public LegoGUI(JFrame frame, Consumer<Boolean> onClick) {
        this.frame = frame;
        initUI(onClick);
    }

    private void initUI(Consumer<Boolean> onClick) {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        legoFigures = new JPanel(new GridLayout(1, 2, 10, 10));
        legoFigures.setBackground(BACKGROUND_COLOR);
        legoFigures.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel legoRectangle = createLegoPanel(resizeImage("src/main/java/resources/images/lego-rectangle.png", 150, 150), "N", onClick);
        JPanel legoCylinder = createLegoPanel(resizeImage("src/main/java/resources/images/lego-cylinder.png", 225, 135), "N", onClick);
        legoFigures.add(legoRectangle);
        legoFigures.add(legoCylinder);
    }

    private JPanel createLegoPanel(ImageIcon image, String number, Consumer<Boolean> onClick) {
        JPanel legoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel imageLabel = new JLabel(image);
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 20));

        legoPanel.add(imageLabel);
        legoPanel.add(numberLabel);
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

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("test");
                onClick.accept(true);

            }
        });

        return legoPanel;
    }

    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}