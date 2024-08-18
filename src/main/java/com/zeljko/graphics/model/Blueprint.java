package com.zeljko.graphics.model;

import com.zeljko.utils.BlueprintType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Blueprint {

    private final List<Model3D> blueprintModels;
    private BlueprintType blueprintType;
    private int numberOfCuboids;
    private int numberOfCylinders;

    public Blueprint(BlueprintType blueprintType, int numberOfCuboids, int numberOfCylinders) {
        this.blueprintModels = new ArrayList<>();
        this.blueprintType = blueprintType;
        this.numberOfCuboids = numberOfCuboids;
        this.numberOfCylinders = numberOfCylinders;
    }

    public void addModel(Model3D model) {
        blueprintModels.add(model);
    }
}
