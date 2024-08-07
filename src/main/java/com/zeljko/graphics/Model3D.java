package com.zeljko.graphics;

import com.jogamp.opengl.GL2;

public class Model3D {
    private double width, height, depth;
    private double translateX, translateY, translateZ;
    private int rotationX, rotationY;
    private float scale;
    private boolean isSelected;

    public Model3D(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.translateX = 0;
        this.translateY = 0;
        this.translateZ = 0;
        this.rotationX = 0;
        this.rotationY = 0;
        this.scale = 1.0f;
    }

    public void translate(double x, double y, double z) {
        this.translateX += x;
        this.translateY += y;
        this.translateZ += z;
    }

    public void rotate(int x, int y) {
        this.rotationX += x;
        this.rotationY += y;
    }

    public void draw(GL2 gl) {

        gl.glPushMatrix();

        gl.glTranslated(translateX, translateY, translateZ);
        gl.glScalef(scale, scale, scale);
        gl.glRotated(rotationX, 1, 0, 0);
        gl.glRotated(rotationY, 0, 1, 0);

        gl.glColor3f(1, 1, 1);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        Shape.rectangle(gl, width, height, depth, true);

        gl.glPopMatrix();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void scale(float factor) {
        this.scale *= factor;
    }
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
    }

    public double getTranslateX() {
        return translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public double getTranslateZ() {
        return translateZ;
    }

    public int getRotationX() {
        return rotationX;
    }

    public int getRotationY() {
        return rotationY;
    }

    public float getScale() {
        return scale;
    }

}
