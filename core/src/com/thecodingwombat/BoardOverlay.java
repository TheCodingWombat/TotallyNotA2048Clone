package com.thecodingwombat;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;

abstract class BoardOverlay extends Group {
    GlyphLayout layout = new GlyphLayout();
    BitmapFont mediumFont, smallFont;
    private int zIndex = 0;
    Texture buttonTexture;

    BoardOverlay(int x, int y, AssetManager assetManager) {
        setBounds(x, y, TotallyNot2048.WIDTH, TotallyNot2048.HEIGHT);

        getColor().a = 0f;
        setZIndex(20);
        setVisible(false);

        smallFont = assetManager.get("smallBoltFont.ttf", BitmapFont.class);
        mediumFont = assetManager.get("mediumFont.ttf", BitmapFont.class);

        buttonTexture = Helper.createRoundedRectangleTexture(TotallyNot2048.WIDTH / 4, smallFont.getCapHeight() * 3, new Color(143f / 256f, 122f / 256f, 102f / 256f, 1f), 3);
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getZIndex() {
        return zIndex;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}