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
            case CAR:
                return "car-background.png";
            case TABLE:
                return "table-background.png";
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

        gl.glPushAttrib(GL2.GL_ENABLE_BIT | GL2.GL_TRANSFORM_BIT | GL2.GL_LIGHTING_BIT);


        // temporarily disable 3d functionalities
            gl.glDisable(GL2.GL_LIGHTING);
            gl.glDisable(GL2.GL_DEPTH_TEST);

            // enable blending
            gl.glEnable(GL2.GL_BLEND);
            gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

            // Set orthographic projection for 2D drawing
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glOrtho(-1, 1, -1, 1, -1, 1);

            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glPushMatrix();
            gl.glLoadIdentity();

            // draw fullscreen background
            gl.glEnable(GL2.GL_TEXTURE_2D);
            TextureLoader.bindTexture(gl, textureName);
            gl.glColor4f(1f, 1f, 1f, 0.6f);
            drawFullScreen(gl);

            // draw shady overlay
            gl.glDisable(GL2.GL_TEXTURE_2D);
            gl.glColor4f(0f, 0f, 0f, 0.4f);
            drawFullScreen(gl);

            // reset to previous matrix state
            gl.glPopMatrix();
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glPopMatrix();

        gl.glPopAttrib();
    }

    private void drawFullScreen(GL2 gl) {
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
    }
}