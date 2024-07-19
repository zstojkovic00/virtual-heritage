package com.zeljko.lego.graphics;

import com.jogamp.opengl.GL2;

public class Light {

    public void setupLighting(GL2 gl) {
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

        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }
}
