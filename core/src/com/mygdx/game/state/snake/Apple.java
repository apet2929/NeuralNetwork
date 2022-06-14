package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Apple extends Sprite{
    private Vector2 position;

    public Apple() {
        this.setTexture(new Texture(Gdx.files.internal("apple.png")));
        position = getNewPos();
    }

    public void collect() {
        this.position = getNewPos();
    }

    private Vector2 getNewPos(){
        int x = (int) (Math.random() * SnakeGameState.GRID_SIZE);
        int y = (int) (Math.random() * SnakeGameState.GRID_SIZE);
        return new Vector2(x, y);
    }

    public Vector2 getWorldPosition() {
        return new Vector2(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        this.setBounds(this.position.x * SnakeGameState.SCALE, this.position.y * SnakeGameState.SCALE, SnakeGameState.SCALE, SnakeGameState.SCALE);
        super.draw(batch);
    }
}
