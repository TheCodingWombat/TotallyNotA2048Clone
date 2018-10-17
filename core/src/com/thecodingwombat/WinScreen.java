package com.thecodingwombat;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

class WinScreen extends BoardOverlay {
    private Texture backgroundTexture;
    private Rectangle keepGoingRect, tryAgainRect;

    WinScreen (int x, int y, AssetManager assetManager) {
        super(x, y, assetManager);

        backgroundTexture = Helper.createRoundedRectangleTexture(getWidth(), getHeight(), new Color(256f / 256f, 256f / 256f, 0f / 256f, .5f), 15);

        keepGoingRect = new Rectangle(getX() + getWidth() / 2 - buttonTexture.getWidth() * 1.05f, getY() + getHeight() / 3, TotallyNot2048.WIDTH / 4, smallFont.getCapHeight() * 3f);
        tryAgainRect = new Rectangle(getX() + getWidth() / 2 + buttonTexture.getWidth() * .05f, getY() + getHeight() / 3, TotallyNot2048.WIDTH / 4, smallFont.getCapHeight() * 3f);

        listenForInput();
    }

    private void listenForInput() {
        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == Input.Buttons.LEFT)
                    if (tryAgainRect.contains(TotallyNot2048.getMousePosInGame().x, TotallyNot2048.getMousePosInGame().y))
                        ((Board) getParent()).restartGame();
                    else if (keepGoingRect.contains(TotallyNot2048.getMousePosInGame().x, TotallyNot2048.getMousePosInGame().y))
                        ((Board) getParent()).keepGoing();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(backgroundTexture, 0, 0);
        layout.setText(mediumFont, "You win!");
        mediumFont.setColor(new Color(245f / 256f, 245f / 256f, 240f / 256f, mediumFont.getColor().a));
        mediumFont.getColor().a = getColor().a;
        mediumFont.draw(batch, "You win!", getWidth() / 2 - layout.width / 2, getHeight() / 2 + layout.height / 2);
        mediumFont.getColor().a = 1f;

        batch.draw(buttonTexture, getWidth() / 2 - buttonTexture.getWidth() * 1.05f, getHeight() / 3);
        layout.setText(smallFont, "Keep going");
        smallFont.setColor(new Color(245f / 256f, 241f / 256f, 237f / 256f, 1f * getColor().a));
        smallFont.draw(batch, "Keep going",
                getWidth() / 2 - buttonTexture.getWidth() * .55f - layout.width / 2,
                getHeight() / 3 + layout.height * 2);

        batch.draw(buttonTexture, getWidth() / 2 + buttonTexture.getWidth() * 0.05f, getHeight() / 3);
        layout.setText(smallFont, "Try again");
        smallFont.setColor(new Color(245f / 256f, 241f / 256f, 237f / 256f, 1f * getColor().a));
        smallFont.draw(batch, "Try again",
                getWidth() / 2 + buttonTexture.getWidth() * .55f - layout.width / 2, getHeight() / 3 + layout.height * 2);

        batch.setColor(getColor().r, getColor().g, getColor().b, 1f);
    }
}