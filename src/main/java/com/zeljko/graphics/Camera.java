package com.zeljko.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
    private GLU glu;
    private int windowWidth, windowHeight;
    private double fov = 45.0;
    private double near = 0.1;
    private double far = 1000.0;

    private double radius = 10.0;
    private double theta = 0.0;
    private double phi = 0.0;

    private double centerX = 0, centerY = 0, centerZ = 0;

    public Camera(int windowWidth, int windowHeight) {
        this.glu = new GLU();
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void setupViewport(GL2 gl) {
        gl.glViewport(0, 0, windowWidth, windowHeight);
    }

    public void setupProjection(GL2 gl) {
        float aspect = (float) windowWidth / windowHeight;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fov, aspect, near, far);
    }

    public void applyViewTransform(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        double x = centerX + radius * Math.sin(theta) * Math.cos(phi);
        double y = centerY + radius * Math.sin(phi);
        double z = centerZ + radius * Math.cos(theta) * Math.cos(phi);

        glu.gluLookAt(x, y, z, centerX, centerY, centerZ, 0, 1, 0);
    }

    public void rotate(double deltaTheta, double deltaPhi) {
        theta += deltaTheta;
        phi += deltaPhi;

        if (phi > Math.PI / 2 - 0.1) phi = Math.PI / 2 - 0.1;
        if (phi < -Math.PI / 2 + 0.1) phi = -Math.PI / 2 + 0.1;
    }

    public void zoom(double delta) {
        radius += delta;
        if (radius < 1) radius = 1;
        if (radius > 100) radius = 100;
    }

    public void setCenter(double x, double y, double z) {
        this.centerX = x;
        this.centerY = y;
        this.centerZ = z;
    }
}