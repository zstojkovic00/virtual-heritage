package com.zeljko.utils;


import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.ShapeType;

public class BlueprintFactory {

    public static Blueprint createTreeBlueprint() {
        Blueprint blueprint = new Blueprint(6, 2);

        Model3D trunk = new Model3D(1.0, 2.0, 1.0, ShapeType.CYLINDER);
        trunk.translate(0, 0, 0);
        blueprint.addModel(trunk);

        Model3D trunk2 = new Model3D(1.0, 2.0, 1.0, ShapeType.CYLINDER);
        trunk2.translate(0, 1, 0);
        blueprint.addModel(trunk2);

        return blueprint;
    }

}
