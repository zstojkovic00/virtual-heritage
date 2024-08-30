package com.zeljko.graphics.model;

import com.zeljko.utils.BlueprintType;
import com.zeljko.utils.ShapeType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Blueprint {

    private final List<Model3D> blueprintModels;
    private BlueprintType blueprintType;
    private List<Texture> requiredTextures;
    private int numberOfCuboids;
    private int numberOfCylinders;

    public Blueprint(BlueprintType blueprintType, int numberOfCuboids, int numberOfCylinders) {
        this.blueprintModels = new ArrayList<>();
        this.requiredTextures = new ArrayList<>();
        this.blueprintType = blueprintType;
        this.numberOfCuboids = numberOfCuboids;
        this.numberOfCylinders = numberOfCylinders;
    }

    public void addRequiredTexture(ShapeType shapeType, String textureName, int count) {
        requiredTextures.add(new Texture(shapeType, textureName, count));
    }

    public void addModel(Model3D model) {
        blueprintModels.add(model);
    }
}
