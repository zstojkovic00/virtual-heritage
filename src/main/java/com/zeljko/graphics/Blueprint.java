package com.zeljko.graphics;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blueprint {
    private List<Model3D> blueprintModels;
    private List<Boolean> filledBlueprints;


    private int maxRectangles;
    private int maxCylinders;

    public Blueprint(int maxRectangles, int maxCylinders) {
        this.blueprintModels = new ArrayList<>();
        this.filledBlueprints = new ArrayList<>(Collections.nCopies(maxRectangles + maxCylinders, false));
    }


    public boolean areAllModelsAligned(List<Model3D> models) {
        if (models.size() != blueprintModels.size()) return false;

        resetFilledBlueprints();

        for (Model3D model : models) {
            boolean modelAligned = false;

            for (int i = 0; i < blueprintModels.size(); i++) {
                if (!filledBlueprints.get(i) && isModelAlignedWithBlueprint(model, blueprintModels.get(i))) {
                    filledBlueprints.set(i, true);
                    modelAligned = true;
                    break;
                }

            }
            if (!modelAligned) {
                return false;
            }
        }
        return true;
    }

    private boolean isModelAlignedWithBlueprint(Model3D modelToCheck, Model3D blueprintModel) {
        double tolerance = 0.05;


        System.out.println("Model to check:");
        System.out.println("  Width: " + modelToCheck.getWidth());
        System.out.println("  Height: " + modelToCheck.getHeight());
        System.out.println("  Depth: " + modelToCheck.getDepth());
        System.out.println("  TranslateX: " + modelToCheck.getTranslateX());
        System.out.println("  TranslateY: " + modelToCheck.getTranslateY());
        System.out.println("  TranslateZ: " + modelToCheck.getTranslateZ());

        System.out.println("Blueprint model:");
        System.out.println("  Width: " + blueprintModel.getWidth());
        System.out.println("  Height: " + blueprintModel.getHeight());
        System.out.println("  Depth: " + blueprintModel.getDepth());
        System.out.println("  TranslateX: " + blueprintModel.getTranslateX());
        System.out.println("  TranslateY: " + blueprintModel.getTranslateY());
        System.out.println("  TranslateZ: " + blueprintModel.getTranslateZ());

        boolean sameSize = Math.abs(modelToCheck.getWidth() - blueprintModel.getWidth()) <= tolerance &&
                Math.abs(modelToCheck.getHeight() - blueprintModel.getHeight()) <= tolerance &&
                Math.abs(modelToCheck.getDepth() - blueprintModel.getDepth()) <= tolerance;

        boolean samePosition = Math.abs(modelToCheck.getTranslateX() - blueprintModel.getTranslateX()) <= tolerance &&
                Math.abs(modelToCheck.getTranslateY() - blueprintModel.getTranslateY()) <= tolerance &&
                Math.abs(modelToCheck.getTranslateZ() - blueprintModel.getTranslateZ()) <= tolerance;

        return sameSize && samePosition;
    }

    private boolean areAllBlueprintsFilled() {
        return !filledBlueprints.contains(false);
    }

    private void resetFilledBlueprints() {
        Collections.fill(filledBlueprints, false);
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
        filledBlueprints.add(false);
    }

    public List<Model3D> getBlueprintModels() {
        return blueprintModels;
    }


}
