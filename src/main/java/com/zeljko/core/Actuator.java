package com.zeljko.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import com.zeljko.graphics.Camera;
import com.zeljko.graphics.Light;
import com.zeljko.graphics.model.Blueprint;
import com.zeljko.ui.Gui;
import com.zeljko.utils.AlignmentChecker;
import com.zeljko.utils.BlueprintFactory;
import lombok.Getter;


import javax.swing.*;
import java.awt.*;


import static com.zeljko.utils.Constants.WINDOW_HEIGHT;
import static com.zeljko.utils.Constants.WINDOW_WIDTH;

public class Actuator implements GLEventListener {

    @Getter
    private final ApplicationState applicationState;
    private final InputListener inputListener;
    private final Camera camera;
    private Gui gui;
    private final Renderer renderer;
    private final Light light;
    private GLCanvas canvas;
    private FPSAnimator animator;

    public Actuator(JFrame frame) {
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setAlphaBits(8);
        caps.setDepthBits(24);
        caps.setDoubleBuffered(true);
        caps.setStencilBits(8);

        this.applicationState = new ApplicationState();
        Blueprint blueprint = BlueprintFactory.createTreeBlueprint();
        this.applicationState.setCurrentBlueprint(blueprint);
        this.camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.inputListener = new InputListener(applicationState, camera);
        this.light = new Light();
        this.renderer = new Renderer();


        SwingUtilities.invokeLater(() -> {
            canvas = new GLCanvas(caps);
            canvas.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            canvas.addGLEventListener(this);
            canvas.addKeyListener(inputListener);
            canvas.addMouseListener(inputListener);
            canvas.addMouseMotionListener(inputListener);
            canvas.addMouseWheelListener(inputListener);

            animator = new FPSAnimator(canvas, 60);
            gui = new Gui(frame, inputListener, this);
            frame.getContentPane().add(canvas, BorderLayout.CENTER);
            frame.setJMenuBar(gui.getMenuBar());
            frame.add(gui.getPanel(), BorderLayout.SOUTH);

            camera.setCenter(0, 0, 0);
            camera.zoom(-2);

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
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        camera.setupViewport(gl);
        camera.setupProjection(gl);
        camera.applyViewTransform(gl);

        if (applicationState.getCurrentBlueprint() != null) {
            renderer.renderScene(gl, applicationState.getCurrentBlueprint(), applicationState.getUserModels());
        }
    }

    public boolean checkAlignment() {
        return AlignmentChecker.areAllModelsAligned(applicationState.getCurrentBlueprint(), applicationState.getUserModels());
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

}