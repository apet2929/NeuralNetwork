package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.state.BaseState;

public class SnakeGameState extends BaseState {
//    FitViewport viewport;
    ShapeRenderer sr;
    Snake snake;
    Apple apple;
    float elapsedTime;
    final float tickSpeed = 0.5f;
    public static final float WORLD_SIZE = 50f;
    public static final int GRID_SIZE = 20;
    public static final float SCALE = (float) Gdx.graphics.getWidth() / GRID_SIZE;

    public static final float WORLD_HEIGHT = Gdx.graphics.getWidth() / 50f;
    @Override
    public void onEnter() {
        sr = new ShapeRenderer();
        snake = new Snake();
        apple = new Apple();
//        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if(elapsedTime > tickSpeed){
            elapsedTime = 0;
            tick();
        }
    }
    void tick(){
        snake.update(apple);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            snake.turn(Direction.UP);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            snake.turn(Direction.LEFT);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            snake.turn(Direction.DOWN);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            snake.turn(Direction.RIGHT);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println("snake = " + snake.body);
            System.out.println("snake.direction.incPos = " + snake.direction.incPos);
            System.out.println("apple.getWorldPosition() = " + apple.getWorldPosition());
            System.out.println("apple = " + apple.toString());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
//        viewport.apply();
//        sr.setProjectionMatrix(viewport.getCamera().combined);
//        sr.begin(ShapeRenderer.ShapeType.Filled);
        sb.begin();
        snake.render(sb);
        apple.draw(sb);
        sb.end();
//        sr.end();
    }
}
