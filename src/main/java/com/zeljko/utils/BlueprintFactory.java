package com.zeljko.utils;


import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BlueprintFactory {

    private static final Map<BlueprintType, Map<ShapeType, String>> textureMap = new HashMap<>();

    static {
        initializeTextures();
    }

    public static Blueprint createBlueprint(BlueprintType type) {
        switch (type) {
            case CAR:
                return createCar();
            case TREE:
                return createTree();
            default:
                throw new IllegalArgumentException("Unsupported blueprint type");
        }
    }

    private static Blueprint createTree() {

        return null;
    }

    public static Blueprint createCar() {
        Blueprint blueprint = new Blueprint(BlueprintType.CAR, 1, 4);

        Model3D body = ModelFactory.createModel(ShapeType.CUBOID);
        body.translate(0, 0, 0);
        blueprint.addModel(body);


        Model3D gum_1 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        gum_1.translate(-2, -1.3, -1.5);
        blueprint.addModel(gum_1);


        Model3D gum_2 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        gum_2.translate(2, -1.3, -1.5);
        blueprint.addModel(gum_2);


        Model3D gum_3 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        gum_3.translate(-2, -1.3, 1);
        blueprint.addModel(gum_3);


        Model3D gum_4 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        gum_4.translate(2, -1.3, 1);
        blueprint.addModel(gum_4);

        return blueprint;
    }


    private static void initializeTextures() {
        Map<ShapeType, String> carTextures = new EnumMap<>(ShapeType.class);
        carTextures.put(ShapeType.CUBOID, "green.png");
        carTextures.put(ShapeType.CYLINDER, "green.png");
        textureMap.put(BlueprintType.CAR, carTextures);
    }

    public static String getTextureForShape(BlueprintType blueprintType, ShapeType shapeType) {
        Map<ShapeType, String> textures = textureMap.get(blueprintType);
        if (textures != null) {
            return textures.get(shapeType);
        }
        return null;
    }

}
