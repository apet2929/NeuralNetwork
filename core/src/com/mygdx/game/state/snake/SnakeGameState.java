package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.state.BaseState;

public class SnakeGameState extends BaseState {
    FitViewport viewport;
//    OrthographicCamera camera;
    ShapeRenderer sr;
    Snake snake;
    Apple apple;
    float elapsedTime;
    final float tickSpeed = 0.09f;
    public static final int GRID_SIZE = 10;
    public static final float SCALE = 1;

    @Override
    public void onEnter() {
        sr = new ShapeRenderer();
        snake = new Snake();
        apple = new Apple();
//        camera = new OrthographicCamera(20, 20);
//        camera.position.x = 10;
//        camera.position.y = 10;
        viewport = new FitViewport(GRID_SIZE, GRID_SIZE);
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
            System.out.println("apple.getWorldPosition() = " + apple.getWorldPosition());
            System.out.println("snake.body = " + snake.body);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        viewport.apply(true);
//        sr.setProjectionMatrix(viewport.getCamera().combined);
//        sr.begin(ShapeRenderer.ShapeType.Filled);
        drawLines();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        apple.draw(sb);
        snake.render(sb);
        sb.end();
//        sr.end();
    }

    void drawLines(){
        int w = 20;
        int h = 20;
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.setColor(0.3f, 0.6f, 0.3f, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < GRID_SIZE; i++) {
            sr.line(0, i, w, i); // horizontal
            sr.line(i, 0, i, h); // vertical
        }
        sr.end();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
