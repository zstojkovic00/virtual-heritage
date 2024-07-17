package com.zeljko.lego.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.zeljko.lego.ui.LegoGUI;

import javax.swing.*;
import java.awt.*;

public class LegoActuator implements GLEventListener {
    private GLCanvas canvas;
    private FPSAnimator animator;
    private LegoGUI gui;
    private JFrame frame;

    public LegoActuator(JFrame frame) {
        this.frame = frame;
        initOpenGL();
    }

    private void initOpenGL() {
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setAlphaBits(8);
        caps.setDepthBits(24);
        caps.setDoubleBuffered(true);
        caps.setStencilBits(8);

        SwingUtilities.invokeLater(() -> {
            canvas = new GLCanvas(caps);
            canvas.setPreferredSize(new Dimension(1000, 562));
            canvas.addGLEventListener(this);
            animator = new FPSAnimator(canvas, 60);


            gui = new LegoGUI(frame);
            frame.getContentPane().add(canvas, BorderLayout.CENTER);
            frame.setJMenuBar(gui.getMenuBar());
            frame.add(gui.getLegoFigures(), BorderLayout.WEST);

            frame.pack();
            frame.setVisible(true);
            animator.start();
        });
    }

    public void start() {
        if (animator != null) {
            animator.start();
        }
    }

    public void stop() {
        animator.stop();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.95f, 0.95f, 1f, 0);

        gl.glEnable(GL2.GL_DEPTH_TEST); // enable the depth buffer to allow us to represent depth information in 3d space
        gl.glEnable(GL2.GL_LIGHTING); // enable lighting calculation
        gl.glEnable(GL2.GL_LIGHT0); // initial value for light (1,1,1,1) -> RGBA
        gl.glEnable(GL2.GL_NORMALIZE);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
        gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 100);


        // initialize different light sources
        float[] ambient = {0.1f, 0.1f, 0.1f, 1f};
        float[] diffuse = {1f, 1f, 1f, 1f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};

        // configure different light sources
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, specular, 0);

        gl.glClearDepth(1.0f); // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST); // enable depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL); // the type of depth test to do
        // perspective correction
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH); // blend colors nicely & have smooth lighting

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);


        // load your own 3d model:

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }
}
