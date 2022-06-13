package brain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NetworkRenderer {
    private ShapeRenderer sr;
    private ArrayList<Vector2> nodes; // list of the positions of the nodes in the matrix
    private NeuralNetwork network;

    private float scale;
    private float radius;
    private float spacing;


    public NetworkRenderer(NeuralNetwork network) {
        this.network = network;
        nodes = new ArrayList<>();
        this.sr = new ShapeRenderer();

        initScale();
        initNodePositions();

    }

    public void draw(){
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        drawNodes();
        drawLines();
        drawErrorBar();

        sr.end();
    }

    private void initScale(){
        int maxNodesInColumn = Math.max(network.weights_ho.rows, network.weights_ih.rows);
        scale = getScale(maxNodesInColumn);
        radius = scale / 2f;
        spacing = scale / 4f;
    }

    private void drawErrorBar(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.setColor(0,0,0,1);
        sr.rect(w* 0.6f, h*0.15f, w * 0.2f, h * 0.05f);

        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.5f,0.5f,0,1);
        sr.rect(w * 0.61f,h * 0.16f, network.getAccuracy() * w * 0.18f, h * 0.03f);
    }

    private void drawLines(){
        int index = 0;
        int hiddenStartIndex = network.input.cols * network.input.rows;
        int outputStartIndex = hiddenStartIndex + (network.hidden.rows * network.hidden.cols);
        for (int i = 0; i < network.input.rows; i++) {
            drawNodesLines(index, network.input.cols * network.input.rows, network.hidden);
            index++;
        }

        for (int i = 0; i < network.hidden.rows; i++) {
            drawNodesLines(index, outputStartIndex, network.output);
            index++;
        }
    }

    private void drawNodesLines(int nodeIndex, int targetStartIndex, Matrix target){
        sr.setColor(0,0,0,1);
        List<Double> mNodes = target.toArray();
        for (int i = 0; i < mNodes.size(); i++) {
            sr.line(nodes.get(nodeIndex), nodes.get(targetStartIndex + i));
        }
    }

    private void drawNodes(){
        int index = 0;
        for (int i = 0; i < network.input.rows; i++) {
            for (int j = 0; j < network.input.cols; j++) {
                drawNode(index, network.input.data[i][j]);
                index++;
            }
        }
        for (int i = 0; i < network.hidden.rows; i++) {
            for (int j = 0; j < network.hidden.cols; j++) {
                drawNode(index, network.hidden.data[i][j]);
                index++;
            }
        }
        for (int i = 0; i < network.output.rows; i++) {
            for (int j = 0; j < network.output.cols; j++) {
                drawNode(index, network.output.data[i][j]);
                index++;
            }
        }
    }

    private void drawNode(int index, double value){
        float fl_val = (float) value;
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.setColor(0,0,0,1);
        sr.circle(nodes.get(index).x, nodes.get(index).y, radius);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.setColor(fl_val, fl_val, fl_val, 1);
        sr.circle(nodes.get(index).x, nodes.get(index).y, radius*0.95f);
    }

    private void initNodePositions(){
        initNetworkMatrixNodes(0, network.input);
        initNetworkMatrixNodes(1, network.hidden);
        initNetworkMatrixNodes(1 + network.hidden.cols, network.output);
    }

    private void initNetworkMatrixNodes(int column, Matrix matrix){
        for (int i = 0; i < matrix.cols; i++) {
            int col = column + i;
            float x = scale * ((col * 6) + 3);
            float margin = (Gdx.graphics.getHeight() - (matrix.rows * 2 * radius) - (spacing * (matrix.rows - 1)))/2;
            for (int j = 0; j < matrix.rows; j++) {
                float y = margin + radius + (j * (2 * radius + spacing));
                nodes.add(new Vector2(x, y));
            }
        }
    }

    private static float getScale(int maxNodesInColumn){
        int height = Gdx.graphics.getHeight();
        float maxDiameter = (float) height / maxNodesInColumn;
        return maxDiameter * 0.7f;
    }

    private static int getNumNodesInNetwork(NeuralNetwork network) {
        int sum = 0;
        sum += network.weights_ih.cols * network.weights_ih.rows;
        sum += network.weights_ho.cols * network.weights_ho.rows;
        return sum;
    }


    private List<Double> getNodeValues() {
        ArrayList<Double> nodeVals = (ArrayList<Double>) network.input.toArray();
        nodeVals.addAll(network.weights_ho.toArray());
        return nodeVals;
    }

}

