package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class LoadingScreen extends ScreenAdapter {

    private final FlappyBirdGame flappyBirdGame;

    private static final float WORLD_WIDTH = 288;
    private static final float WORLD_HEIGHT = 400;

    private static final float PROGRESS_BAR_WIDTH = 100;
    /**
     * I used this to lengthen the bar width. If I use
     * the one above, the bar's x will be modified as well.
     */
    private static final float PROGRESS_BAR_WIDTH2 = 200;
    private static final float PROGRESS_BAR_HEIGHT = 25;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;

    private float progress = 0;

    public LoadingScreen(FlappyBirdGame game) {
        this.flappyBirdGame = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();

        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        BitmapFontLoader.BitmapFontParameter bitmapFontParameter =
                new BitmapFontLoader.BitmapFontParameter();
        bitmapFontParameter.atlasName = "flappee_bird_pack.atlas";
        flappyBirdGame.getAssetManager().load("gomarice.fnt", BitmapFont.class,
                bitmapFontParameter);

        flappyBirdGame.getAssetManager().load("flappee_bird_pack.atlas", TextureAtlas.class);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void update() {
        if (flappyBirdGame.getAssetManager().update()) {
            flappyBirdGame.setScreen(new GameScreen(flappyBirdGame));
//                dispose(); THIS SHIT CAUSED ERRORS SO IM COMMENTING IT
        } else
            progress = flappyBirdGame.getAssetManager().getProgress();
    }

    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
                (WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2,
                (WORLD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2,
                progress * PROGRESS_BAR_WIDTH2,
                PROGRESS_BAR_HEIGHT
        );
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
