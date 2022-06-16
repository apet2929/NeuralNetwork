package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Apple extends Sprite{
    private Vector2 position;

    public Apple(Snake snake) {
        this(new Texture(Gdx.files.internal("apple.png")), snake);
    }

    private Apple(Texture texture, Snake snake){
        super(texture);
        position = getNewPos(snake);
    }

    public void collect(Snake snake) {
        this.position = getNewPos(snake);
    }

    private Vector2 getNewPos(Snake snake){
        while(true){
            Vector2 newPos = tryGetNewPos();
            if(!snake.isIntersecting(newPos)) return newPos;
        }
    }

    private Vector2 tryGetNewPos(){
        int x = (int) (Math.random() * SnakeGameState.GRID_SIZE);
        int y = (int) (Math.random() * SnakeGameState.GRID_SIZE);
        return new Vector2(x, y);
    }

    public Vector2 getWorldPosition() {
        return new Vector2(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        float x = this.position.x;
        float y = this.position.y;
        this.setBounds(x, y, 1, 1);
        super.draw(batch);
    }
}
