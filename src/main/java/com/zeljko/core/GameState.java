package com.zeljko.core;

import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.BlueprintFactory;
import com.zeljko.utils.BlueprintType;
import com.zeljko.utils.ModelFactory;
import com.zeljko.utils.ShapeType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class GameState {

    private Blueprint currentBlueprint;
    private List<Model3D> userModels;
    private int currentModelIndex;
    private boolean alignmentCheck;
    private List<BlueprintType> remainingBlueprints;
    private Random random = new Random();

    public GameState() {
        remainingBlueprints = new ArrayList<>(Arrays.asList(BlueprintType.values()));
        this.userModels = new ArrayList<>();
    }

    public boolean addModel(ShapeType shapeType) {
        if (canAddModel(shapeType)) {
            Model3D newModel = ModelFactory.createModel(shapeType);


            newModel.translate(Math.random() * 10 - 5,
                    Math.random() * 10 - 5,
                    Math.random() * 10 - 5);

            userModels.add(newModel);

            selectModel(userModels.size() - 1);
            return true;
        }
        return false;
    }

    public boolean canAddModel(ShapeType type) {
        long currentCount = countModels(type);
        if (type == ShapeType.CUBOID) {
            return currentCount < currentBlueprint.getNumberOfCuboids();
        } else if (type == ShapeType.CYLINDER) {
            return currentCount < currentBlueprint.getNumberOfCylinders();
        }
        return false;
    }

    private long countModels(ShapeType type) {
        return userModels.stream()
                .filter(m -> m.getShapeType() == type)
                .count();
    }

    public long getRemainingModelCount(ShapeType type) {
        long currentCount = userModels.stream().filter(m -> m.getShapeType() == type).count();
        if (type == ShapeType.CUBOID) {
            return currentBlueprint.getNumberOfCuboids() - currentCount;
        } else if (type == ShapeType.CYLINDER) {
            return currentBlueprint.getNumberOfCylinders() - currentCount;
        }
        return 0;
    }

    public void selectNextModel() {
        if (!userModels.isEmpty()) {
            selectModel((currentModelIndex + 1) % userModels.size());
        }
    }

    private void selectModel(int index) {
        if (currentModelIndex != -1) {
            userModels.get(currentModelIndex).setSelected(false);
        }
        currentModelIndex = index;
        userModels.get(currentModelIndex).setSelected(true);
    }

    public Model3D getCurrentModel() {
        return currentModelIndex != -1 ? userModels.get(currentModelIndex) : null;
    }

    public void setRandomBlueprint() {
        if (!remainingBlueprints.isEmpty()) {
            int i = random.nextInt(remainingBlueprints.size());
            BlueprintType type = remainingBlueprints.remove(i);
            setCurrentBlueprint(type);
        }
    }

    public void setCurrentBlueprint(BlueprintType type) {
        this.currentBlueprint = BlueprintFactory.createBlueprint(type);
    }
}
