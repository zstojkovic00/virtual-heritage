package com.zeljko.graphics.model;

import com.jogamp.opengl.GL2;
import com.zeljko.graphics.Shape;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.ShapeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Blueprint {

    private final List<Model3D> blueprintModels;
    private int maxCuboids;
    private int maxCylinders;

    public Blueprint(int maxCuboids, int maxCylinders) {
        this.blueprintModels = new ArrayList<>();
        this.maxCuboids = maxCuboids;
        this.maxCylinders = maxCylinders;
    }

    public void addModel(Model3D model) {
        blueprintModels.add(model);
    }
}
