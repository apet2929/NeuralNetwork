package com.mygdx.game;


import brain.NetworkRenderer;
import brain.NeuralNetwork;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.state.GameStateManager;
import com.mygdx.game.state.NetworkState;
import com.mygdx.game.state.TestState1;
import com.mygdx.game.state.snake.PauseGameState;
import com.mygdx.game.state.snake.SnakeGameState;

import java.util.Arrays;
import java.util.List;

public class Main extends ApplicationAdapter {
	GameStateManager gsm;
	@Override
	public void create () {
		gsm = new GameStateManager();
		gsm.put("net", new NetworkState());
		gsm.put("game", new SnakeGameState());
		gsm.put("pause", new PauseGameState((SnakeGameState) gsm.peek()));
	}

	@Override
	public void render () {
		gsm.handleInput();
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}
	
	@Override
	public void dispose () {
		gsm.dispose();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height);
	}
}
