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
//            case EMPTY:
//                return createEmptyBlueprint();
            default:
                throw new IllegalArgumentException("Unsupported blueprint type");
        }
    }

//    private static Blueprint createEmptyBlueprint() {
//        return new Blueprint(BlueprintType.EMPTY, 99, 99);
//    }


    public static Blueprint createTree() {
        Blueprint blueprint = new Blueprint(BlueprintType.TREE, 6, 1);

        Model3D trunk = new Model3D(0.5, 2.0, 0.5, ShapeType.CYLINDER);
        trunk.translate(0, 0.05, 0);
        trunk.rotate(90, 0);
        blueprint.addModel(trunk);

        Model3D treeTop1 = new Model3D(2, 0.5, 0.5, ShapeType.CUBOID);
        treeTop1.translate(0, 0.3, 0);
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
        Blueprint blueprint = new Blueprint(BlueprintType.CAR, 99, 99);

        Model3D car_body = new Model3D(4.5, 0.3, 0.3, ShapeType.CUBOID);
        car_body.translate(0, -1, -1.7);
        car_body.setRotationX(-90.00);
        blueprint.addModel(car_body);

        Model3D car_body_2 = new Model3D(4.5, 0.3, 0.3, ShapeType.CUBOID);
        car_body_2.translate(0, -1, 1.2);
        car_body_2.setRotationX(-90.00);
        blueprint.addModel(car_body_2);


//        Model3D car_body_3 = new Model3D(2.90, 0.40, 2.40, ShapeType.CUBOID);
//        car_body_3.translate(0.03, -1.12, -0.25);
//        blueprint.addModel(car_body_3);


        Model3D car_body_4 = new Model3D(2.60, 2.50, 0.30, ShapeType.CUBOID);
        car_body_4.translate(2.7, 0.15, -0.30);
        car_body_4.rotate(90,90);
        blueprint.addModel(car_body_4);

        Model3D car_body_5 = new Model3D(5.00, 0.30, 2.50, ShapeType.CUBOID);
        car_body_5.translate(0, 1.30, -0.3);
        blueprint.addModel(car_body_5);



        Model3D wheel_1 = new Model3D(1.0, 2.5, 1.0, ShapeType.CYLINDER);
        wheel_1.translate(-2, -1.0, -1.5);
        blueprint.addModel(wheel_1);


        Model3D wheel_2 = new Model3D(1.0, 2.5, 1.0, ShapeType.CYLINDER);
        wheel_2.translate(2, -1.0, -1.5);
        blueprint.addModel(wheel_2);

        return blueprint;
    }


    private static void initializeTextures() {
        Map<String, String> carTextures = new HashMap<>();
        carTextures.put(ShapeType.CUBOID.name(), "wood.png");
        carTextures.put(ShapeType.CYLINDER.name(), "grass.png");
        carTextures.put("BACKGROUND", "black-background.png");
        textureMap.put(BlueprintType.CAR, carTextures);


        Map<String, String> treeTextures = new HashMap<>();
        treeTextures.put(ShapeType.CUBOID.name(), "grass.png");
        treeTextures.put(ShapeType.CYLINDER.name(), "wood.png");
        treeTextures.put("BACKGROUND", "tree-background.png");
        textureMap.put(BlueprintType.TREE, treeTextures);


//        Map<String, String> emptyTextures = new HashMap<>();
//        emptyTextures.put("BACKGROUND", "black-background.png");
//        textureMap.put(BlueprintType.EMPTY, emptyTextures);
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
