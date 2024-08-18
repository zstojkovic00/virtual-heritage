package com.zeljko.utils;


import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;

public class BlueprintFactory {

    public static Blueprint createCar() {
        Blueprint blueprint = new Blueprint(1, 4);

        Model3D body = ModelFactory.createModel(ShapeType.CUBOID);
        body.translate(0,0,0);
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

}
