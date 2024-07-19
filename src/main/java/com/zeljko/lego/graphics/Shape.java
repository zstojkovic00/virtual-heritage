package com.zeljko.lego.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Shape {

    public static void rectangle(GL2 gl, double width, double height, double depth, boolean makeTextureCoordinate) {
        gl.glPushMatrix();

            // Front
            gl.glPushMatrix();
            gl.glRotatef(0, 0, 1, 0);
            gl.glTranslated(0, 0, depth / 2);
            square(gl, width, height, makeTextureCoordinate);
            gl.glPopMatrix();

            // Right
            gl.glPushMatrix();
            gl.glRotatef(90, 0, 1, 0);
            gl.glTranslated(0, 0, width / 2);
            square(gl, depth, height, makeTextureCoordinate);
            gl.glPopMatrix();

            // Back
            gl.glPushMatrix();
            gl.glRotatef(180, 0, 1, 0);
            gl.glTranslated(0, 0, depth / 2);
            square(gl, width, height, makeTextureCoordinate);
            gl.glPopMatrix();

            // Left
            gl.glPushMatrix();
            gl.glRotatef(270, 0, 1, 0);
            gl.glTranslated(0, 0, width / 2);
            square(gl, depth, height, makeTextureCoordinate);
            gl.glPopMatrix();

            // Top
            gl.glPushMatrix();
            gl.glRotatef(90, 1, 0, 0);
            gl.glTranslated(0, 0, height / 2);
            square(gl, width, depth, makeTextureCoordinate);
            gl.glPopMatrix();

            // Bottom
            gl.glPushMatrix();
            gl.glRotatef(-90, 1, 0, 0);
            gl.glTranslated(0, 0, height / 2);
            square(gl, width, depth, makeTextureCoordinate);
            gl.glPopMatrix();

        gl.glPopMatrix();
    }

    public static void square(GL2 gl, double width, double height, boolean makeTextureCoordinate) {
        double halfWidth = width / 2;
        double halfHeight = height / 2;

        gl.glBegin(GL2.GL_POLYGON);
            gl.glNormal3f(0, 0, 1);

            if (makeTextureCoordinate) {
                gl.glTexCoord2d(0, 1);
            }
            gl.glVertex2d(-halfWidth, halfHeight);

            if (makeTextureCoordinate) {
                gl.glTexCoord2d(0, 0);
            }
            gl.glVertex2d(-halfWidth, -halfHeight);

            if (makeTextureCoordinate) {
                gl.glTexCoord2d(1, 0);
            }
            gl.glVertex2d(halfWidth, -halfHeight);

            if (makeTextureCoordinate) {
                gl.glTexCoord2d(1, 1);
            }
            gl.glVertex2d(halfWidth, halfHeight);
        gl.glEnd();
    }

}
