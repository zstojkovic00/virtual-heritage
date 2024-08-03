package com.zeljko.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.zeljko.graphics.Blueprint;
import com.zeljko.graphics.Camera;
import com.zeljko.graphics.Light;

import com.zeljko.graphics.Model3D;
import com.zeljko.ui.Gui;

import java.util.List;

import javax.swing.*;
import java.awt.*;


import static com.zeljko.utils.Constants.WINDOW_HEIGHT;
import static com.zeljko.utils.Constants.WINDOW_WIDTH;

public class Actuator implements GLEventListener {
    private Gui gui;
    private final Camera camera;
    private final InputListener inputListener;
    private final Light light;
    private GLCanvas canvas;
    private FPSAnimator animator;
    private Blueprint currentBlueprint;

    public Actuator(JFrame frame) {
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setAlphaBits(8);
        caps.setDepthBits(24);
        caps.setDoubleBuffered(true);
        caps.setStencilBits(8);

        this.light = new Light();
        this.camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
        inputListener = new InputListener();

        SwingUtilities.invokeLater(() -> {
            canvas = new GLCanvas(caps);
            canvas.setPreferredSize(new Dimension(1000, 562));
            canvas.addGLEventListener(this);
            canvas.addKeyListener(inputListener);

            animator = new FPSAnimator(canvas, 60);

            gui = new Gui(frame, inputListener, this);
            frame.getContentPane().add(canvas, BorderLayout.CENTER);
            frame.setJMenuBar(gui.getMenuBar());
            frame.add(gui.getPanel(), BorderLayout.SOUTH);


            camera.lookAt(-5, 0, 3,
                    0, 0, 0,
                    0, 1, 0);

            camera.setScale(15);
            this.currentBlueprint = BlueprintFactory.createHouseBlueprint();

            frame.pack();
            frame.setVisible(true);
            animator.start();
        });
    }

    public void stop() {
        animator.stop();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);

        light.setupLighting(gl);
        GLU.createGLU(gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        camera.setupViewport(gl);
        camera.setupProjection(gl);
        camera.setObserver();

        // draw blueprint
        currentBlueprint.drawBlueprint(gl);

        for (Model3D model3D : inputListener.getModels()) {
            model3D.draw(gl);
        }

        camera.apply(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    public boolean checkAlignment() {
        if (currentBlueprint == null || inputListener.getModels().isEmpty()) return false;

        List<Model3D> userModels = inputListener.getModels();
        return currentBlueprint.areAllModelsAligned(userModels);
    }
}
