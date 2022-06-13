package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TestState1 extends BaseState {

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {
        System.out.println("Exiting!");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            GameStateManager.getInstance().switchState("net");
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        ScreenUtils.clear(1,0.5f,0.2f, 1);

    }
}
