package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBirdGame extends Game {

    public static final int WORLD_WIDTH = 288;
    public static final int WORLD_HEIGHT = 400;

    private AssetManager assetManager = new AssetManager();

    public void create() {
        this.setScreen(new StartScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
