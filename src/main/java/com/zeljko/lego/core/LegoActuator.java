package com.zeljko.lego.core;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import com.zeljko.lego.ui.LegoGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class LegoActuator implements GLEventListener {
    private GLCanvas canvas;
    private FPSAnimator animator;
    private LegoGUI gui;
    private JFrame frame;
    private TextRenderer textRenderer;
    private TextRenderer textMatch;

    private int WINDOW_WIDTH = 640;
    private int WINDOW_HEIGHT = 480;

    private GLU glu;
    private final String[] textureFileNames = {
            "lego.png",
            "lego-back.png",
            "lego-side.png",
            "lego-up.png"
    };
    Texture[] textures = new Texture[textureFileNames.length];

    private Camera camera;

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


            camera = new Camera();
            camera.lookAt(-5, 0, 3, // look from camera XYZ
                    0, 0, 0, // look at the origin
                    0, 1, 0); // positive Y up vector (roll of the camera)

            // set the size of the shape while zoom in/out
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
        gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        setupLighting(gl);

        glu = new GLU();
        glu.createGLU(gl);

        for (int i = 0; i < textureFileNames.length; i++) {
            try {
                File file = new File("src/main/java/resources/images/" + textureFileNames[i]);
                System.out.println("Checking file: " + file.getAbsolutePath() + ", exists: " + file.exists());
                if (file.exists()) {
                    BufferedImage image = ImageIO.read(file);
                    ImageUtil.flipImageVertically(image);
                    textures[i] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, true);

                    textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                    textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                    System.out.println("Texture loaded: " + textureFileNames[i]);
                } else {
                    System.out.println("Failed to find texture file: " + textureFileNames[i]);
                }
            } catch (Exception e) {
                System.out.println("Error loading texture: " + textureFileNames[i]);
                e.printStackTrace();
            }
        }

        for (Texture texture : textures) {
            if (texture != null) {
                texture.enable(gl);
                texture.bind(gl);
            } else {
                System.out.println("A texture is null, check loading logs.");
            }
        }

        textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 12));
        textMatch = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
    }


    private void setupLighting(GL2 gl) {
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
        gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 100);

        float[] ambient = {0.1f, 0.1f, 0.1f, 1f};
        float[] diffuse = {1f, 1f, 1f, 1f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);


        gl.glClearDepth(1.0f); // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST); // enable depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL); // the type of depth test to do
        // perspective correction
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH); // blend colors nicely & have smooth lighting

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);


        drawLegoBrick(glAutoDrawable);

        camera.apply(gl);
//        lights(gl);

    }

    private void drawLegoBrick(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        int currentAngleOfVisibleField = 55; // camera
        float translateX = 0;
        float translateY = 0;
        float translateZ = 0;
        float scale = 0;
        int currentAngleOfRotationX = 0;
        int currentAngleOfRotationY = 0;


        // define the point of view of the blueprint
        gl.glViewport(WINDOW_WIDTH / 8, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(currentAngleOfVisibleField,
                1.f * WINDOW_WIDTH / WINDOW_HEIGHT, 1, 100);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        setObserver();

        // draw the blueprint
        gl.glPushMatrix();

        // change the orientation of the blueprint
        gl.glTranslated(0, 0, -5);
        gl.glScalef(1.0f, 1.0f, 1.0f);
        gl.glRotated(currentAngleOfRotationX, 1, 0, 0);
        gl.glRotated(currentAngleOfRotationY, 0, 1, 0);

        // add some texture on the blueprint
        gl.glColor3f(1, 1, 1);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textures[3].bind(gl);
        gl.glEnable(GL.GL_TEXTURE_2D);
        Shape.cube(gl, 3, true);
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glPopMatrix();
    }

    private void setObserver() {
        glu.gluLookAt(-1, 2, 10.0, // look from camera XYZ
                0.0, 0.0, 0.0, // look at the origin
                0.0, 1.0, 0.0); // positive Y up vector
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }
}
