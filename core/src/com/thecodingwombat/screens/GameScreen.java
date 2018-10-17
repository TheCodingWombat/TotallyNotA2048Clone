package com.thecodingwombat.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.thecodingwombat.Board;
import com.thecodingwombat.GameHeader;
import com.thecodingwombat.TotallyNot2048;

public class GameScreen extends Group implements Screen{
    private AssetManager assetManager;
    private int GAME_MARGIN = (TotallyNot2048.SCREEN_WIDTH - TotallyNot2048.WIDTH) / 2;
    private Game game;
    private Stage stage;
    private GameHeader header;
    private Board board;
    private TitleScreen titleScreen;
    private final int BOARD_SIZE;

    GameScreen(Game game, TitleScreen titleScreen, AssetManager assetManager, final int BOARD_SIZE) {
        this.titleScreen = titleScreen;
        this.assetManager = assetManager;
        this.game = game;
        game.setScreen(this);
        this.BOARD_SIZE = BOARD_SIZE;
        stage = new Stage(new ScreenViewport());

        setFontSize();

        board = new Board(game, assetManager, GAME_MARGIN, GAME_MARGIN, BOARD_SIZE);
        stage.addActor(board);
        header = new GameHeader(game, assetManager);
        stage.addActor(header);
        stage.setKeyboardFocus(board);
    }

    void setFontSize() {
        BitmapFont tileFont = assetManager.get("tileFont.ttf", BitmapFont.class);
        tileFont.getData().setScale(2.0f / BOARD_SIZE);

        BitmapFont smallTileFont = assetManager.get("smallTileFont.ttf", BitmapFont.class);
        smallTileFont.getData().setScale(2.0f / BOARD_SIZE);
    }

    public void toTitleScreen() {
        game.setScreen(titleScreen);
    }

    public void updateScore(int score) {
        header.updateScore(score);
    }

    public void restartGame() {
        header.setScore(0);
        stage.clear();
        header = new GameHeader(game, assetManager);
        board = new Board(game, assetManager, GAME_MARGIN, GAME_MARGIN, BOARD_SIZE);
        stage.addActor(board);
        stage.addActor(header);
        stage.setKeyboardFocus(board);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(251f / 256f, 248f / 256f, 241f / 256f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}