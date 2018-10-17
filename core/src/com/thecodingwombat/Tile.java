package com.thecodingwombat;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

class Tile extends Actor {
    private int value;
    private BitmapFont font;
    private boolean exists = true;
    private boolean upgradedThisTurn = false;
    private int index;
    private float moveX;
    private float moveY;
    private int valueForRender;
    private AssetManager assetManager;
    private Texture texture;
    private GlyphLayout layout = new GlyphLayout();
    private Board parent;

    Tile(int value, int index, AssetManager assetManager, Board board) {
        parent = board;
        this.assetManager = assetManager;
        this.index = index;
        this.value = value;
        this.valueForRender = value;

        createTile();

        setColor(getColor().r, getColor().g, getColor().b, 0);
        addAction(Actions.alpha(1f, 2.2f));

        moveX = getX();
        moveY = getY();
    }

    Tile (Tile anotherTile, Board board) {
        parent = board;
        this.assetManager = anotherTile.assetManager;
        this.value = anotherTile.value;
        this.index = anotherTile.index;
        this.moveX = anotherTile.moveX;
        this.moveY = anotherTile.moveY;
        this.valueForRender = anotherTile.valueForRender;
        this.upgradedThisTurn = anotherTile.upgradedThisTurn;

        createTile();

        this.moveX = anotherTile.moveX;
        this.moveY = anotherTile.moveY;
    }

    private void createTile() {
        setSize(parent.TILE_SIZE, parent.TILE_SIZE);
        setPosition(
                (parent.TILE_SIZE + parent.MARGIN) * (index % parent.BOARD_SIZE) + parent.MARGIN,
                (parent.TILE_SIZE + parent.MARGIN) * (int) Math.floor(index / parent.BOARD_SIZE) + parent.MARGIN
        );
        texture = Helper.createRoundedRectangleTexture(parent.TILE_SIZE, parent.TILE_SIZE, getTileColor(value), 6);
        createFont();

        layout.setText(font, Integer.toString(value));
    }

    private void createFont() {
        if (valueForRender > 512 || value > 512)
            font = assetManager.get("smallTileFont.ttf", BitmapFont.class);
        else
            font = assetManager.get("tileFont.ttf", BitmapFont.class);
    }

    void doAnimation() {
        addAction(Actions.sequence(Actions.moveTo(moveX, moveY, parent.TILE_MOVE_SPEED), Actions.run(() -> {
            if (value != valueForRender) {
                texture = Helper.createRoundedRectangleTexture(parent.TILE_SIZE, parent.TILE_SIZE, getTileColor(valueForRender), 6);
                createFont();
                layout.setText(font, Integer.toString(valueForRender));
                value = valueForRender;
            }

            if (!exists) ((Board) getParent()).removeTempTile(Tile.this);
            resetAnimationParameters();
            Board.resume();
        })));
    }

    void setAnimationParameters(int x, int y, int renderValue, boolean delete, int index) {
        if (valueForRender != value || delete || (x == 0 && y == 0)) upgradedThisTurn = true;
        moveX += x;
        moveY += y;
        valueForRender = renderValue;
        this.index = index;
        if (delete) this.exists = false;
    }

    private void resetAnimationParameters() {
        upgradedThisTurn = false;
        moveX = getX();
        moveY = getY();
        valueForRender = value;
    }

    boolean isExists() {
        return exists;
    }

    int getValue() {
        return value;
    }

    int getValueForRender() {
        return valueForRender;
    }

    boolean isUpgradedThisTurn() {
        return upgradedThisTurn;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
        font.setColor(getFontColor(value));
        font.draw(batch, Integer.toString(value), getX() + getWidth() / 2 - layout.width / 2, getY() + getHeight() / 2 + layout.height / 2);
    }

    private Color getFontColor(int value) {
        switch(value) {
            case 2: case 4:
                return new Color(119f / 256f, 110f / 256f, 101f/ 256f, 1f);
            default:
                return new Color(249f / 256f, 246f / 256f, 242f/ 256f, 1f);
        }
    }

    private Color getTileColor(int value) {
        switch(value) {
            case 2:
                return new Color(238f / 256f, 238f / 256f, 218f / 256f, 1f);
            case 4:
                return new Color(237f / 256f, 224f / 256f, 200f / 256f, 1f);
            case 8:
                return new Color(242f / 256f, 177f / 256f, 121f / 256f, 1f);
            case 16:
                return new Color(245f / 256f, 149f / 256f, 99f / 256f, 1f);
            case 32:
                return new Color(246f / 256f, 124f / 256f, 95f / 256f, 1f);
            case 64:
                return new Color(246f / 256f, 94f / 256f, 59f / 256f, 1f);
            case 128:
                return new Color(237f / 256f, 207f / 256f, 114f / 256f, 1f);
            case 256:
                return new Color(237f / 256f, 204f / 256f, 97f / 256f, 1f);
            case 512:
                return new Color(237f / 256f, 200f / 256f, 80f / 256f, 1f);
            case 1024:
                return new Color(237f / 256f, 197f / 256f, 63f / 256f, 1f);
            case 2048:
                return new Color(237f / 256f, 194f / 256f, 46f / 256f, 1f);
            default:
                return new Color(48f / 256f, 45f / 256f, 37f / 256f, 1f);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    void dispose() {
        texture.dispose();
    }
}