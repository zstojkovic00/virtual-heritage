package com.zeljko.utils;

import com.zeljko.graphics.model.Model3D;

public class ModelFactory {
    public static Model3D createModel(ShapeType shapeType) {
        switch (shapeType) {
            case CUBOID:
                return new Model3D(5.0, 1.5, 2.0, ShapeType.CUBOID);
            case CYLINDER:
                return new Model3D(1.0, 2.0, 1.0, ShapeType.CYLINDER);
            default:
                throw new IllegalArgumentException("Unsupported shape type");
        }
    }
}
