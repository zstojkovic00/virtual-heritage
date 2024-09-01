package com.zeljko.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import static com.zeljko.utils.Constants.*;

public class Camera {
    private GLU glu;
    private int windowWidth, windowHeight;

    // Field of view
    private double fov = 45.0;
    // Nearest point the camera can see
    private double near = 0.1;
    // Farthest point the camera can see
    private double far = 1000.0;
    // Distance of camera from the center of the screen
    private double radius = 10.0;
    // Angle of rotation around y axis
    private double theta = 0.0;
    // Angle of rotation around X axis
    private double phi = 0.0;
    // Center of the scene
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
        // set up perspective projection
        glu.gluPerspective(fov, aspect, near, far);
    }

    public void applyViewTransform(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Calculate camera position in spherical coordinates
        double x = centerX + radius * Math.sin(theta) * Math.cos(phi);
        double y = centerY + radius * Math.sin(phi);
        double z = centerZ + radius * Math.cos(theta) * Math.cos(phi);

        // Set up camera view
        glu.gluLookAt(x, y, z, centerX, centerY, centerZ, 0, 1, 0);
    }

    public void rotateByMouse(int deltaX, int deltaY) {
        // Rotate camera based on mouse movement
        rotate(Math.toRadians(deltaX * ROTATION_SPEED), Math.toRadians(-deltaY * ROTATION_SPEED));
    }

    public void zoomByWheel(double wheelRotation) {
        zoom(wheelRotation * ZOOM_SPEED);
    }

    public void rotate(double deltaTheta, double deltaPhi) {
        theta += deltaTheta; // update horizontal rotation angle
        phi += deltaPhi; // update vertical rotation angle

        // limit vertical rotation to prevent camera flipping
        if (phi > Math.PI / 2 - 0.1) phi = Math.PI / 2 - 0.1;
        if (phi < -Math.PI / 2 + 0.1) phi = -Math.PI / 2 + 0.1;
    }

    public void zoom(double delta) {
        radius += delta; // change camera distance from scene center
        // limit zoom to prevent getting too close or too far
        if (radius < 1) radius = 1;
        if (radius > 100) radius = 100;
    }

    public void setCenter(double x, double y, double z) {
        this.centerX = x;
        this.centerY = y;
        this.centerZ = z;
    }
}