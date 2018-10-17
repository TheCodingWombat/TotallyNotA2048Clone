package com.thecodingwombat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

class Helper {
    /*Method by siroakblog*/
    static Texture createRoundedRectangleTexture(float width, float height, Color color, int radius) {
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        Pixmap.setBlending(Pixmap.Blending.None);
        // wide rectangle
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight() - 2 * radius);
        // tall rectangle
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2 * radius, pixmap.getHeight());
        // Bottom-left circle
        pixmap.fillCircle(radius, radius, radius);
        // Top-left circle
        pixmap.fillCircle(radius, pixmap.getHeight() - radius, radius);
        // Bottom-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, radius, radius);
        // Top-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, pixmap.getHeight() - radius, radius);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}