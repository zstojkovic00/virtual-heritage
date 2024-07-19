package com.zeljko.lego.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private final LegoModel legoModel;
    private final double blueprintConstant;

    public InputHandler(LegoModel legoModel, double blueprintConstant) {
        this.legoModel = legoModel;
        this.blueprintConstant = blueprintConstant;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_Z:
                legoModel.scale(1.1f);
                break;
            case KeyEvent.VK_X:
                legoModel.scale(0.9f);
                break;
            case KeyEvent.VK_I:
                legoModel.translate(0, 0, blueprintConstant);
                break;
            case KeyEvent.VK_O:
                legoModel.translate(0, 0, -blueprintConstant);
                break;
            case KeyEvent.VK_J:
                legoModel.translate(blueprintConstant, 0, 0);
                break;
            case KeyEvent.VK_K:
                legoModel.translate(-blueprintConstant, 0, 0);
                break;
            case KeyEvent.VK_N:
                legoModel.translate(0, blueprintConstant, 0);
                break;
            case KeyEvent.VK_M:
                legoModel.translate(0, -blueprintConstant, 0);
                break;
            case KeyEvent.VK_LEFT:
                legoModel.rotate(1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                legoModel.rotate(-1, 0);
                break;
            case KeyEvent.VK_UP:
                legoModel.rotate(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                legoModel.rotate(0, 1);
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
}