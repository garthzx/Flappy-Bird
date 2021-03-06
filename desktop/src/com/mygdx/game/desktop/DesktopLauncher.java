package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.FlappyBirdGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
//		config.width = 480;
//		config.height = 640;
		config.width = FlappyBirdGame.WORLD_WIDTH;
		config.height = FlappyBirdGame.WORLD_HEIGHT;
		new LwjglApplication(new FlappyBirdGame(), config);
	}
}
