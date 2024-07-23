package com.zeljko.lego.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.zeljko.lego.graphics.Camera;
import com.zeljko.lego.graphics.Light;

import com.zeljko.lego.ui.LegoGUI;

import javax.swing.*;
import java.awt.*;


import static com.zeljko.lego.utils.Constants.WINDOW_HEIGHT;
import static com.zeljko.lego.utils.Constants.WINDOW_WIDTH;

public class LegoActuator implements GLEventListener {
    private LegoGUI gui;
    private final Camera camera;
    private final InputHandler inputHandler;
    private final Light lightingManager;
    private GLCanvas canvas;
    private FPSAnimator animator;


    public LegoActuator(JFrame frame) {
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setAlphaBits(8);
        caps.setDepthBits(24);
        caps.setDoubleBuffered(true);
        caps.setStencilBits(8);

        this.lightingManager = new Light();
        this.camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
        inputHandler = new InputHandler(this, 0.1);

        SwingUtilities.invokeLater(() -> {
            canvas = new GLCanvas(caps);
            canvas.setPreferredSize(new Dimension(1000, 562));
            canvas.addGLEventListener(this);
            canvas.addKeyListener(inputHandler);

            animator = new FPSAnimator(canvas, 60);

            gui = new LegoGUI(frame, inputHandler);
            frame.getContentPane().add(canvas, BorderLayout.CENTER);
            frame.setJMenuBar(gui.getMenuBar());
            frame.add(gui.getLegoFigures(), BorderLayout.SOUTH);


            camera.lookAt(-5, 0, 3,
                    0, 0, 0,
                    0, 1, 0);

            camera.setScale(15);

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

        lightingManager.setupLighting(gl);
        GLU.createGLU(gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        camera.setupViewport(gl);
        camera.setupProjection(gl);
        camera.setObserver();


        for (LegoModel model : inputHandler.getLegoModels()) {
            model.draw(gl);
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

}
