package com.zeljko.lego.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.zeljko.lego.graphics.Face;
import com.zeljko.lego.graphics.Model;
import com.zeljko.lego.graphics.OBJLoader;
import com.zeljko.lego.graphics.math.Vector3f;
import com.zeljko.lego.ui.LegoGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LegoActuator implements GLEventListener {
    private GLCanvas canvas;
    private FPSAnimator animator;
    private LegoGUI gui;
    private JFrame frame;

    private Model model;

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
        setupLighting(gl);
        loadModel();
    }

    private void setupLighting(GL2 gl) {
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);

        float[] ambient = {0.1f, 0.1f, 0.1f, 1f};
        float[] diffuse = {1f, 1f, 1f, 1f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);
    }

    private void loadModel() {
        try {
            model = OBJLoader.loadModel(new File("src/main/java/resources/textures/LegoBricks3.obj"));
        } catch (Exception e) {
            System.out.println("Error loading model: " + e.getMessage());
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        if (model != null) {
            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glColor3f(1.0f, 0.0f, 0.0f);
            for (Face face : model.faces) {
                Vector3f n1 = model.normals.get((int) face.normal.x - 1);
                gl.glNormal3f(n1.x, n1.y, n1.z);
                Vector3f v1 = model.vertices.get((int) face.vertex.x - 1);
                gl.glVertex3f(v1.x, v1.y, v1.z);

                Vector3f n2 = model.normals.get((int) face.normal.y - 1);
                gl.glNormal3f(n2.x, n2.y, n2.z);
                Vector3f v2 = model.vertices.get((int) face.vertex.y - 1);
                gl.glVertex3f(v2.x, v2.y, v2.z);

                Vector3f n3 = model.normals.get((int) face.normal.z - 1);
                gl.glNormal3f(n3.x, n3.y, n3.z);
                Vector3f v3 = model.vertices.get((int) face.vertex.z - 1);
                gl.glVertex3f(v3.x, v3.y, v3.z);
            }
            gl.glEnd();
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }
}
