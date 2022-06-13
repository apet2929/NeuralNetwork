package com.mygdx.game.state;

import brain.NetworkRenderer;
import brain.NeuralNetwork;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class NetworkState extends BaseState {
    NetworkRenderer nr;
    NeuralNetwork network;

    double[][] x;
    double[][] y;

    int testIndex = 0;
    double[][] input;

    public NetworkState(){
        network = new NeuralNetwork(2, 15, 1);
        nr = new NetworkRenderer(network);

        x = new double[][]{
                {0, 0},
                {1, 0},
                {0, 1},
                {1, 1}
        };
        y = new double[][]{
                {0}, {1}, {1}, {1}
        };

        input = new double[][]{
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

    }

    @Override
    public void onEnter() {


    }

    @Override
    public void update(float delta) {
        trainAndTestNetwork();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            GameStateManager.getInstance().switchState("game");
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        nr.draw();
    }

    void trainAndTestNetwork(){
        network.fit(x, y, 3);
        network.predict(input[testIndex]);
        testIndex++;
        if(testIndex >= input.length){
            testIndex = 0;
        }
    }
}
