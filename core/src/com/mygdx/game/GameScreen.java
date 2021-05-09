package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 288;
    private static final float WORLD_HEIGHT = 400;

    private Camera camera;
    private Viewport viewport;

    private SpriteBatch batch;

    private TextureRegion background;

    private FlappyBirdGame flappyBirdGame;

    private TextureRegion flappyBirdTextureRegion;
    private FlappyBird flappyBird;

    private ShapeRenderer shapeRenderer;

    private TextureRegion bottomPipeTexture;
    private Texture topPipeTexture;

    private final float DISTANCE_BETWEEN_PIPES = 60F;

    private Array<Pipe> pipes = new Array<>();

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private int score = 0;

    private TextureRegion heartTextureRegion;

    public GameScreen(FlappyBirdGame flappyBirdGame) {
        this.flappyBirdGame = flappyBirdGame;
    }

    @Override
    public void show() {
        TextureAtlas textureAtlas =
                flappyBirdGame.getAssetManager().get("flappee_bird_pack.atlas");

        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();

        background = textureAtlas.findRegion("background-day");
        flappyBirdTextureRegion = textureAtlas.findRegion("bluebird-downflap");
        flappyBird = new FlappyBird(flappyBirdTextureRegion);
        flappyBird.setPosition(WORLD_WIDTH/4, 350);

        shapeRenderer = new ShapeRenderer();

        bottomPipeTexture = textureAtlas.findRegion("pipe-green");
        topPipeTexture = new Texture(Gdx.files.internal("pipe-green-reversed.png"));

        glyphLayout = new GlyphLayout();
        bitmapFont = flappyBirdGame.getAssetManager().get("gomarice.fnt");
    }

    @Override
    public void render(float delta) {
        clearScreen();
        draw();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        flappyBird.drawDebug(shapeRenderer);
        drawDebugPipes();
        shapeRenderer.end();

        update(delta);
    }

    public void update(float dt){
        flappyBird.update(dt);
        if (flappyBird.boundToWorld()){
            restartGame();
        }
        updatePipes(dt);
        updateScore();
        if (checkIfFlappyIsColliding()){
            restartGame();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) flappyBird.flyUp();
    }
    private void draw() {
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        drawPipes();
        flappyBird.draw(batch);
        drawScore();
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        batch.setProjectionMatrix(camera.combined);

    }

    private void createPipe(){
        Pipe pipe = new Pipe(topPipeTexture, bottomPipeTexture);
        pipe.setPosition(WORLD_WIDTH + Pipe.RECTANGLE_WIDTH * 2);
        pipes.add(pipe);
    }
    private void drawPipes(){
        for (Pipe p : pipes){
            p.draw(batch);
        }
    }
    private void checkIfPipeIsNeeded() {
        if (pipes.isEmpty()){
            createPipe();
        }
        else{
            Pipe pipe = pipes.peek();
            if (pipe.getX() < WORLD_WIDTH - DISTANCE_BETWEEN_PIPES) {
                createPipe();
            }
        }
    }
    private void updatePipes(float dt){
        for (Pipe pipe : pipes){
            pipe.update(dt);
        }
        checkIfPipeIsNeeded();
        removeFlowersIfPassedWorld();
    }
    private void removeFlowersIfPassedWorld(){
        if (pipes.size > 0) {
            Pipe pipe = pipes.first();
            if (pipe.getX() < -Pipe.RECTANGLE_WIDTH) {
                pipes.removeValue(pipe, true);
            }
        }
    }
    private void drawDebugPipes(){
        for (Pipe p : pipes){
            p.drawDebug(shapeRenderer);
        }
    }
    private boolean checkIfFlappyIsColliding(){
        for (Pipe pipe : pipes){
            if (pipe.isFlappyColliding(flappyBird)){
                return true;
            }
        }
        return false;
    }
    private void restartGame(){
        flappyBird.setYSpeed(0);
        flappyBird.setPosition(WORLD_WIDTH/4, 350);
        pipes.clear();
        score = 0;
    }

    private void drawScore(){
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.draw(batch, scoreAsString,
                (viewport.getWorldWidth() - glyphLayout.width) /2,
                (4 * viewport.getWorldHeight() / 5) - glyphLayout.height/2);
    }
    private void updateScore() {
        Pipe pipe = pipes.first();
        if (pipe.getX() + Pipe.RECTANGLE_WIDTH < flappyBird.getX() && !pipe.isPointClaimed()) {
            pipe.markPointClaimed();
            score++;
        }
    }

    @Override
    public void dispose() {

    }
}
