package com.zeljko.graphics;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

public class Blueprint {
    private List<Model3D> blueprintModels;
    private int maxRectangles;
    private int maxCylinders;

    public Blueprint(int maxRectangles, int maxCylinders) {
        this.blueprintModels = new ArrayList<>();
    }

    public boolean isModelAligned(Model3D modelToCheck) {
        for (Model3D blueprintModel : blueprintModels) {
            if (isModelAlignedWithBlueprint(modelToCheck, blueprintModel)) {
                return true;
            }
        }
        return false;
    }


    private boolean isModelAlignedWithBlueprint(Model3D modelToCheck, Model3D blueprintModel) {
        double tolerance = 0.1;

        return Math.abs(modelToCheck.getTranslateX() - blueprintModel.getTranslateX()) <= (blueprintModel.getWidth() / 2 + tolerance) &&
                Math.abs(modelToCheck.getTranslateY() - blueprintModel.getTranslateY()) <= (blueprintModel.getHeight() / 2 + tolerance) &&
                Math.abs(modelToCheck.getTranslateZ() - blueprintModel.getTranslateZ()) <= (blueprintModel.getDepth() / 2 + tolerance);
    }
    public void drawBlueprint(GL2 gl) {
        if (blueprintModels.isEmpty()) return;

        gl.glPushMatrix();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        for (Model3D model : blueprintModels) {
            gl.glPushMatrix();
            gl.glTranslated(model.getTranslateX(), model.getTranslateY(), model.getTranslateZ());
            gl.glRotated(model.getRotationX(), 1, 0, 0);
            gl.glRotated(model.getRotationY(), 0, 1, 0);
            gl.glScalef(model.getScale(), model.getScale(), model.getScale());

            drawModelOutline(gl, model);

            gl.glPopMatrix();
        }

        gl.glPopMatrix();
    }

    private void drawModelOutline(GL2 gl, Model3D model) {
        if (model.getWidth() <= 0 || model.getHeight() <= 0 || model.getDepth() <= 0) return;
        Shape.rectangle(gl, model.getWidth(), model.getHeight(), model.getDepth(), false);
    }

    public void addModel(Model3D model) {
        blueprintModels.add(model);
    }

    public List<Model3D> getBlueprintModels() {
        return blueprintModels;
    }


}
