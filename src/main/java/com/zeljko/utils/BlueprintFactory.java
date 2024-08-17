package com.zeljko.utils;


import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.ShapeType;

public class BlueprintFactory {

    public static Blueprint createTreeBlueprint() {
        Blueprint blueprint = new Blueprint(6, 1);

        Model3D trunk = new Model3D(1.0, 2.0, 1.0, ShapeType.CYLINDER);
        trunk.translate(0, 0, 0);
        blueprint.addModel(trunk);

        return blueprint;
    }

}
