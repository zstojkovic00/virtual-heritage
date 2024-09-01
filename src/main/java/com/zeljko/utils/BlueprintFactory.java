package com.zeljko.utils;


import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;


public class BlueprintFactory {

    public static Blueprint createBlueprint(BlueprintType type) {
        Blueprint blueprint;
        switch (type) {
            case TREE:
                blueprint = createTree();
                setTreeTextures(blueprint);
                break;
            case SATELLITE:
                blueprint = createSatellite();
                setSatelliteTextures(blueprint);
                break;
            case TABLE:
                blueprint = createTable();
                setTableTextures(blueprint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported blueprint type");
        }
        return blueprint;
    }

    private static void setTreeTextures(Blueprint blueprint) {
        blueprint.addRequiredTexture(ShapeType.CYLINDER, "wood.png", 1);
        blueprint.addRequiredTexture(ShapeType.CUBOID, "grass.png", 6);
    }

    private static void setTableTextures(Blueprint blueprint) {
        blueprint.addRequiredTexture(ShapeType.CYLINDER, "wood-2.png", 4);
        blueprint.addRequiredTexture(ShapeType.CUBOID, "wood-2.png", 1);
    }

    private static void setSatelliteTextures(Blueprint blueprint) {
        blueprint.addRequiredTexture(ShapeType.CUBOID, "metal-blue.png", 2);
        blueprint.addRequiredTexture(ShapeType.CYLINDER, "metal-black.png", 5);
    }

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

    public static Blueprint createTable() {
        Blueprint blueprint = new Blueprint(BlueprintType.TABLE, 1, 4);

        Model3D table = new Model3D(5, 0.5, 5, ShapeType.CUBOID);
        table.translate(0, 0, 0);
        blueprint.addModel(table);

        Model3D table_leg_1 = new Model3D(0.5, 2.5, 1.0, ShapeType.CYLINDER);
        table_leg_1.translate(-2.0, -0.1, -2);
        table_leg_1.rotate(90, 0);
        blueprint.addModel(table_leg_1);

        Model3D table_leg_2 = new Model3D(0.5, 2.5, 1.0, ShapeType.CYLINDER);
        table_leg_2.translate(2.0, -0.1, -2);
        table_leg_2.rotate(90, 0);
        blueprint.addModel(table_leg_2);


        Model3D table_leg_3 = new Model3D(0.5, 2.5, 1.0, ShapeType.CYLINDER);
        table_leg_3.translate(2.0, -0.1, 2);
        table_leg_3.rotate(90, 0);
        blueprint.addModel(table_leg_3);

        Model3D table_leg_4 = new Model3D(0.5, 2.5, 1.0, ShapeType.CYLINDER);
        table_leg_4.translate(-2.0, -0.1, 2);
        table_leg_4.rotate(90, 0);
        blueprint.addModel(table_leg_4);

        return blueprint;
    }

    private static Blueprint createSatellite() {

        Blueprint blueprint = new Blueprint(BlueprintType.SATELLITE, 2, 5);

        Model3D satellite_body = new Model3D(1.5, 2.5, 1.0, ShapeType.CYLINDER);
        satellite_body.translate(0, 0, 0);
        satellite_body.rotate(90, 0);
        blueprint.addModel(satellite_body);

        Model3D satellite_element_up = new Model3D(0.5, 0.5, 1.0, ShapeType.CYLINDER);
        satellite_element_up.translate(0, 0.5, 0);
        satellite_element_up.rotate(90, 0);
        blueprint.addModel(satellite_element_up);

        Model3D satellite_element_up_2 = new Model3D(3.5, 0.5, 0.1, ShapeType.CUBOID);
        satellite_element_up_2.translate(3, 0.5, 0);
        satellite_element_up_2.rotate(90, 0);
        blueprint.addModel(satellite_element_up_2);

        Model3D satellite_element_up_3 = new Model3D(3.5, 0.5, 0.1, ShapeType.CUBOID);
        satellite_element_up_3.translate(-3, 0.5, 0);
        satellite_element_up_3.rotate(90, 0);
        blueprint.addModel(satellite_element_up_3);


        Model3D satellite_element_up_4 = new Model3D(0.1, 1, 0.1, ShapeType.CYLINDER);
        satellite_element_up_4.translate(-1.2, 0.5, 0);
        satellite_element_up_4.rotate(0, 90);
        blueprint.addModel(satellite_element_up_4);

        Model3D satellite_element_up_5 = new Model3D(0.1, 1, 0.1, ShapeType.CYLINDER);
        satellite_element_up_5.translate(0.2, 0.5, 0);
        satellite_element_up_5.rotate(0, 90);
        blueprint.addModel(satellite_element_up_5);


        Model3D satellite_element_down = new Model3D(0.5, 0.5, 1.0, ShapeType.CYLINDER);
        satellite_element_down.translate(0, -2.5, 0);
        satellite_element_down.rotate(90, 0);
        blueprint.addModel(satellite_element_down);


        return blueprint;
    }

}
