package com.thecodingwombat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.thecodingwombat.screens.GameScreen;

import static com.thecodingwombat.TotallyNot2048.*;

public class GameHeader extends Group {
    private BitmapFont bigFont;
    private BitmapFont smallFont, smallBoltFont;
    private int GAME_MARGIN = (TotallyNot2048.SCREEN_WIDTH - WIDTH) / 2;
    private GlyphLayout layout = new GlyphLayout();
    private Texture scoreBackground;
    private int score, best;
    private Preferences prefs;
    private Game game;
    private Rectangle menuRect, newRect;

    public GameHeader(Game game, AssetManager assetManager) {
        setBounds(GAME_MARGIN, HEIGHT + GAME_MARGIN, WIDTH, SCREEN_HEIGHT - HEIGHT);
        this.game = game;

        score = 0;

        prefs = Gdx.app.getPreferences("HighScore");
        prefs.flush();

        best = prefs.getInteger("highScore");

        bigFont = assetManager.get("bigFont.ttf", BitmapFont.class);
        smallFont = assetManager.get("smallFont.ttf", BitmapFont.class);
        smallBoltFont = assetManager.get("smallBoltFont.ttf", BitmapFont.class);

        scoreBackground = Helper.createRoundedRectangleTexture(WIDTH / 4.2f, smallFont.getLineHeight() * 2, new Color(187f / 256f, 173f / 256f, 160f / 256f, 1f), 3);

        menuRect = new Rectangle(GAME_MARGIN + WIDTH - 80, HEIGHT + GAME_MARGIN * 2, 80, 40);
        newRect = new Rectangle(GAME_MARGIN + WIDTH - 170, HEIGHT + GAME_MARGIN * 2, 80, 40);

        listenForInput();
    }

    private void listenForInput() {
        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == Input.Buttons.LEFT) {
                    if (menuRect.contains(TotallyNot2048.getMousePosInGame().x, TotallyNot2048.getMousePosInGame().y))
                        ((GameScreen) game.getScreen()).toTitleScreen();

                    if (newRect.contains(TotallyNot2048.getMousePosInGame().x, TotallyNot2048.getMousePosInGame().y))
                        ((GameScreen) game.getScreen()).restartGame();
                }
                return true;
            }
        });
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void updateScore(int incrementBy) {
        score += incrementBy;

        if (score > best) {
            best = score;
            prefs.putInteger("highScore", best);
            prefs.flush();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        layout.setText(bigFont, "2048");
        bigFont.setColor(new Color(120f / 256f, 110f / 256f, 100f / 256f, 1f));
        bigFont.draw(batch, "2048", 0, SCREEN_HEIGHT - layout.height - GAME_MARGIN);

        layout.setText(smallFont, "Join the numbers and get to the ");
        smallFont.setColor(new Color(120f / 256f, 110f / 256f, 100f / 256f, 1f));
        smallFont.draw(batch, "Join the numbers and get to the ", 0, HEIGHT + GAME_MARGIN);

        smallBoltFont.setColor(new Color(120f / 256f, 110f / 256f, 100f / 256f, 1f));
        smallBoltFont.draw(batch, "2048 tile!", layout.width, HEIGHT + GAME_MARGIN);

        batch.draw(scoreBackground, WIDTH - scoreBackground.getWidth() * 2.05f, SCREEN_HEIGHT - GAME_MARGIN * 2.5f);
        batch.draw(scoreBackground, WIDTH - scoreBackground.getWidth(), SCREEN_HEIGHT - GAME_MARGIN * 2.5f);

        layout.setText(smallBoltFont, "SCORE");
        smallBoltFont.setColor(new Color(242f / 256f, 228f / 256f, 217f / 256f, 1f));
        smallBoltFont.draw(batch, "SCORE", WIDTH - scoreBackground.getWidth() * 2.05f + scoreBackground.getWidth() / 2 - layout.width / 2, SCREEN_HEIGHT - GAME_MARGIN * 2.5f + scoreBackground.getHeight() * .8f);
        layout.setText(smallBoltFont, "BEST");
        smallBoltFont.draw(batch, "BEST",  WIDTH - scoreBackground.getWidth() + scoreBackground.getWidth() / 2 - layout.width / 2, SCREEN_HEIGHT - GAME_MARGIN * 2.5f + scoreBackground.getHeight() * .8f);

        layout.setText(smallBoltFont, Integer.toString(score));
        smallBoltFont.setColor(Color.WHITE);
        smallBoltFont.draw(batch, Integer.toString(score), WIDTH - scoreBackground.getWidth() * 2.05f + scoreBackground.getWidth() / 2 - layout.width / 2, SCREEN_HEIGHT - GAME_MARGIN * 2.5f + scoreBackground.getHeight() * .4f);
        layout.setText(smallBoltFont, Integer.toString(best));
        smallBoltFont.draw(batch, Integer.toString(best), WIDTH - scoreBackground.getWidth() + scoreBackground.getWidth() / 2 - layout.width / 2, SCREEN_HEIGHT - GAME_MARGIN * 2.5f + scoreBackground.getHeight() * .4f);


        batch.draw(scoreBackground, WIDTH - 80, HEIGHT + GAME_MARGIN, 80, 40);

        layout.setText(smallBoltFont, "Menu");
        smallBoltFont.draw(batch, "Menu", WIDTH - 80 / 2 - layout.width / 2, HEIGHT + GAME_MARGIN + 40 / 2 + layout.height / 2);

        batch.draw(scoreBackground, WIDTH - 170, HEIGHT + GAME_MARGIN, 80, 40);

        layout.setText(smallBoltFont, "New");
        smallBoltFont.draw(batch, "New", WIDTH - 80 / 2 - 90 - layout.width / 2, HEIGHT + GAME_MARGIN + 40 / 2 + layout.height / 2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}