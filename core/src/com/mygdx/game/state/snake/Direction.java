package com.mygdx.game.state.snake;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP(new Vector2(0, 1)),
    DOWN(new Vector2(0, -1)),
    LEFT(new Vector2(-1, 0)),
    RIGHT(new Vector2(1, 0));

    public final Vector2 incPos;
    Direction(Vector2 incPos){
        this.incPos = incPos;
    }
}
