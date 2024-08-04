package com.zeljko.core;

import com.zeljko.graphics.Blueprint;
import com.zeljko.graphics.Model3D;



public class BlueprintFactory {
    public static Blueprint createHouseBlueprint() {
        Blueprint blueprint = new Blueprint(1, 0);
        Model3D rectangle = new Model3D(5.0, 1.5, 2.0);
        rectangle.translate(0, 0, 0);

        Model3D rectangle2 = new Model3D(5.0, 1.5, 2.0);
        rectangle2.translate(0, 2, 0);

        blueprint.addModel(rectangle);
        blueprint.addModel(rectangle2);
        return blueprint;
    }
}
