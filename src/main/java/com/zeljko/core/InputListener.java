package com.zeljko.core;

import com.zeljko.graphics.Camera;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.ui.GuiNotifier;
import com.zeljko.utils.ShapeType;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.*;

import static com.zeljko.utils.Constants.UNIT_MOVEMENT;

public class InputListener implements KeyListener, ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private final GameState gameState;

    @Setter
    private GuiNotifier notifier;
    private final Camera camera;
    private boolean isLeftMouseButtonPressed = false;
    private int lastMouseX;
    private int lastMouseY;

    public InputListener(GameState gameState, Camera camera) {
        this.gameState = gameState;
        this.camera = camera;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Model3D currentModel = gameState.getCurrentModel();
        if (currentModel == null) return;


        switch (key) {

            // Dimensions
            case KeyEvent.VK_1:
                currentModel.setWidth(currentModel.getWidth() - UNIT_MOVEMENT);
                break;
            case KeyEvent.VK_2:
                currentModel.setWidth(currentModel.getWidth() + UNIT_MOVEMENT);
                break;
            case KeyEvent.VK_3:
                currentModel.setHeight(currentModel.getHeight() - UNIT_MOVEMENT);
                break;
            case KeyEvent.VK_4:
                currentModel.setHeight(currentModel.getHeight() + UNIT_MOVEMENT);
                break;
            case KeyEvent.VK_5:
                currentModel.setDepth(currentModel.getDepth() - UNIT_MOVEMENT);
                break;
            case KeyEvent.VK_6:
                currentModel.setDepth(currentModel.getDepth() + UNIT_MOVEMENT);
                break;

            // Translation
            case KeyEvent.VK_A:
                currentModel.translate(-UNIT_MOVEMENT, 0, 0);
                break;
            case KeyEvent.VK_D:
                currentModel.translate(UNIT_MOVEMENT, 0, 0);
                break;
            case KeyEvent.VK_W:
                currentModel.translate(0, UNIT_MOVEMENT, 0);
                break;
            case KeyEvent.VK_S:
                currentModel.translate(0, -UNIT_MOVEMENT, 0);
                break;
            case KeyEvent.VK_Q:
                currentModel.translate(0, 0, UNIT_MOVEMENT);
                break;
            case KeyEvent.VK_E:
                currentModel.translate(0, 0, -UNIT_MOVEMENT);
                break;

            // Rotation
            case KeyEvent.VK_LEFT:
                currentModel.rotate(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                currentModel.rotate(0, 1);
                break;
            case KeyEvent.VK_UP:
                currentModel.rotate(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                currentModel.rotate(1, 0);
                break;

            // Scale
            case KeyEvent.VK_Z:
                currentModel.scale(1.1f);
                break;
            case KeyEvent.VK_X:
                currentModel.scale(0.9f);
                break;

            // Select model
            case KeyEvent.VK_O:
                gameState.selectNextModel();
                break;

            case KeyEvent.VK_T:
                currentModel.nextTexture();
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String type = e.getActionCommand();
        ShapeType shapeType = ShapeType.valueOf(type);
        boolean isAdded = gameState.addModel(shapeType);
        if (!isAdded) {
            notifier.notify("Cannot add more " + shapeType, "Model Constraint", JOptionPane.WARNING_MESSAGE);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isLeftMouseButtonPressed = true;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isLeftMouseButtonPressed = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isLeftMouseButtonPressed) {
            int deltaX = e.getX() - lastMouseX;
            int deltaY = e.getY() - lastMouseY;

            camera.rotateByMouse(deltaX, deltaY);

            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.zoomByWheel(e.getPreciseWheelRotation());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}