package com.zeljko.core;

import com.jogamp.opengl.GL2;
import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.TextureLoader;

import java.util.List;

public class Renderer {

    public void renderScene(GL2 gl, Blueprint blueprint, List<Model3D> userModels) {
        if (blueprint != null) renderBlueprint(gl, blueprint);
        if (userModels != null) renderUserModels(gl, userModels);
    }

    private void renderBlueprint(GL2 gl, Blueprint blueprint) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        for (Model3D model : blueprint.getBlueprintModels()) {
            model.draw(gl, true);
        }
    }

    private void renderUserModels(GL2 gl, List<Model3D> userModels) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        for (Model3D model : userModels) {
            if (model.isSelected()) {
                gl.glColor3f(1.0f, 0.0f, 0.0f);
            } else {
                gl.glColor3f(0.5f, 0.5f, 0.5f);
            }

            if (model.getTextureName() != null) {
                TextureLoader.bindTexture(gl, model.getTextureName());
            }

            model.draw(gl, false);

            if (model.getTextureName() != null) {
                TextureLoader.unbindTexture(gl);
            }
        }
    }
}
