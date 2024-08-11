package com.zeljko.graphics;

import com.jogamp.opengl.GL2;

public class Shape {

    public static void rectangle(GL2 gl, double width, double height, double depth, boolean makeTextureCoordinate) {
        double halfWidth = width / 2;
        double halfHeight = height / 2;
        double halfDepth = depth / 2;

        gl.glPushMatrix();

        // Front
        gl.glPushMatrix();
        gl.glRotatef(0, 0, 1, 0);
        gl.glTranslated(0, 0, halfDepth);
        square(gl, width, height, makeTextureCoordinate);
        gl.glPopMatrix();

        // Right
        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslated(0, 0, halfWidth);
        square(gl, depth, height, makeTextureCoordinate);
        gl.glPopMatrix();

        // Back
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        gl.glTranslated(0, 0, halfDepth);
        square(gl, width, height, makeTextureCoordinate);
        gl.glPopMatrix();

        // Left
        gl.glPushMatrix();
        gl.glRotatef(270, 0, 1, 0);
        gl.glTranslated(0, 0, halfWidth);
        square(gl, depth, height, makeTextureCoordinate);
        gl.glPopMatrix();

        // Top
        gl.glPushMatrix();
        gl.glRotatef(90, 1, 0, 0);
        gl.glTranslated(0, 0, halfHeight);
        square(gl, width, depth, makeTextureCoordinate);
        gl.glPopMatrix();

        // Bottom
        gl.glPushMatrix();
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslated(0, 0, halfHeight);
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


    public static void cylinder(GL2 gl, double radius, double height, int slices, int stacks, int rings,
                                boolean makeTextureCoordinates) {
        if (radius <= 0) throw new IllegalArgumentException("Radius must be positive");
        if (height <= 0) throw new IllegalArgumentException("Height must be positive");
        if (slices < 3) throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2) throw new IllegalArgumentException("Number of stacks must be at least 2.");

        // body
        for (int j = 0; j < stacks; j++) {
            double z1 = (height / stacks) * j;
            double z2 = (height / stacks) * (j + 1);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2 * Math.PI / slices) * i;
                double sinLongitude = Math.sin(longitude);
                double cosineLongitude = Math.cos(longitude);
                double x = cosineLongitude;
                double y = sinLongitude;
                gl.glNormal3d(x, y, 0);
                if (makeTextureCoordinates) {
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * (j + 1));
                }
                gl.glVertex3d(radius * x, radius * y, z2);
                if (makeTextureCoordinates) {
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * j);
                }
                gl.glVertex3d(radius * x, radius * y, z1);
            }
            gl.glEnd();
        }

        // draw the top and bottom
        if (rings > 0) {
            gl.glNormal3d(0, 0, 1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0 / rings) * j;
                double d2 = (1.0 / rings) * (j + 1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {  // TODO: ADD  "i<= slices"
                    double angle = (2 * Math.PI / slices) * i;
                    double sin = Math.sin(angle);
                    double cosine = Math.cos(angle);
                    if (makeTextureCoordinates) {
                        gl.glTexCoord2d(1 * (1 + cosine * d1), 0.5 * (1 + sin * d1));
                    }
                    gl.glVertex3d(radius * cosine * d1, radius * sin * d1, height);

                    if (makeTextureCoordinates) {
                        gl.glTexCoord2d(1 * (1 + cosine * d2), 0.5 * (1 + sin * d2));
                    }
                    gl.glVertex3d(radius * cosine * d2, radius * sin * d2, height);
                }
                gl.glEnd();
            }
            gl.glNormal3d(0, 0, -1);

            for (int j = 0; j < rings; j++) {
                double d1 = (1.0 / rings) * j;
                double d2 = (1.0 / rings) * (j + 1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {  // TODO: ADD  "i<= slices"
                    double angle = (2 * Math.PI / slices) * i;
                    double sin = Math.sin(angle);
                    double cosine = Math.cos(angle);
                    if (makeTextureCoordinates) {
                        gl.glTexCoord2d(0.5 * (1 + cosine * d2), 0.5 * (1 + sin * d2));
                    }
                    gl.glVertex3d(radius * cosine * d2, radius * sin * d2, 0);

                    if (makeTextureCoordinates) {
                        gl.glTexCoord2d(0.5 * (1 + cosine * d1), 0.5 * (1 + sin * d1));
                    }
                    gl.glVertex3d(radius * cosine * d1, radius * sin * d1, 0);
                }
                gl.glEnd();
            }
        }
    }


}
