package com.zeljko.lego.utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextureLoader {
    private static final String[] textureFileNames = {
            "lego-side.png"
    };

    private static Texture[] textures;

    public static void loadTextures(GL2 gl) {

        textures = new Texture[textureFileNames.length];

        for (int i = 0; i < textureFileNames.length; i++) {
            try {
                Path path = Paths.get("src/main/java/resources/textures/" + textureFileNames[i]);

                if (Files.exists(path)) {
                    BufferedImage image = ImageIO.read(path.toFile());
                    ImageUtil.flipImageVertically(image);

                    textures[i] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, true);
                    textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                    textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                    System.out.println("Texture loaded successfully: " + textureFileNames[i]);

                }
            } catch (IOException e) {
                System.out.println("Error loading texture: " + textureFileNames[i]);
            }
        }

    }
}
