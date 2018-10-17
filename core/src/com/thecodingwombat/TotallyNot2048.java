package com.thecodingwombat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Vector3;
import com.thecodingwombat.screens.TitleScreen;

public class TotallyNot2048 extends Game {
	private AssetManager assetManager;
	public static final int WIDTH = 600;
	static final int HEIGHT = 600;
	public static final int SCREEN_HEIGHT = 900;
	public static final int SCREEN_WIDTH = 700;
	private static OrthographicCamera camera;
	
	@Override
	public void create () {
		assetManager = new AssetManager();
		loadAssets();
		this.setScreen(new TitleScreen(this, assetManager));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	private void loadAssets() {
		assetManager.load("board_preview_3.png", Texture.class);
		assetManager.load("board_preview_4.png", Texture.class);
		assetManager.load("board_preview_5.png", Texture.class);
		assetManager.load("board_preview_6.png", Texture.class);
		assetManager.load("board_preview_8.png", Texture.class);
		assetManager.load("leftButton.png", Texture.class);
		assetManager.load("rightButton.png", Texture.class);

		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreetypeFontLoader.FreeTypeFontLoaderParameter font = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font.fontFileName = "ClearSans-Bold.ttf";
		font.fontParameters.size = HEIGHT / 10;
		assetManager.load("mediumFont.ttf", BitmapFont.class, font);

		FreetypeFontLoader.FreeTypeFontLoaderParameter font2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font2.fontFileName = "ClearSans-Bold.ttf";
		font2.fontParameters.size = HEIGHT / 6;
		assetManager.load("bigFont.ttf", BitmapFont.class, font2);

		FreetypeFontLoader.FreeTypeFontLoaderParameter font3 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font3.fontFileName = "ClearSans-Regular.ttf";
		font3.fontParameters.size = HEIGHT / 30;
		assetManager.load("smallFont.ttf", BitmapFont.class, font3);

		FreetypeFontLoader.FreeTypeFontLoaderParameter font4 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font4.fontFileName = "ClearSans-Bold.ttf";
		font4.fontParameters.size = HEIGHT / 30;
		assetManager.load("smallBoltFont.ttf", BitmapFont.class, font4);

		FreetypeFontLoader.FreeTypeFontLoaderParameter font5 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font5.fontFileName = "ClearSans-Bold.ttf";
		font5.fontParameters.size = (int) (HEIGHT / 5.0f);
		assetManager.load("tileFont.ttf", BitmapFont.class, font5);

		FreetypeFontLoader.FreeTypeFontLoaderParameter font6 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font6.fontFileName = "ClearSans-Bold.ttf";
		font6.fontParameters.size = (int) ((float) HEIGHT / 7.0f);
		assetManager.load("smallTileFont.ttf", BitmapFont.class, font6);

		FreetypeFontLoader.FreeTypeFontLoaderParameter font7 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		font7.fontFileName = "ClearSans-Regular.ttf";
		font7.fontParameters.size = HEIGHT / 14;
		assetManager.load("smallerRegularFont.ttf", BitmapFont.class, font7);

		assetManager.finishLoading();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
	}

	static Vector3 getMousePosInGame() {
		return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
}