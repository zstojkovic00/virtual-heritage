package com.zeljko.ui;


import com.zeljko.core.GameActuator;
import com.zeljko.core.InputListener;
import com.zeljko.utils.BlueprintType;
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
    private JToolBar toolBar;
    private JButton nextLevelButton, checkAlignmentButton, autocompleteButton, showBlueprintButton;
    private JLabel blueprintImage;
    private JDialog blueprintImageDialog;
    private JCheckBox  lightOnOff, globalAmbientLight, ambientLight, diffuseLight, specularLight;


    private GameActuator gameActuator;
    private Map<String, JLabel> modelCount = new HashMap<>();

    public Gui(JFrame frame, InputListener inputListener, GameActuator gameActuator) {
        this.frame = frame;
        this.gameActuator = gameActuator;
        inputListener.setNotifier(this);
        initUI(inputListener);
    }

    private void initUI(InputListener inputListener) {
        initMenuBar();
        initToolBar();
        initPanel(inputListener);
    }

    @Override
    public void notify(String message, String reason, int jOptionPane) {
        JOptionPane.showMessageDialog(frame, message, reason, jOptionPane);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exitGame = new JMenuItem("Exit");


        fileMenu.add(newGame);
        fileMenu.add(exitGame);
        menuBar.add(fileMenu);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel lightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        blueprintImage = new JLabel();
        checkAlignmentButton = new JButton("Check Alignment");
        autocompleteButton = new JButton("Autocomplete");
        showBlueprintButton = new JButton("Show Blueprint");

        lightOnOff = new JCheckBox("Turn Light On/Off", true);
        ambientLight = new JCheckBox("Ambient Light", false);
        globalAmbientLight = new JCheckBox("Global Ambient Light", false);
        specularLight = new JCheckBox("Specular Light", false);
        diffuseLight = new JCheckBox("Diffuse Light", false);

        nextLevelButton = new JButton("Next Level");
        nextLevelButton.setVisible(false);

        autocompleteButton.addActionListener(e -> gameActuator.startAutocomplete());
        checkAlignmentButton.addActionListener(e -> checkAlignment());
        showBlueprintButton.addActionListener(e -> showFullSizeImage());
        nextLevelButton.addActionListener(e -> gameActuator.nextLevel());

        lightOnOff.addActionListener(e -> gameActuator.updateLighting());
        ambientLight.addActionListener(e -> gameActuator.updateLighting());
        globalAmbientLight.addActionListener(e -> gameActuator.updateLighting());
        specularLight.addActionListener(e -> gameActuator.updateLighting());
        diffuseLight.addActionListener(e -> gameActuator.updateLighting());

        buttonPanel.add(checkAlignmentButton);
        buttonPanel.add(autocompleteButton);
        buttonPanel.add(showBlueprintButton);
        buttonPanel.add(nextLevelButton);

        lightPanel.add(lightOnOff);
        lightPanel.add(ambientLight);
        lightPanel.add(globalAmbientLight);
        lightPanel.add(specularLight);
        lightPanel.add(diffuseLight);

        toolBar.add(buttonPanel);
        toolBar.add(lightPanel);
    }

    private void initPanel(InputListener inputListener) {

        panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel cuboid = createPanel(resizeImage(createImagePath(IMAGE_PATH, ShapeType.CUBOID),
                150, 150), inputListener, ShapeType.CUBOID.name());

        JPanel cylinder = createPanel(resizeImage(createImagePath(IMAGE_PATH, ShapeType.CYLINDER),
                150, 135), inputListener, ShapeType.CYLINDER.name());

        panel.add(cuboid);
        panel.add(cylinder);
    }

    private JPanel createPanel(ImageIcon image, InputListener inputListener, String type) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel imageLabel = new JLabel(image);
        JLabel numberLabel = new JLabel();
        numberLabel.setFont(new Font("Arial", Font.BOLD, 20));

        if (type.equals(ShapeType.CUBOID.name())) {
            numberLabel.setText(String.valueOf(gameActuator.getGameState().getCurrentBlueprint().getNumberOfCuboids()));
        } else if (type.equals(ShapeType.CYLINDER.name())) {
            numberLabel.setText(String.valueOf(gameActuator.getGameState().getCurrentBlueprint().getNumberOfCylinders()));
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

    private void checkAlignment() {
        boolean aligned = gameActuator.checkAlignment();
        JOptionPane.showMessageDialog(frame,
                aligned ? "All models are aligned with the blueprint!" : "Some models are not aligned, or textures are incorrect",
                "Alignment Check",
                aligned ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    public void updateModelCount() {
        for (ShapeType type : ShapeType.values()) {
            long count = gameActuator.getGameState().getRemainingModelCount(type);
            modelCount.get(type.name()).setText(Long.toString(count));
        }
    }

    private void showFullSizeImage() {
        JLabel imageLabel = new JLabel();
        if (blueprintImageDialog == null) {
            blueprintImageDialog = new JDialog(frame, "Blueprint Example", true);
            blueprintImageDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            JScrollPane scrollPane = new JScrollPane(imageLabel);
            blueprintImageDialog.add(scrollPane);

            blueprintImageDialog.setSize(450, 450);
            blueprintImageDialog.setLocationRelativeTo(frame);
        }

        ImageIcon icon = (ImageIcon) blueprintImage.getIcon();
        if (icon != null) {
            imageLabel.setIcon(icon);
        }

        blueprintImageDialog.setVisible(true);
    }

    public void setBlueprintImage(BlueprintType type) {
        String imagePath = createImagePath(IMAGE_PATH, type);
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image image = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            blueprintImage.setIcon(new ImageIcon(image));
        } else {
            System.out.println("Failed to load image: " + imagePath);
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

    public String createImagePath(String path, BlueprintType type) {
        return path + type.name().toLowerCase() + ".png";
    }

    public void showNextLevelButton() {
        nextLevelButton.setVisible(true);
    }

    public void hideNextLevelButton() {
        nextLevelButton.setVisible(false);
    }

    public void showAllLevelCompleteMessage() {
        JOptionPane.showMessageDialog(null, "Unfortunately, we don't have any more levels. You've completed them all!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}