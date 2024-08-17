package com.zeljko.utils;

import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlignmentChecker {

    public static boolean areAllModelsAligned(Blueprint blueprint, List<Model3D> userModels) {
        if (userModels.size() != blueprint.getBlueprintModels().size()) return false;

        return userModels.stream()
                .allMatch(userModel -> blueprint.getBlueprintModels().stream()
                        .anyMatch(blueprintModel -> isModelAlignedWithBlueprint(userModel, blueprintModel)));
    }

    private static boolean isModelAlignedWithBlueprint(Model3D model, Model3D blueprint) {
        if (model.getShapeType() != blueprint.getShapeType()) return false;

        double widthDiff = model.getShapeType() == ShapeType.CYLINDER ?
                (model.getWidth() / 2) - (blueprint.getWidth() / 2) :
                model.getWidth() - blueprint.getWidth();
        double heightDiff = model.getHeight() - blueprint.getHeight();
        double depthDiff = model.getDepth() - blueprint.getDepth();

        double xDiff = model.getTranslateX() - blueprint.getTranslateX();
        double yDiff = model.getTranslateY() - blueprint.getTranslateY();
        double zDiff = model.getTranslateZ() - blueprint.getTranslateZ();

        boolean size = Math.abs(widthDiff) <= TOLERANCE &&
                Math.abs(heightDiff) <= TOLERANCE &&
                Math.abs(depthDiff) <= TOLERANCE;

        boolean position = Math.abs(xDiff) <= TOLERANCE &&
                Math.abs(yDiff) <= TOLERANCE &&
                Math.abs(zDiff) <= TOLERANCE;

        System.out.println("Differences (Model - Blueprint):");
        System.out.printf("  Width: %.6f (Tolerance: %.6f)%n", widthDiff, TOLERANCE);
        System.out.printf("  Height: %.6f (Tolerance: %.6f)%n", heightDiff, TOLERANCE);
        System.out.printf("  Depth: %.6f (Tolerance: %.6f)%n", depthDiff, TOLERANCE);
        System.out.printf("  X position: %.6f (Tolerance: %.6f)%n", xDiff, TOLERANCE);
        System.out.printf("  Y position: %.6f (Tolerance: %.6f)%n", yDiff, TOLERANCE);
        System.out.printf("  Z position: %.6f (Tolerance: %.6f)%n", zDiff, TOLERANCE);

        if (!size) {
            System.out.println("Size: ");
            if (Math.abs(widthDiff) > TOLERANCE) System.out.printf("  Width off by: %.6f%n", widthDiff);
            if (Math.abs(heightDiff) > TOLERANCE) System.out.printf("  Height off by: %.6f%n", heightDiff);
            if (Math.abs(depthDiff) > TOLERANCE) System.out.printf("  Depth off by: %.6f%n", depthDiff);
        }

        if (!position) {
            System.out.println("Position: ");
            if (Math.abs(xDiff) > TOLERANCE) System.out.printf("  X coordinate off by: %.6f%n", xDiff);
            if (Math.abs(yDiff) > TOLERANCE) System.out.printf("  Y coordinate off by: %.6f%n", yDiff);
            if (Math.abs(zDiff) > TOLERANCE) System.out.printf("  Z coordinate off by: %.6f%n", zDiff);
        }

        return size && position;
    }
}