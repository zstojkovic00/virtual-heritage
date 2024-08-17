package com.zeljko.core;

import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.ModelFactory;
import com.zeljko.utils.ShapeType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApplicationState {

    private Blueprint currentBlueprint;
    private List<Model3D> userModels;
    private int currentModelIndex;

    public ApplicationState() {
        this.userModels = new ArrayList<>();
    }

    public void addModel(ShapeType shapeType) {
        if (canAddModel(shapeType)) {

            Model3D newModel = ModelFactory.createModel(shapeType);

            newModel.translate(Math.random() * 10 - 5,
                    Math.random() * 10 - 5,
                    Math.random() * 10 - 5);

            userModels.add(newModel);

            selectModel(userModels.size() - 1);

        } else {
            throw new IllegalStateException("Cannot add more models of this type");
        }
    }

    public boolean canAddModel(ShapeType type) {
        long currentCount = countModels(type);
        if (type == ShapeType.CUBOID) {
            return currentCount < currentBlueprint.getMaxCuboids();
        } else if (type == ShapeType.CYLINDER) {
            return currentCount < currentBlueprint.getMaxCylinders();
        }
        return false;
    }

    private long countModels(ShapeType type) {
        return userModels.stream()
                .filter(m -> m.getShapeType() == type)
                .count();
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
}
