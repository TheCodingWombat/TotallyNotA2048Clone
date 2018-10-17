package com.thecodingwombat;

import com.badlogic.gdx.graphics.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Colors {
    Tile_2 (2, new Color(238f / 256f, 238f / 256f, 218f / 256f, 1f), new Color(119f / 256f, 110f / 256f, 101f/ 256f, 1f)),
    Tile_4 (4, new Color(237f / 256f, 224f / 256f, 200f / 256f, 1f), new Color(119f / 256f, 110f / 256f, 101f/ 256f, 1f)),
    Tile_8 (8, new Color(242f / 256f, 177f / 256f, 121f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_16 (16, new Color(245f / 256f, 149f / 256f, 99f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_32 (32, new Color(246f / 256f, 124f / 256f, 95f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_64 (64, new Color(246f / 256f, 94f / 256f, 59f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_128 (128, new Color(237f / 256f, 207f / 256f, 114f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_256 (256, new Color(237f / 256f, 204f / 256f, 97f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_512 (512, new Color(237f / 256f, 200f / 256f, 80f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_1024 (1024, new Color(237f / 256f, 197f / 256f, 63f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_2048 (2048, new Color(237f / 256f, 194f / 256f, 46f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_4096 (4096, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_8192 (8192, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_16384 (16384, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_32768 (32768, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_65536 (65536, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_131072 (131072, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f)),
    Tile_262144 (262144, new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f), new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f));

    private final int value;
    private final Color color;
    private final Color fontColor;
    private static final List<Colors> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    Colors(int value, Color color, Color fontColor){
        this.value = value;
        this.color = color;
        this.fontColor = fontColor;
    }

    public static List<Colors> getVALUES() {
        return VALUES;
    }

    public Color getColor() {
        return color;
    }

    public Color getFontColor() {
        return fontColor;
    }
}