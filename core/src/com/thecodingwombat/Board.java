package com.thecodingwombat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.thecodingwombat.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class Board extends Group {
    private AssetManager assetManager;
    private Tile[] tiles;
    private ArrayList<Tile> tempTiles;
    private Random random;
    final float TILE_MOVE_SPEED = .08f;
    final int MARGIN;
    final int TILE_SIZE;
    private final int MOVE_DISTANCE;
    final int BOARD_SIZE;
    private Texture boardBackground, emptyTile;
    private static boolean pause = false;
    private boolean isLost = false;
    private boolean isWon = false;
    private boolean keepGoing = false;
    private LossScreen lossScreen;
    private WinScreen winScreen;
    private Game game;
    private boolean hasMoveSucceeded = false;
    private boolean tileMoved = false;

    public Board(Game game, AssetManager assetManager, int x, int y, final int BOARD_SIZE) {
        setBounds(x, y, TotallyNot2048.WIDTH, TotallyNot2048.HEIGHT);

        this.BOARD_SIZE = BOARD_SIZE;
        MARGIN = TotallyNot2048.WIDTH / (BOARD_SIZE * 11);
        TILE_SIZE = (TotallyNot2048.WIDTH - MARGIN * (BOARD_SIZE + 1)) / BOARD_SIZE;
        MOVE_DISTANCE = TILE_SIZE + MARGIN;
        this.game = game;
        this.assetManager = assetManager;

        boardBackground = Helper.createRoundedRectangleTexture(getWidth(), getHeight(), new Color(187f / 256f, 173f / 256f, 160f / 256f, 1f), 15);
        emptyTile = Helper.createRoundedRectangleTexture(TILE_SIZE, TILE_SIZE, new Color(205f / 256f, 192f / 256f, 180f / 256f, 1f), 6);

        lossScreen = new LossScreen(x, y, assetManager);
        winScreen = new WinScreen(x, y, assetManager);
        addActor(lossScreen);
        addActor(winScreen);

        setup();

        listenForInput();
    }

    private static void pause() {
        pause = true;
    }

    static void resume() {
        pause = false;
    }

    private void updateScore(int incrementBy) {
        ((GameScreen) game.getScreen()).updateScore(incrementBy);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        batch.draw(boardBackground, 0, 0);
        drawEmptyTiles(batch);
        drawChildrenByZ(batch, parentAlpha);
    }

    private void drawEmptyTiles(Batch batch) {
        for (int y = 0; y < BOARD_SIZE; y++)
            for (int x = 0; x < BOARD_SIZE; x++)
                batch.draw(emptyTile, MARGIN + x * (TILE_SIZE + MARGIN), MARGIN + y * (TILE_SIZE + MARGIN));
    }

    private void drawChildrenByZ(Batch batch, float parentAlpha) {
        SnapshotArray<Actor> children = getChildren();
        children.sort((Actor o1, Actor o2) -> o1.getZIndex() > o2.getZIndex() ? 1 : -1);
        for (Actor actor : children) actor.draw(batch, parentAlpha);

    }

    private void showScreen(BoardOverlay screen) {
        screen.setVisible(true);
        screen.addAction(Actions.fadeIn(.4f));
    }

    private void hideWinScreen() {
        winScreen.addAction(Actions.sequence(Actions.fadeOut(.4f), Actions.run(() -> winScreen.setVisible(false))));
    }

    private void setup() {
        tiles = new Tile[BOARD_SIZE * BOARD_SIZE];
        tempTiles = new ArrayList<>();

        random = new Random();

        addNewTile();
        addNewTile();
    }

//    private void addNewDebugTile(int value, int pos) {
//        Tile tile = new Tile(value, pos, assetManager, this);
//        addActor(tile);
//        tiles[pos] = tile;
//    }

    void removeTempTile(Tile tile) {
        removeActor(tile);
        tempTiles.remove(tile);
    }

    private void addNewTile() {
        int newPos = random.nextInt(tiles.length);
        while (tiles[newPos] != null)
            newPos = random.nextInt(tiles.length);

        int value = random.nextInt(10) < 9 ? 2 : 4;
        Tile tile = new Tile(value, newPos, assetManager, this);
        addActor(tile);

        tiles[newPos] = tile;
    }

    private void moveTiles(int i, int j, int move_x, int move_y) {
        if ((tiles[i + j] != null && !tiles[i + j].isExists()) || tiles[i + j] == null) {
            tiles[i + j] = tiles[i];
            tiles[i] = null;
            tiles[i + j].setAnimationParameters(move_x * MOVE_DISTANCE, move_y * MOVE_DISTANCE, tiles[i + j].getValue(), false, i + j);
            hasMoveSucceeded = true;
            tileMoved = true;
        }
    }

    private void joinTiles(int i, int j, int move_x, int move_y) {
        if (tiles[i + j] != null && tiles[i + j].isExists() && tiles[i] != null)
            if (tiles[i + j].getValue() == tiles[i].getValue() && !tiles[i + j].isUpgradedThisTurn() && !tiles[i].isUpgradedThisTurn()) {
                updateScore(tiles[i + j].getValue() * 2);

                tiles[i + j].setAnimationParameters(0, 0, tiles[i + j].getValue() * 2, false, i + j);

                Tile tile = new Tile(tiles[i], this);
                tempTiles.add(tile);
                addActor(tile);

                tile.setAnimationParameters(move_x * MOVE_DISTANCE, move_y * MOVE_DISTANCE, tiles[i + j].getValue(), true, i + j);

                removeActor(tiles[i]);
                tiles[i].dispose();
                tiles[i] = null;

                hasMoveSucceeded = true;
                tileMoved = true;
            }
    }

    private void move(Direction direction) {
        tileMoved = false;
        for (int i = direction.getStartValue(tiles.length, BOARD_SIZE); direction.hasNext(i, tiles.length); i = direction.getNext(i)) {
            if (tiles[i] != null && tiles[i].isExists())
                if (direction.isValidMove(i, BOARD_SIZE)) {
                    moveTiles(i, direction.getDestinationIndex(BOARD_SIZE), direction.getHorMoveDistance(), direction.getVerMoveDistance());
                    if (tileMoved) {
                        move(direction);
                        break;
                    }

                    joinTiles(i, direction.getDestinationIndex(BOARD_SIZE), direction.getHorMoveDistance(), direction.getVerMoveDistance());
                    if (tileMoved) {
                        move(direction);
                        break;
                    }
                }
        }
    }

    private void setWon() {
        isWon = true;
        showScreen(winScreen);
    }

    private boolean boardIsFull() {
        for (Tile tile : tiles)
            if (tile == null)
                return false;
        return true;
    }

    private void checkLoss() {
        if (boardIsFull()) {
            isLost = true;
            for (int i = 0; i < tiles.length; i++)
                if ((i + 1 < tiles.length && (i + 1) % BOARD_SIZE != 0 && tiles[i].getValueForRender() == tiles[i + 1].getValueForRender())
                        || (i - 1 >= 0 && (i % BOARD_SIZE) != 0 && tiles[i].getValueForRender() == tiles[i - 1].getValueForRender())
                        || (i + BOARD_SIZE < tiles.length && tiles[i].getValueForRender() == tiles[i + BOARD_SIZE].getValueForRender())
                        || (i - BOARD_SIZE >= 0 && tiles[i].getValueForRender() == tiles[i - BOARD_SIZE].getValueForRender())
                        )
                    isLost = false;
        }

        if (isLost)
            showScreen(lossScreen);
    }

    private void doAnimations() {
        for (Tile tile : tiles)
            if (tile != null)
                tile.doAnimation();

        tempTiles.forEach(Tile::doAnimation);
    }

    void restartGame() {
        ((GameScreen) game.getScreen()).restartGame();
    }

    private void checkState() {
        if (!keepGoing)
            for (Tile tile : tiles)
                if (tile != null && tile.getValueForRender() == 2048)
                    setWon();

        if (!isWon)
            checkLoss();
    }

    void keepGoing() {
        keepGoing = true;
        isWon = false;
        hideWinScreen();
    }

    private void tryMove(Direction direction) {
        pause();
        hasMoveSucceeded = false;
        move(direction);
        if (hasMoveSucceeded) {
            doAnimations();
            addNewTile();
            checkState();
        } else
            resume();
    }

    private void listenForInput() {
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (!pause && !isLost && !isWon) {
                    switch (keycode) {
                        case Input.Keys.RIGHT:
                            tryMove(MoveDirection.RIGHT);
                            break;
                        case Input.Keys.LEFT:
                            tryMove(MoveDirection.LEFT);
                            break;
                        case Input.Keys.UP:
                            tryMove(MoveDirection.UP);
                            break;
                        case Input.Keys.DOWN:
                            tryMove(MoveDirection.DOWN);
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}