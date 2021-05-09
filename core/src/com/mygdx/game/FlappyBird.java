package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class FlappyBird {
    private static final float WORLD_WIDTH = 288,  WORLD_HEIGHT = 480;

    private float x, y;
    private static final float FLY_UP_ACCEL = 3F;
    private static final float DIVE_ACCEL = 0.30F;
    private float ySpeed = 0;

    private TextureRegion textureRegion;

    private static final float CIRCLE_RADIUS = 15;
    private Circle circle;

    public FlappyBird(TextureRegion textureRegion){
        x = 0;
        y = 0;
        this.textureRegion = textureRegion;

        circle = new Circle(x ,y, CIRCLE_RADIUS);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
    }

    public void update(float delta){
        ySpeed -= DIVE_ACCEL;
        setPosition(x, y + ySpeed);
    }
    public void flyUp()
    {
        ySpeed = FLY_UP_ACCEL;
        setPosition(x, y + ySpeed);
    }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        circle.setX(this.x);
        circle.setY(this.y);
    }

    public boolean boundToWorld()
    {
        if (this.y < 0){
            setPosition(x, 0);
            return true;
        }
        return false;
    }
    public void draw(SpriteBatch batch) {
        // center Circle shape to Flappy
        float textureX = circle.x - textureRegion.getRegionWidth() / 2f;
        float textureY = circle.y - textureRegion.getRegionHeight() / 2f;
        batch.draw(textureRegion, textureX, textureY);
    }

    public Circle getCircle(){
        return this.circle;
    }
    public float getX() {
        return this.x;
    }

    public void setYSpeed(float y) {
        this.ySpeed = y;
    }
}
