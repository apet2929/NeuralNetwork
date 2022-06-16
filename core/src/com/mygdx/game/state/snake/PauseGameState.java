package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.state.GameStateManager;
import com.mygdx.game.state.State;

public class PauseGameState implements State {

    private SnakeGameState game;
    private ShapeRenderer sr;
    public PauseGameState(SnakeGameState state){
        this.game = state;
        sr = new ShapeRenderer();
    }
    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            GameStateManager.getInstance().switchState("game");
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        game.render(sb);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {

    }
}
