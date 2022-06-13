package brain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

    @Test
    public void NetworkShouldGuessWell(){
        NeuralNetwork network = new NeuralNetwork(2, 10, 1);

        System.out.println("INITIAL network.weights_ih.toString() = " + network.weights_ih.toString());
        System.out.println("INITIAL network.weights_ho.toString() = " + network.weights_ho.toString());
        System.out.println("INITIAL network.bias_h.toString() = " + network.bias_h.toString());
        System.out.println("INITIAL network.bias_o.toString() = " + network.bias_o.toString());
        System.out.println();

        double[][] trainingInput = new double[][]{
                {0, 0},
                {1, 0},
                {0, 1},
                {1, 1}
        };
        double[][] trainingExpectedOutput = new double[][]{
                {0}, {1}, {1}, {1}
        };

        network.fit(trainingInput, trainingExpectedOutput, 50000);

        System.out.println("FINAL network.weights_ih.toString() = " + network.weights_ih.toString());
        System.out.println("FINAL network.weights_ho.toString() = " + network.weights_ho.toString());
        System.out.println("FINAL network.bias_h.toString() = " + network.bias_h.toString());
        System.out.println("FINAL network.bias_o.toString() = " + network.bias_o.toString());
        System.out.println();

        double[][] testInput = {
                {0,0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        List<Double> testOutput;
        for (double[] d : testInput) {
            testOutput = network.predict(d);
            System.out.println("Arrays.toString(output) = " + testOutput.toString());
        }
    }

}