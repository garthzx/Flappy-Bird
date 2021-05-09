package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Pipe {

    public static final float RECTANGLE_WIDTH = 53;
    private static final float RECTANGLE_HEIGHT = 320;
    private float x, y;

    private final float MAX_SPEED_PER_SECOND = 60F;
    private final float DISTANCE_BETWEEN_TOP_DOWN_PIPES = 120F;

    private Texture topTextureRegion;
    private TextureRegion bottomTextureRegion;

    private static final float HEIGHT_OFFSET = -300f;

    private Rectangle topRectangle;
    private Rectangle bottomRectangle;

    private boolean pointClaimed = false;

    public Pipe(Texture topTextureRegion, TextureRegion bottomTextureRegion){
        this.topTextureRegion = topTextureRegion;
        this.bottomTextureRegion = bottomTextureRegion;

        this.y = MathUtils.random(HEIGHT_OFFSET);
//        this.x = 400;
        this.bottomRectangle = new Rectangle(x, y, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        this.topRectangle = new Rectangle(x, (y + bottomRectangle.height) + DISTANCE_BETWEEN_TOP_DOWN_PIPES,
                                RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(bottomTextureRegion, x , y);
        batch.draw(topTextureRegion, x, topRectangle.getY());
    }
    public void setPosition(float x) {
        this.x = x;
        updateRectangle();
    }
    private void updateRectangle() {
        bottomRectangle.setX(this.x);
        topRectangle.setX(this.x);
    }
    public void update(float delta){
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }
    public void drawDebug(ShapeRenderer shapeRenderer){
        shapeRenderer.rect(
                bottomRectangle.x, bottomRectangle.y,
                bottomRectangle.width, bottomRectangle.height);

        shapeRenderer.rect(
                topRectangle.x, topRectangle.y,
                topRectangle.width, topRectangle.height
        );
    }

    public float getX() {
        return this.x;
    }
    public boolean isFlappyColliding(FlappyBird flappyBird){
        Circle flappyCircle = flappyBird.getCircle();
        return Intersector.overlaps(flappyCircle, topRectangle)
                || Intersector.overlaps(flappyCircle, bottomRectangle);
    }

    public boolean isPointClaimed(){
        return pointClaimed;
    }
    public void markPointClaimed(){
        pointClaimed = true;
    }
}
