package com.zeljko.lego.graphics;


import com.zeljko.lego.graphics.math.Vector3f;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Face {

    public Vector3f vertex; // three indices, not vertices or normals!
    public Vector3f normal;
}