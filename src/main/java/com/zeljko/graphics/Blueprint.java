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


    // separate class? rotation check?
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

    private boolean isModelAlignedWithBlueprint(Model3D model, Model3D blueprint) {
        double tolerance = 0.1;

        double widthDiff = model.getWidth() - blueprint.getWidth();
        double heightDiff = model.getHeight() - blueprint.getHeight();
        double depthDiff = model.getDepth() - blueprint.getDepth();

        double xDiff = model.getTranslateX() - blueprint.getTranslateX();
        double yDiff = model.getTranslateY() - blueprint.getTranslateY();
        double zDiff = model.getTranslateZ() - blueprint.getTranslateZ();

        System.out.println("Differences (Model - Blueprint):");
        System.out.printf("  Width: %.6f (Tolerance: %.6f)%n", widthDiff, tolerance);
        System.out.printf("  Height: %.6f (Tolerance: %.6f)%n", heightDiff, tolerance);
        System.out.printf("  Depth: %.6f (Tolerance: %.6f)%n", depthDiff, tolerance);
        System.out.printf("  X position: %.6f (Tolerance: %.6f)%n", xDiff, tolerance);
        System.out.printf("  Y position: %.6f (Tolerance: %.6f)%n", yDiff, tolerance);
        System.out.printf("  Z position: %.6f (Tolerance: %.6f)%n", zDiff, tolerance);

        // Check dimensions
        boolean size = Math.abs(widthDiff) <= tolerance &&
                Math.abs(heightDiff) <= tolerance &&
                Math.abs(depthDiff) <= tolerance;

        // Check position
        boolean position = Math.abs(xDiff) <= tolerance &&
                Math.abs(yDiff) <= tolerance &&
                Math.abs(zDiff) <= tolerance;


        if (!size) {
            System.out.println("Size: ");
            if (Math.abs(widthDiff) > tolerance) System.out.printf("  Width off by: %.6f%n", widthDiff);
            if (Math.abs(heightDiff) > tolerance) System.out.printf("  Height off by: %.6f%n", heightDiff);
            if (Math.abs(depthDiff) > tolerance) System.out.printf("  Depth off by: %.6f%n", depthDiff);
        }

        if (!position) {
            System.out.println("Position: ");
            if (Math.abs(xDiff) > tolerance) System.out.printf("  X coordinate off by: %.6f%n", xDiff);
            if (Math.abs(yDiff) > tolerance) System.out.printf("  Y coordinate off by: %.6f%n", yDiff);
            if (Math.abs(zDiff) > tolerance) System.out.printf("  Z coordinate off by: %.6f%n", zDiff);
        }

        return size && position;
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
