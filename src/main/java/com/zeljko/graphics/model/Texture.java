package com.zeljko.graphics.model;

import com.zeljko.utils.ShapeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Texture {
    private ShapeType shapeType;
    private String textureName;
    private int count;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Texture texture = (Texture) o;

        if (shapeType != texture.shapeType) return false;
        return Objects.equals(textureName, texture.textureName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shapeType, textureName);
    }

}
