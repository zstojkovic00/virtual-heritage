package com.zeljko.lego.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

public class LegoActuator implements GLEventListener {
    private GLCanvas canvas;
    private FPSAnimator animator;
    private JFrame frame;

    public LegoActuator(JFrame frame) {
        this.frame = frame;
        initOpenGL();
    }

    private void initOpenGL() {
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDepthBits(24);
        capabilities.setDoubleBuffered(true);

        canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);
        canvas.setPreferredSize(frame.getSize());
        frame.add(canvas);

        animator = new FPSAnimator(canvas, 60);
    }

    public void start() {
        animator.start();
    }

    public void stop() {
        animator.stop();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {}
}
