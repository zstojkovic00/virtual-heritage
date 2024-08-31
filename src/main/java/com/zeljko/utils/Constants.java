package com.zeljko.utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static int WINDOW_WIDTH = 1000;
    public static int WINDOW_HEIGHT = 562;

    private static final double TOLERANCE = 0.1;
    public static final float UNIT_MOVEMENT = 0.1F;

    public static final double ROTATION_SPEED = 0.1;
    public static final double ZOOM_SPEED = 0.5;


    public static final Color BLACK = new Color(100, 100, 100);
    public static final Color GRAY = new Color(200, 200, 200);
    public static final String IMAGE_PATH = "src/main/java/resources/images/";
    public static final String TEXTURE_PATH = "src/main/java/resources/textures/";

    public static final List<String> TEXTURES = Arrays.asList("wood.png", "grass.png", "rock.png", "rubber.png", "wood-2.png");

}
