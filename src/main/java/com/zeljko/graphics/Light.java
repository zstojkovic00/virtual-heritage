package com.zeljko.graphics;

import com.jogamp.opengl.GL2;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Light {

    private boolean lightOnOff = true;
    private boolean ambientLightOn = false;
    private boolean diffuseLightOn = false;
    private boolean specularLightOn = false;

    public void setupLighting(GL2 gl) {
        gl.glClearColor(0.95f, 0.95f, 1f, 0); // RGBA

        // enable the depth buffer to allow us represent depth information in 3d space
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING); // enable lighting calculation
        gl.glEnable(GL2.GL_LIGHT0); // initial value for light (1,1,1,1) -> RGBA
        gl.glEnable(GL2.GL_NORMALIZE);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
        gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 100);

        // initialize different light sources
        float[] ambient = {0.1f, 0.1f, 0.1f, 1.0f};
        float[] diffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};


        // configure different light sources
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, specular, 0);

        gl.glClearDepth(1.0f); // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST); // enable depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL); // the type of depth test to do
        // perspective correction
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH); // blend colors nicely & have smooth lighting

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    public void applyLighting(GL2 gl, float[] zero) {
        gl.glColor3d(0.5, 0.5, 0.5);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, zero, 0);


        if(lightOnOff){
            gl.glDisable(GL2.GL_LIGHTING);
        }else{
            gl.glEnable(GL2.GL_LIGHTING);
        }

        float[] ambient = {0.1f, 0.1f, 0.1f, 1};
        float[] diffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};

        // ambient light
        if (ambientLightOn) {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, ambient, 0);
            gl.glEnable(GL2.GL_LIGHT0);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT0);
        }

        // diffuse light
        if (diffuseLightOn) {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, diffuse, 0);
            gl.glEnable(GL2.GL_LIGHT1);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT1);
        }

        // specular light
        if (specularLightOn) {
            float[] shininess = {0.1f, 0.1f, 0.1f, 1};
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, specular, 0);
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess, 0);
            gl.glEnable(GL2.GL_LIGHT2);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT2);
        }

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
    }
}
