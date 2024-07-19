package com.zeljko.lego.core;

import com.jogamp.opengl.GL2;
import com.zeljko.lego.graphics.Shape;

public class LegoModel {
    private double width;
    private double height;
    private double depth;
    private double translateX;
    private double translateY;
    private double translateZ;
    private int rotationX;
    private int rotationY;
    private float scale;

    public LegoModel(double width, double height, double depth) {
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

    public void scale(float factor) {
        this.scale *= factor;
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

}
