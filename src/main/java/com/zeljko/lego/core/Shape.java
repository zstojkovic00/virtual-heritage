package com.zeljko.lego.core;

import com.jogamp.opengl.GL2;

public class Shape {

    public static void cube(GL2 gl, double side, boolean makeTextureCoordinate) {
        // push the current matrix down in the stack
        gl.glPushMatrix();

        gl.glPushMatrix();
        gl.glRotatef(0, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTextureCoordinate);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTextureCoordinate);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTextureCoordinate);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(270, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTextureCoordinate);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90, 1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTextureCoordinate);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTextureCoordinate);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }


    public static void square(GL2 gl, double side, boolean makeTextureCoordinate) {

        double radius = side / 2;
        gl.glBegin(GL2.GL_POLYGON);

        // vector for lighting calculation
        gl.glNormal3f(0, 0, 1);

        // top left corner of a square
        if (makeTextureCoordinate) {
            gl.glTexCoord2d(0, 1);
        }
        gl.glVertex2d(-radius, radius);

        // bottom left corner of a square
        if (makeTextureCoordinate) {
            gl.glTexCoord2d(0, 0);
        }
        gl.glVertex2d(-radius, -radius);

        // bottom right corner of a square
        if (makeTextureCoordinate) {
            gl.glTexCoord2d(1, 0);
        }
        gl.glVertex2d(radius, -radius);

        // top right corner of a square
        if (makeTextureCoordinate) {
            gl.glTexCoord2d(1, 1);
        }
        gl.glVertex2d(radius, radius);

        gl.glEnd();
    }

}
