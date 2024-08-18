package com.zeljko.core;

import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.ShapeType;
import lombok.SneakyThrows;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static jogamp.opengl.glu.nurbs.Knotvector.TOLERANCE;

public class GameAction {

    private static Runnable onAutocompleteFinished;

    public static void updateModelConstraints(Runnable callback) {
        GameAction.onAutocompleteFinished = callback;
    }

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


    public static void startAutocomplete(GameActuator gameActuator, GameState gameState) {
        List<Model3D> userModels = new ArrayList<>(gameState.getUserModels());
        List<Model3D> blueprintModels = gameState.getCurrentBlueprint().getBlueprintModels();

        System.out.println("Starting autocomplete. User models: " + userModels.size() + ", Blueprint models: " + blueprintModels.size());

        for (int i = 0; i < blueprintModels.size(); i++) {
            Model3D blueprintModel = blueprintModels.get(i);
            ShapeType type = blueprintModel.getShapeType();

            System.out.println("Processing blueprint model " + i + " of type: " + type);

            Optional<Model3D> unalignedModel = userModels.stream()
                    .filter(getUnalignedModel(blueprintModel, type))
                    .findFirst();

            Model3D modelToAlign;

            if (unalignedModel.isPresent()) {
                modelToAlign = unalignedModel.get();
                System.out.println("Found unaligned model of type: " + type);
                userModels.remove(modelToAlign);
            } else if (gameState.canAddModel(type)) {
                System.out.println("No unaligned model found. Adding new model of type: " + type);
                boolean added = gameState.addModel(type);
                if (!added) {
                    System.out.println("Failed to add new model of type: " + type);
                    continue;
                }
                modelToAlign = gameState.getUserModels().get(gameState.getUserModels().size() - 1);
            } else {
                continue;
            }

            startAutocompleteWithAnimation(modelToAlign, blueprintModel, gameActuator);

            if (onAutocompleteFinished != null) {
                SwingUtilities.invokeLater(onAutocompleteFinished);
            }
        }
    }

    @SneakyThrows
    private static void startAutocompleteWithAnimation(Model3D model, Model3D blueprint, GameActuator gameActuator) {
        double[] unalignedModelPosition = {model.getTranslateX(), model.getTranslateY(), model.getTranslateZ()};
        double[] blueprintModelPosition = {blueprint.getTranslateX(), blueprint.getTranslateY(), blueprint.getTranslateZ()};

        double[] unalignedModelSize = {model.getWidth(), model.getHeight(), model.getDepth()};
        double[] blueprintModelSize = {blueprint.getWidth(), blueprint.getHeight(), blueprint.getDepth()};

        double[] unalignedModelRotation = {model.getRotationX(), model.getRotationY()};
        double[] blueprintModelRotation = {blueprint.getRotationX(), blueprint.getRotationY()};


        int steps = 60;
        long delay = 16;

        for (int i = 0; i <= steps; i++) {
            final double progress = (double) i / steps;
            SwingUtilities.invokeLater(() -> {
                double[] newPosition = new double[3];
                double[] newSize = new double[3];
                double[] newRotation = new double[2];

                for (int j = 0; j < 3; j++) {
                    newPosition[j] = unalignedModelPosition[j] + (blueprintModelPosition[j] - unalignedModelPosition[j]) * progress;
                    newSize[j] = unalignedModelSize[j] + (blueprintModelSize[j] - unalignedModelSize[j]) * progress;

                    if (j < 2) {
                        newRotation[j] = unalignedModelRotation[j] + (blueprintModelRotation[j] - unalignedModelRotation[j]) * progress;
                    }
                }

                model.setTranslateX(newPosition[0]);
                model.setTranslateY(newPosition[1]);
                model.setTranslateZ(newPosition[2]);


                model.setWidth(newSize[0]);
                model.setHeight(newSize[1]);
                model.setDepth(newSize[2]);

                model.setRotationX(newRotation[0]);
                model.setRotationY(newRotation[1]);

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