package com.zeljko.lego.graphics;

import com.zeljko.lego.graphics.math.Vector3f;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Model {

    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Face> faces = new ArrayList<>();

}