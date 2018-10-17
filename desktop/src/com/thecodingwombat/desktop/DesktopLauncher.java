package com.thecodingwombat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thecodingwombat.TotallyNot2048;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = TotallyNot2048.SCREEN_WIDTH;
		config.height = TotallyNot2048.SCREEN_HEIGHT;
		new LwjglApplication(new TotallyNot2048(), config);
	}
}
