package com.zeljko.utils;


import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;

import java.util.HashMap;
import java.util.Map;

public class BlueprintFactory {

    private static final Map<BlueprintType, Map<String, String>> textureMap = new HashMap<>();

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

    public static Blueprint createTree() {
        Blueprint blueprint = new Blueprint(BlueprintType.TREE, 6, 1);

        Model3D trunk = new Model3D(0.5, 2.0, 0.5, ShapeType.CYLINDER);
        trunk.translate(0, 0, 0);
        trunk.rotate(90, 0);
        blueprint.addModel(trunk);

        Model3D treeTop1 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop1.translate(0, 0.25, 0);
        blueprint.addModel(treeTop1);

        Model3D treeTop2 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop2.translate(1, 0.8, 0);
        blueprint.addModel(treeTop2);

        Model3D treeTop3 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop3.translate(-1, 0.8, 0);
        blueprint.addModel(treeTop3);

        Model3D treeTop4 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop4.translate(2, 1.3, 0);
        blueprint.addModel(treeTop4);

        Model3D treeTop5 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop5.translate(0, 1.3, 0);
        blueprint.addModel(treeTop5);

        Model3D treeTop6 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop6.translate(-2, 1.3, 0);
        blueprint.addModel(treeTop6);

        return blueprint;
    }

    public static Blueprint createCar() {
        Blueprint blueprint = new Blueprint(BlueprintType.CAR, 1, 4);

        Model3D car_body = ModelFactory.createModel(ShapeType.CUBOID);
        car_body.translate(0, 0, 0);
        blueprint.addModel(car_body);


        Model3D wheel_1 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        wheel_1.translate(-2, -1.3, -1.5);
        blueprint.addModel(wheel_1);


        Model3D wheel_2 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        wheel_2.translate(2, -1.3, -1.5);
        blueprint.addModel(wheel_2);


        Model3D wheel_3 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        wheel_3.translate(-2, -1.3, 1);
        blueprint.addModel(wheel_3);


        Model3D wheel_4 = new Model3D(1.0, 0.5, 1.0, ShapeType.CYLINDER);
        wheel_4.translate(2, -1.3, 1);
        blueprint.addModel(wheel_4);

        return blueprint;
    }


    private static void initializeTextures() {
        Map<String, String> carTextures = new HashMap<>();
        carTextures.put(ShapeType.CUBOID.name(), "wood.png");
        carTextures.put(ShapeType.CYLINDER.name(), "grass.png");
        carTextures.put("BACKGROUND", "car-background.png");
        textureMap.put(BlueprintType.CAR, carTextures);


        Map<String, String> treeTextures = new HashMap<>();
        treeTextures.put(ShapeType.CUBOID.name(), "grass.png");
        treeTextures.put(ShapeType.CYLINDER.name(), "wood.png");
        treeTextures.put("BACKGROUND", "tree-background.png");
        textureMap.put(BlueprintType.TREE, treeTextures);
    }

    public static String getTextureForShape(BlueprintType blueprintType, ShapeType shapeType) {
        Map<String, String> textures = textureMap.get(blueprintType);
        if (textures != null) {
            return textures.get(shapeType.name());
        }
        return null;
    }

    public static String getBackgroundForType(BlueprintType blueprintType) {
        Map<String, String> textures = textureMap.get(blueprintType);
        if (textures != null) {
            return textures.get("BACKGROUND");
        }
        return null;
    }

}
