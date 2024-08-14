package com.zeljko.core;

import com.zeljko.graphics.Camera;
import com.zeljko.graphics.Model3D;
import com.zeljko.utils.ShapeType;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static com.zeljko.utils.Constants.UNIT_MOVEMENT;

// separate class for Mouse and Key Listener?
public class InputListener implements KeyListener, ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private List<Model3D> models = new ArrayList<>();
    private boolean shouldDraw = false;
    private int currentModelIndex = -1;
    private final Camera camera;
    private boolean isLeftMouseButtonPressed = false;
    private static final double ROTATION_SPEED = 0.1;
    private static final double ZOOM_SPEED = 0.5;
    private int lastMouseX;
    private int lastMouseY;

    public InputListener(Camera camera) {
        this.camera = camera;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (currentModelIndex == -1 || models.isEmpty()) return;
        Model3D currentModel = models.get(currentModelIndex);
        int key = e.getKeyCode();


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
//            case KeyEvent.VK_5:
//                currentModel.setDepth(currentModel.getDepth() - UNIT_MOVEMENT);
//                break;
//            case KeyEvent.VK_6:
//                currentModel.setDepth(currentModel.getDepth() + UNIT_MOVEMENT);
//                break;

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
            case KeyEvent.VK_TAB:
                if (!models.isEmpty()) {
                    models.get(currentModelIndex).setSelected(false);
                    currentModelIndex = (currentModelIndex + 1) % models.size();
                    models.get(currentModelIndex).setSelected(true);
                    System.out.println("Selected model " + currentModelIndex);
                }
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
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

            camera.rotate(Math.toRadians(deltaX * ROTATION_SPEED), Math.toRadians(-deltaY * ROTATION_SPEED));

            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.zoom(e.getPreciseWheelRotation() * ZOOM_SPEED);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Model3D newModel;
        String type = e.getActionCommand();
        System.out.println(type);

        if (ShapeType.CUBOID.name().equalsIgnoreCase(type)) {
            newModel = new Model3D(5.0, 1.5, 2.0, ShapeType.CUBOID);
        } else if (ShapeType.CYLINDER.name().equalsIgnoreCase(type)) {
            newModel = new Model3D(1.0, 2.0, 1.0, ShapeType.CYLINDER);
        } else {
            System.out.println("Not implemented yet");
            return;
        }
        newModel.translate(Math.random() * 10 - 5,
                Math.random() * 10 - 5,
                Math.random() * 10 - 5);

        models.add(newModel);
        if (currentModelIndex == -1) {
            currentModelIndex = 0;
            newModel.setSelected(true);
        } else {
            models.get(currentModelIndex).setSelected(false);
            currentModelIndex = models.size() - 1;
            newModel.setSelected(true);
        }
        setShouldDraw(true);
    }

    public List<Model3D> getModels() {
        return models;
    }

    public Model3D getCurrentModel() {
        if (currentModelIndex >= 0 && currentModelIndex < models.size()) {
            return models.get(currentModelIndex);
        }
        return null;
    }

    public void setModels(List<Model3D> models) {
        this.models = models;
    }

    public boolean isShouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    public int getCurrentModelIndex() {
        return currentModelIndex;
    }

    public void setCurrentModelIndex(int currentModelIndex) {
        this.currentModelIndex = currentModelIndex;
    }

}