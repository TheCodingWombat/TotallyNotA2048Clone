package com.thecodingwombat.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.thecodingwombat.TotallyNot2048;

public class TitleScreen implements Screen {
    private Stage stage;
    private GameScreen[] gameScreens;
    private TitleScreen titleScreen;
    private Game game;
    private AssetManager assetManager;
    private Image board;
    private GlyphLayout layout = new GlyphLayout();
    private String[] boardDescriptions;
    private Texture[] boardPreviews;
    private int currentBoardIndex = 1;
    private int[] boardSizes;
    private BitmapFont font;
    private Label boardDescriptionLabel;

    public TitleScreen(Game game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.titleScreen = this;
        stage = new Stage(new ScreenViewport());

        font = assetManager.get("smallerRegularFont.ttf", BitmapFont.class);
        Texture board3 = assetManager.get("board_preview_3.png", Texture.class);
        Texture board4 = assetManager.get("board_preview_4.png", Texture.class);
        Texture board5 = assetManager.get("board_preview_5.png", Texture.class);
        Texture board6 = assetManager.get("board_preview_6.png", Texture.class);
        Texture board8 = assetManager.get("board_preview_8.png", Texture.class);

        createTitle();
        createButtons();

        boardDescriptions = new String[] {"Small - 3x3", "Classic - 4x4", "Big - 5x5", "Bigger - 6x6", "Huge - 8x8"};
        boardSizes = new int[] {3, 4, 5, 6, 8};
        gameScreens = new GameScreen[5];
        boardPreviews = new Texture[] {board3, board4, board5, board6, board8};

        board = new Image(boardPreviews[1]);
        board.setBounds(TotallyNot2048.SCREEN_WIDTH / 2 - 300 / 2, TotallyNot2048.SCREEN_HEIGHT / 3 + layout.height * 1.5f, 300, 300);
        stage.addActor(board);

        boardDescriptionLabel = new Label(boardDescriptions[1], new Label.LabelStyle(font, new Color(.2f, .2f, .2f, 1f)));
        boardDescriptionLabel.setBounds(TotallyNot2048.SCREEN_WIDTH / 2 - 150, TotallyNot2048.SCREEN_HEIGHT / 3, 300, font.getCapHeight());
        boardDescriptionLabel.setAlignment(Align.center);

        stage.addActor(boardDescriptionLabel);
    }

    private void createButtons() {
        TextButton.TextButtonStyle button1Style = new TextButton.TextButtonStyle();
        button1Style.font = font;
        button1Style.fontColor = new Color(.2f, .2f, .2f, 1f);

        layout.setText(font, "Start Game");

        TextButton play = new TextButton("Start Game", button1Style);
        Image leftButton = new Image(assetManager.get("leftButton.png", Texture.class));
        Image rightButton = new Image(assetManager.get("rightButton.png", Texture.class));

        play.setBounds(TotallyNot2048.SCREEN_WIDTH / 2 - layout.width * .75f, TotallyNot2048.SCREEN_HEIGHT / 3 - layout.height * 3, layout.width * 1.5f, layout.height * 2);
        leftButton.setBounds(TotallyNot2048.SCREEN_WIDTH / 2 - 300 / 2 - layout.height * 1.5f, TotallyNot2048.SCREEN_HEIGHT / 3 - layout.height * .2f, layout.height * 1.5f, layout.height * 1.5f);
        rightButton.setBounds(TotallyNot2048.SCREEN_WIDTH / 2 + 300 / 2, TotallyNot2048.SCREEN_HEIGHT / 3 - layout.height * .2f, layout.height * 1.5f, layout.height * 1.5f);

        stage.addActor(play);
        stage.addActor(leftButton);
        stage.addActor(rightButton);

        play.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (gameScreens[currentBoardIndex] == null)
                    gameScreens[currentBoardIndex] = new GameScreen(game, titleScreen, assetManager, boardSizes[currentBoardIndex]);
                game.setScreen(gameScreens[currentBoardIndex]);
                gameScreens[currentBoardIndex].setFontSize();
                return true;
            }
        });

        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (currentBoardIndex == 0)
                    currentBoardIndex = 4;
                else
                    currentBoardIndex--;
                boardDescriptionLabel.setText(boardDescriptions[currentBoardIndex]);
                board.setDrawable(new TextureRegionDrawable(new TextureRegion(boardPreviews[currentBoardIndex])));
                return true;
            }
        });

        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (currentBoardIndex == 4)
                    currentBoardIndex = 0;
                else
                    currentBoardIndex++;
                boardDescriptionLabel.setText(boardDescriptions[currentBoardIndex]);
                board.setDrawable(new TextureRegionDrawable(new TextureRegion(boardPreviews[currentBoardIndex])));
                return true;
            }
        });
    }

    private void createTitle() {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = assetManager.get("bigFont.ttf", BitmapFont.class);
        label1Style.fontColor = new Color(.2f, .2f, .2f, 1f);
        Label title = new Label("2048", label1Style);
        title.setAlignment(Align.center);
        title.setBounds(0, Gdx.graphics.getHeight() * .85f, Gdx.graphics.getWidth(), 0);
        stage.addActor(title);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
    public void dispose() {
        stage.dispose();
    }
}