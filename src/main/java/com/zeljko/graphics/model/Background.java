package com.zeljko.graphics.model;

import com.jogamp.opengl.GL2;
import com.zeljko.utils.BlueprintType;
import com.zeljko.utils.TextureLoader;
import lombok.Getter;
import lombok.Setter;

import static com.zeljko.utils.Constants.WINDOW_HEIGHT;
import static com.zeljko.utils.Constants.WINDOW_WIDTH;


@Getter
@Setter
public class Background {
    private static Background instance;
    private String textureName;

    public static Background getInstance() {
        if (instance == null) {
            instance = new Background();
        }
        return instance;
    }

    public static String getBackgroundForType(BlueprintType type) {
        switch (type) {
            case TREE:
                return "tree-background.png";
            default:
                return "default-background.png";
        }
    }

    public void draw(GL2 gl, String textureName) {
        if (textureName == null) {
            System.out.println("TextureName is null");
            return;
        }

        gl.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);


        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glPushMatrix();
            gl.glLoadIdentity();

            gl.glDisable(GL2.GL_DEPTH_TEST);

            gl.glEnable(GL2.GL_TEXTURE_2D);
            TextureLoader.bindTexture(gl, textureName);

            gl.glColor3f(1f, 1f, 1f);

            gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-1, -1);
            gl.glTexCoord2f(1, 0);
            gl.glVertex2f(1, -1);
            gl.glTexCoord2f(1, 1);
            gl.glVertex2f(1, 1);
            gl.glTexCoord2f(0, 1);
            gl.glVertex2f(-1, 1);
        gl.glEnd();

            gl.glDisable(GL2.GL_TEXTURE_2D);

            gl.glEnable(GL2.GL_DEPTH_TEST);

            gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
            gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPopMatrix();
    }
}