package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.state.BaseState;
import com.mygdx.game.state.GameStateManager;

public class SnakeGameState extends BaseState {
    FitViewport viewport;
//    OrthographicCamera camera;
    ShapeRenderer sr;
    Snake snake;
    Apple apple;
    float elapsedTime;
    final float tickSpeed = 0.09f;
    public static final int GRID_SIZE = 15;
    public static final float SCALE = 1;

    public SnakeGameState(){
        sr = new ShapeRenderer();
        viewport = new FitViewport(GRID_SIZE, GRID_SIZE);
        init();
    }

    @Override
    public void onEnter() {

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            System.out.println("apple.getWorldPosition() = " + apple.getWorldPosition());
            System.out.println("snake.body = " + snake.body);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            GameStateManager.getInstance().switchState("pause");
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        viewport.apply(true);
        drawLines();
        drawSprites(sb);

    }

    void drawSprites(SpriteBatch sb){
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        apple.draw(sb);
        snake.render(sb);
        sb.end();
    }

    void drawLines(){
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.setColor(0.3f, 0.6f, 0.3f, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < GRID_SIZE; i++) {
            sr.line(0, i, GRID_SIZE, i); // horizontal
            sr.line(i, 0, i, GRID_SIZE); // vertical
        }
        sr.end();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    void reset(){
        init();
        GameStateManager.getInstance().switchState("pause");
    }

    void init(){
        snake = new Snake();
        snake.setOnGameOver(() -> {
            System.out.println("Game over!");
            reset();
        });
        apple = new Apple(snake);
    }
}
