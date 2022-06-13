package com.mygdx.game.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashMap;
import java.util.Stack;

public class GameStateManager {

    private static GameStateManager instance;
    public static GameStateManager getInstance() {
        if(instance == null) {
            return new GameStateManager();
        }
        return instance;
    }

    public GameStateManager(){
        if(instance != null){
            throw new Error("gsm has already been instantiated!");
        }
        instance = this;
        gameStates = new HashMap<>();
        sb = new SpriteBatch();

    }

    private final HashMap<String, State> gameStates;
    private State currentState;
    private final SpriteBatch sb;


    public State peek(){
        return this.currentState;
    }

    public void put(String name, State state){
        this.gameStates.put(name, state);
        state.onEnter();
        currentState = state;
    }

    public void switchState(String name){
        currentState.onExit();
        currentState = gameStates.get(name);
        currentState.onEnter();
    }

    public void handleInput(){
        currentState.handleInput();
    }

    public void update(float delta){
        currentState.update(delta);
    }

    public void render(){
        ScreenUtils.clear(1,1,1,1);
        currentState.render(sb);
    }

    public void dispose() {
        // disposes of all states
        this.gameStates.values().forEach(State::dispose);
    }

    public void resize(int width, int height) {
        // resizes all states
        this.gameStates.values().forEach((State state) -> state.resize(width, height));
    }



}
