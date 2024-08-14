package com.zeljko.core;


import com.zeljko.graphics.Blueprint;
import com.zeljko.graphics.Model3D;
import com.zeljko.utils.ShapeType;

public class BlueprintFactory {

    public static Blueprint createTreeBlueprint() {
        Blueprint blueprint = new Blueprint(6, 1);

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

}
