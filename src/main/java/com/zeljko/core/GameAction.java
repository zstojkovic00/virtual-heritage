package com.zeljko.core;

import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.ShapeType;
import lombok.SneakyThrows;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static jogamp.opengl.glu.nurbs.Knotvector.TOLERANCE;

public class GameAction {

    public static boolean checkAlignment(Blueprint blueprint, List<Model3D> userModels) {
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


    public static boolean startAutocomplete(GameActuator gameActuator, GameState gameState) {
        List<Model3D> userModels = gameState.getUserModels();
        List<Model3D> blueprintModels = gameState.getCurrentBlueprint().getBlueprintModels();

        for (Model3D blueprintModel : blueprintModels) {
            ShapeType type = blueprintModel.getShapeType();

            Optional<Model3D> unalignedModel = userModels.stream()
                    .filter(getUnalignedModel(blueprintModel, type))
                    .findFirst();

            if (unalignedModel.isPresent()) {
                startAutocompleteWithAnimation(unalignedModel.get(), blueprintModel, gameActuator);
            }
        }

        return checkAlignment(gameState.getCurrentBlueprint(), gameState.getUserModels());
    }

    @SneakyThrows
    private static void startAutocompleteWithAnimation(Model3D model, Model3D blueprint, GameActuator gameActuator) {
        double startX = model.getTranslateX();
        double startY = model.getTranslateY();
        double startZ = model.getTranslateZ();

        double endX = blueprint.getTranslateX();
        double endY = blueprint.getTranslateY();
        double endZ = blueprint.getTranslateZ();

        int steps = 60;
        long delay = 16;

        for (int i = 0; i <= steps; i++) {
            final double progress = (double) i / steps;
            SwingUtilities.invokeLater(() -> {
                double newX = startX + (endX - startX) * progress;
                double newY = startY + (endY - startY) * progress;
                double newZ = startZ + (endZ - startZ) * progress;

                model.setTranslateX(newX);
                model.setTranslateY(newY);
                model.setTranslateZ(newZ);

                gameActuator.requestRender();
            });

            Thread.sleep(delay);
        }
    }

    private static Predicate<Model3D> getUnalignedModel(Model3D blueprintModel, ShapeType type) {
        return model -> model.getShapeType() == type
                && !isModelAlignedWithBlueprint(model, blueprintModel);
    }
}