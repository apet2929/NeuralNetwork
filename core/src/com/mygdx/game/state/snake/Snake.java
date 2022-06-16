package com.mygdx.game.state.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.*;

import static com.mygdx.game.state.snake.SnakeGameState.SCALE;

public class Snake {
    int length;
    Stack<Vector2> body;
    Direction direction;
    Direction nextDirection;

    HashMap<String, Texture> textures;
    Runnable onGameOver;

    public Snake() {
        length = 5;
        body = new Stack<>();

        body.push(new Vector2(1, 2));
        body.push(new Vector2(2, 2));
        body.push(new Vector2(3, 2));
        body.push(new Vector2(4, 2));
        body.push(new Vector2(5, 2));
        direction = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
        initTextures();
    }

    public void update(Apple apple){
        assert this.onGameOver != null;
        if(this.isIntersectingHead(apple.getWorldPosition())){
            System.out.println("Ate apple!!");
            apple.collect(this);
            this.length++;
        }
        body.push(getHead().add(direction.incPos));

        if(body.size() > length){
            body.remove(0);
        }
        if(this.isIntersectingExceptHead(this.getHead())){
            this.onGameOver.run();
        }
        if(this.getHead().x >= SnakeGameState.GRID_SIZE || this.getHead().x < 0 || this.getHead().y >= SnakeGameState.GRID_SIZE || this.getHead().y < 0){
            this.onGameOver.run();
        }


    }

    public void render(SpriteBatch sb){
        for (int i = 0; i < body.size(); i++) {
            drawBodyPart(sb, i);
        }
    }

    public void debugRender(ShapeRenderer sr){
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0,1,0.2f,1);
        for (Vector2 pos : body) {
            sr.rect(pos.x * SCALE, pos.y * SCALE, SCALE, SCALE);
        }
    }

    void drawBodyPart(SpriteBatch sb, int index){
        String texturePath;
        if(index == 0) {
            texturePath = this.getTailTexture();
        }
        else if(index > 0 && index < this.length-1){
            texturePath = getBodyTexture(index);
        } else {
            texturePath = this.getHeadTexture();
        }
        Texture texture = this.textures.get(texturePath);
        sb.draw(texture, getBodyPart(index).x, getBodyPart(index).y, 1, 1);
    }

    String getBodyTexture(int index){
        Vector2 nextBlock = getBodyPart(index - 1).sub(getBodyPart(index));
        Vector2 prevBlock = getBodyPart(index + 1).sub(getBodyPart(index));
        if(prevBlock.x == nextBlock.x){
//            Snake is vertical and not turning
            return "body_vertical.png";
        } else if(prevBlock.y == nextBlock.y){
            return "body_horizontal.png";
        } else return getTurnDirection(prevBlock, nextBlock);
    }

    String getTurnDirection(Vector2 prevBlock, Vector2 nextBlock) {
        if((prevBlock.x == -1 && nextBlock.y == 1) || (prevBlock.y == 1 && nextBlock.x == -1)) return "body_topleft.png";
        else if((prevBlock.x == -1 && nextBlock.y == -1) || (prevBlock.y == -1 && nextBlock.x == -1)) return "body_bottomleft.png";
        else if((prevBlock.x == 1 && nextBlock.y == 1) || (prevBlock.y == 1 && nextBlock.x == 1)) return "body_topright.png";
        else if((prevBlock.x == 1 && nextBlock.y == -1) || (prevBlock.y == -1 && nextBlock.x == 1)) return "body_bottomright.png";
        System.err.println("Turn direction invalid! Debug getTurnDirection() in Snake");
        System.err.println(this.body);
        return "";
    }

    String getTailTexture(){
        return "tail_" + getHeadOrTailTexture(0, 1) + ".png";
    }
    String getHeadTexture(){
        return "head_" + getHeadOrTailTexture(length-1, length-2) + ".png";
    }

    String getHeadOrTailTexture(int index, int compareTo){
        Vector2 current = getBodyPart(index);
        Vector2 compare = getBodyPart(compareTo);
        Vector2 relation = current.sub(compare);
        if(relation.equals(Direction.UP.incPos)){
            return "up";
        } else if(relation.equals(Direction.DOWN.incPos)){
            return "down";
        } else if(relation.equals(Direction.LEFT.incPos)) {
            return "left";
        } else if(relation.equals(Direction.RIGHT.incPos)) {
            return "right";
        }
        System.err.println("Relationship between two body parts is invalid! getHeadOrTailTexture()");
        return "";
    }

    void turn(Direction yee){
        if(!canTurn(yee)) return;
        this.direction = yee;
//        Vector2 headPos = this.body.pop();
//        this.body.push(new Vector2(Math.round(headPos.x), Math.round(headPos.y))); // ensure head isn't between spaces
    }

    boolean canTurn(Direction direction){
//        if(this.direction == Direction.UP || this.direction == Direction.DOWN) {
//            return direction != Direction.DOWN && direction != Direction.UP;
//        }
//        else {
//            return direction != Direction.LEFT && direction != Direction.RIGHT;
//        }
        Vector2 newPos = getHead().add(direction.incPos);
        return !isIntersecting(newPos);
    }

    boolean isIntersectingExceptHead(Vector2 point){
        for (int i = 0; i < length - 2; i++) {
            Vector2 bodyPart = this.body.get(i);
            if(bodyPart.x == point.x && bodyPart.y == point.y) return true;
        }
        return false;
    }

    boolean isIntersecting(Vector2 point){
        for (Vector2 b :
                body) {
            if(b.x == point.x && b.y == point.y) return true;
        }
        return false;
    }

    boolean isIntersectingHead(Vector2 point){
        return point.x == getHead().x && point.y == getHead().y;
    }

    Vector2 getHead(){
        Vector2 head = this.body.peek();
        return new Vector2(head.x, head.y);
    }

    void initTextures(){
        this.textures = new HashMap<>();
        loadTexture("head_up.png");
        loadTexture("head_down.png");
        loadTexture("head_left.png");
        loadTexture("head_right.png");

        loadTexture("tail_up.png");
        loadTexture("tail_down.png");
        loadTexture("tail_left.png");
        loadTexture("tail_right.png");

        loadTexture("body_vertical.png");
        loadTexture("body_horizontal.png");
        loadTexture("body_topleft.png");
        loadTexture("body_topright.png");
        loadTexture("body_bottomleft.png");
        loadTexture("body_bottomright.png");
    }

    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;
    }

    void loadTexture(String path){
        textures.put(path, new Texture(Gdx.files.internal(path)));
    }

    Vector2 getBodyPart(int index){
        return new Vector2(this.body.get(index));
    }
}
