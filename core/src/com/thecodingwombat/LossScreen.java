package com.thecodingwombat;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

class LossScreen extends BoardOverlay {
    private Texture backgroundTexture;
    private Rectangle tryAgainRect;

    LossScreen (int x, int y, AssetManager assetManager) {
        super(x, y, assetManager);

        backgroundTexture = Helper.createRoundedRectangleTexture(getWidth(), getHeight(), new Color(200f / 256f, 200f / 256f, 200f / 256f, .8f), 15);

        tryAgainRect = new Rectangle(getX() + getWidth() / 2 - buttonTexture.getWidth() / 2, getY() + getHeight() / 3, TotallyNot2048.WIDTH / 4, smallFont.getCapHeight() * 3f);

        listenForInput();
    }

    private void listenForInput() {
        System.out.println("test");
        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("test2");
                if (pointer == Input.Buttons.LEFT)
                    if (tryAgainRect.contains(TotallyNot2048.getMousePosInGame().x, TotallyNot2048.getMousePosInGame().y))
                        ((Board) getParent()).restartGame();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(backgroundTexture, 0, 0);
        layout.setText(mediumFont, "Game over!");
        mediumFont.setColor(119f / 256f, 110f / 256f, 101f/ 256f, mediumFont.getColor().a);
        mediumFont.getColor().a = getColor().a;
        mediumFont.draw(batch, "Game over!", getWidth() / 2 - layout.width / 2, getHeight() / 2 + layout.height / 2);
        mediumFont.getColor().a = 1f;

        batch.draw(buttonTexture, getWidth() / 2 - buttonTexture.getWidth() / 2, getHeight() / 3);
        layout.setText(smallFont, "Try again");
        smallFont.setColor(new Color(245f / 256f, 241f / 256f, 237f / 256f, 1f * getColor().a));
        smallFont.draw(batch, "Try again", getWidth() / 2 - layout.width / 2, getHeight() / 3 + layout.height * 2);
        batch.setColor(getColor().r, getColor().g, getColor().b, 1f);
    }
}