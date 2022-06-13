package com.mygdx.game;


import brain.NetworkRenderer;
import brain.NeuralNetwork;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;
import java.util.List;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;

	Texture img;
	double[][] x;
	double[][] y;

	int testIndex = 0;
	double[][] input = {
			{0,0},
			{0, 1},
			{1, 0},
			{1, 1}
	};
	NeuralNetwork network;
	NetworkRenderer nr;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		// guess whether a point is inside or outside a circle
		x = new double[][]{
				{0, 0},
				{1, 0},
				{0, 1},
				{1, 1}
		};
		y = new double[][]{
				{0}, {1}, {1}, {1}
		};
		network = new NeuralNetwork(2, 15, 1);
		long startTime = System.currentTimeMillis();

		long endTime = System.currentTimeMillis();

		System.out.println("Training took " + (endTime - startTime) + " milliseconds");

		double[][] input = {
				{0,0},
				{0, 1},
				{1, 0},
				{1, 1}
		};

		List<Double> output;
		for (double[] d : input) {
			output = network.predict(d);
			System.out.println("Arrays.toString(output) = " + output.toString());
		}

		nr = new NetworkRenderer(network);

	}

	@Override
	public void render () {
		trainAndTestNetwork();

		ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1);
		nr.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
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
