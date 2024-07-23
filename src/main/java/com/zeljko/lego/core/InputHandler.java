package com.zeljko.lego.core;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class InputHandler implements KeyListener, ActionListener {

    private List<LegoModel> legoModels = new ArrayList<>();
    private final LegoActuator legoActuator;
    private boolean shouldDrawLegoBrick = false;
    private final double blueprintConstant;
    private int currentModelIndex = -1;

    public InputHandler(LegoActuator legoActuator, double blueprintConstant) {
        this.legoActuator = legoActuator;
        this.blueprintConstant = blueprintConstant;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentModelIndex == -1 || legoModels.isEmpty()) return;
        LegoModel currentModel = legoModels.get(currentModelIndex);
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_Z:
                currentModel.scale(1.1f);
                break;
            case KeyEvent.VK_X:
                currentModel.scale(0.9f);
                break;
            case KeyEvent.VK_I:
                currentModel.translate(0, 0, blueprintConstant);
                break;
            case KeyEvent.VK_O:
                currentModel.translate(0, 0, -blueprintConstant);
                break;
            case KeyEvent.VK_J:
                currentModel.translate(blueprintConstant, 0, 0);
                break;
            case KeyEvent.VK_K:
                currentModel.translate(-blueprintConstant, 0, 0);
                break;
            case KeyEvent.VK_N:
                currentModel.translate(0, blueprintConstant, 0);
                break;
            case KeyEvent.VK_M:
                currentModel.translate(0, -blueprintConstant, 0);
                break;
            case KeyEvent.VK_LEFT:
                currentModel.rotate(1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                currentModel.rotate(-1, 0);
                break;
            case KeyEvent.VK_UP:
                currentModel.rotate(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                currentModel.rotate(0, 1);
                break;
            case KeyEvent.VK_Q:
                if (!legoModels.isEmpty()) {
                    legoModels.get(currentModelIndex).setSelected(false);
                    currentModelIndex = (currentModelIndex + 1) % legoModels.size();
                    legoModels.get(currentModelIndex).setSelected(true);
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
    public void actionPerformed(ActionEvent e) {
        LegoModel newModel;
        String legoType = e.getActionCommand();
        if ("rectangle".equals(legoType)) {
            newModel = new LegoModel(5.0, 1.5, 2.0);
        } else {
            System.out.println("Not implemented yet");
            return;
        }
        newModel.translate(Math.random() * 10 - 5,
                Math.random() * 10 - 5,
                Math.random() * 10 - 5);

        legoModels.add(newModel);
        if (currentModelIndex == -1) {
            currentModelIndex = 0;
            newModel.setSelected(true);
        } else {
            legoModels.get(currentModelIndex).setSelected(false);
            currentModelIndex = legoModels.size() - 1;
            newModel.setSelected(true);
        }
        setShouldDrawLegoBrick(true);
    }

    public LegoModel getCurrentModel() {
        if (currentModelIndex >= 0 && currentModelIndex < legoModels.size()) {
            return legoModels.get(currentModelIndex);
        }
        return null;
    }

    public List<LegoModel> getLegoModels() {
        return legoModels;
    }


    public boolean isShouldDrawLegoBrick() {
        return shouldDrawLegoBrick;
    }

    public void setShouldDrawLegoBrick(boolean shouldDrawLegoBrick) {
        this.shouldDrawLegoBrick = shouldDrawLegoBrick;
    }
}