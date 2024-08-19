package com.zeljko.graphics.model;

import com.jogamp.opengl.GL2;
import com.zeljko.graphics.Shape;
import com.zeljko.utils.ShapeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model3D {

    private double width, height, depth;
    private double translateX, translateY, translateZ;
    private double rotationX, rotationY;
    private float scale;
    private boolean isSelected;
    private ShapeType shapeType;
    private String textureName;
    private List<String> textures;

    public void draw(GL2 gl, boolean isOutline) {
        gl.glPushMatrix();
        gl.glTranslated(translateX, translateY, translateZ);
        gl.glRotated(rotationX, 1, 0, 0);
        gl.glRotated(rotationY, 0, 1, 0);
        gl.glScalef(scale, scale, scale);

        if (isOutline) {
            drawOutline(gl);
        } else {
            drawFilled(gl);
        }

        gl.glPopMatrix();
    }


    private void drawOutline(GL2 gl) {
        if (width <= 0 || height <= 0 || depth <= 0) return;

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        if (shapeType == ShapeType.CUBOID) {
            Shape.cuboid(gl, width, height, depth, false);
        } else if (shapeType == ShapeType.CYLINDER) {
            Shape.cylinder(gl, width / 2, height, 32, 32, 1, false);
        }
    }

    private void drawFilled(GL2 gl) {
        if (width <= 0 || height <= 0 || depth <= 0) return;

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        if (shapeType == ShapeType.CUBOID) {
            Shape.cuboid(gl, width, height, depth, true);
        } else if (shapeType == ShapeType.CYLINDER) {
            Shape.cylinder(gl, width / 2, height, 32, 32, 1, true);
        }
    }

    public void setTextures(List<String> textures) {
        this.textures = textures;
        if (!textures.isEmpty()) {
            this.textureName = textures.get(0);
        }
    }

    public void getNextTexture() {
        if (textures != null && !textures.isEmpty()) {
            int currentIndex = textures.indexOf(textureName);
            int nextIndex = (currentIndex + 1) % textures.size();
            textureName = textures.get(nextIndex);
        }
    }

    public Model3D(double width, double height, double depth, ShapeType shapeType) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.translateX = 0;
        this.translateY = 0;
        this.translateZ = 0;
        this.rotationX = 0;
        this.rotationY = 0;
        this.scale = 1.0f;
        this.shapeType = shapeType;
        this.textureName = null;
    }

    public void translate(double x, double y, double z) {
        this.translateX += x;
        this.translateY += y;
        this.translateZ += z;
    }

    public void rotate(int x, int y) {
        this.rotationX += x;
        this.rotationY += y;
    }

    public void scale(float factor) {
        this.scale *= factor;
    }
}
