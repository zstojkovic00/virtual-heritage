package com.zeljko.ui;


import com.zeljko.core.Actuator;
import com.zeljko.core.InputListener;
import com.zeljko.utils.ShapeType;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import static com.zeljko.utils.Constants.*;

@Getter
@Setter
public class Gui implements GuiNotifier {

    private JFrame frame;
    private JPanel panel;
    private JMenuBar menuBar;
    private Actuator actuator;
    private Map<String, JLabel> modelCount = new HashMap<>();

    public Gui(JFrame frame, InputListener inputListener, Actuator actuator) {
        this.frame = frame;
        this.actuator = actuator;
        inputListener.setNotifier(this);
        initUI(inputListener);
    }

    private void initUI(InputListener inputListener) {
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


        JPanel cuboid = createPanel(resizeImage(createImagePath(IMAGE_PATH, ShapeType.CUBOID),
                150, 150), inputListener, ShapeType.CUBOID.name());

        JPanel cylinder = createPanel(resizeImage(createImagePath(IMAGE_PATH, ShapeType.CYLINDER),
                225, 135), inputListener, ShapeType.CYLINDER.name());

        panel.add(cuboid);
        panel.add(cylinder);
        panel.add(checkAlignmentButton);
    }

    private JPanel createPanel(ImageIcon image, InputListener inputListener, String type) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel imageLabel = new JLabel(image);
        JLabel numberLabel = new JLabel();
        numberLabel.setFont(new Font("Arial", Font.BOLD, 20));

        if (type.equals(ShapeType.CUBOID.name())) {
            numberLabel.setText(String.valueOf(actuator.getApplicationState().getCurrentBlueprint().getMaxCuboids()));
        } else if (type.equals(ShapeType.CYLINDER.name())) {
            numberLabel.setText(String.valueOf(actuator.getApplicationState().getCurrentBlueprint().getMaxCylinders()));
        }

        modelCount.put(type, numberLabel);

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
                inputListener.actionPerformed(new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, type));
                updateModelCount();
            }
        });

        return panel;
    }

    @Override
    public void notify(String message, String reason, int jOptionPane) {
        JOptionPane.showMessageDialog(frame, message, reason, jOptionPane);
    }

    private void updateModelCount() {
        for (ShapeType type : ShapeType.values()) {
            long count = actuator.getApplicationState().getRemainingModelCount(type);
            modelCount.get(type.name()).setText(Long.toString(count));
        }
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

}