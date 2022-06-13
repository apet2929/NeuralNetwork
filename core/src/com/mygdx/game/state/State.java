package com.mygdx.game.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface State {
    void onEnter();
    void onExit();
    void update(float delta);  // returns the new state to transition to or null
    void handleInput();        // returns the new state to transition to or null
    void render(SpriteBatch sb);
    void resize(int width, int height);
    void dispose();
}
