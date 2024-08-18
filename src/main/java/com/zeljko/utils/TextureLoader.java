package com.zeljko.utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.zeljko.utils.Constants.TEXTURE_PATH;

public class TextureLoader {

    @Getter
    private static final Map<String, Texture> textures = new HashMap<>();

    public static void loadAllTextures(GL2 gl) {
        try (Stream<Path> paths = Files.walk(Paths.get(TEXTURE_PATH))) {

            paths.filter(Files::isRegularFile)
                    .forEach(path -> loadTexture(gl, path.getFileName().toString()));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void loadTexture(GL2 gl, String textureName) {
        if (textures.containsKey(textureName)) {
            return;
        }

        try {
            Path path = Paths.get(TEXTURE_PATH, textureName);

            if (Files.exists(path)) {

                BufferedImage image = ImageIO.read(path.toFile());
                ImageUtil.flipImageVertically(image);
                Texture texture = AWTTextureIO.newTexture(GLProfile.getDefault(), image, true);

                texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                textures.put(textureName, texture);

                System.out.println("Texture loaded: " + textureName);
            }
        } catch (IOException e) {
            System.out.println("Error loading texture: " + textureName);
            e.printStackTrace();
        }
    }

    public static void bindTexture(GL2 gl, String textureName) {
        Texture texture = textures.get(textureName);
        if (texture != null) {
            gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            texture.enable(gl);
            texture.bind(gl);
        } else {
            System.out.println("Texture not found: " + textureName);
        }
    }

    public static void unbindTexture(GL2 gl) {
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }
}