package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends ScreenAdapter
{
    private final int WORLD_WIDTH = 288;
    private final int WORLD_HEIGHT = 400;

    FlappyBirdGame game;
    private Stage stage;

    private Texture backgroundDayTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private Texture message;
    private TextureRegion getReadyMessaage;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private Image backgroundMessage;

    public StartScreen(FlappyBirdGame flappyBirdGame)
    {
        this.game = flappyBirdGame;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundDayTexture = new Texture(Gdx.files.internal("background-day.png"));
        Image background = new Image(backgroundDayTexture);
        background.setSize(480, 640);
        stage.addActor(background);

        playTexture = new Texture(Gdx.files.internal("play.png"));
        playPressTexture = new Texture(Gdx.files.internal("playPress.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)),
                new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 4.0f, Align.center);
        stage.addActor(play);

        play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        });

        message = new Texture(Gdx.files.internal("message.png"));
        getReadyMessaage = new TextureRegion(message, message.getWidth(), (message.getHeight() / 2) + 20);
        backgroundMessage = new Image(getReadyMessaage);
        backgroundMessage.setPosition((WORLD_WIDTH / 2.0f), WORLD_HEIGHT / 1.5f, Align.center);
        stage.addActor(backgroundMessage);

        bitmapFont = new BitmapFont(Gdx.files.internal("gomarice.fnt"));
        glyphLayout = new GlyphLayout();


    }

    private SpriteBatch batch;
    private void pressSpaceMessage()
    {
        batch = new SpriteBatch();
        String pressSpace = "PRESS SPACE TO JUMP";
        glyphLayout.setText(bitmapFont, pressSpace);
        batch.begin();
        bitmapFont.getData().setScale(0.8f);
        bitmapFont.draw(batch, pressSpace,
                ((stage.getViewport().getWorldWidth() - glyphLayout.width) / 2.0f),
                (WORLD_HEIGHT / 10.0f));
        batch.end();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        pressSpaceMessage();
    }

    @Override
    public void resize(int width, int height) {
        // this is wrong
//        stage.getViewport().update(WORLD_WIDTH, WORLD_HEIGHT);
        // this one's right
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        bitmapFont.dispose();
        backgroundDayTexture.dispose();
        playTexture.dispose();
        playPressTexture.dispose();
        message.dispose();
        batch.dispose();
    }
}
