package brain;

import java.util.List;

public class NeuralNetwork {
    public Matrix weights_ih; // weight for input and hidden layers
    public Matrix weights_ho; // weight for hidden and output layers
    public Matrix bias_h; // bias for hidden layer
    public Matrix bias_o; // bias for output layer

    public Matrix input;
    public Matrix hidden;
    public Matrix output;

    private float accuracy;

    public final double l_rate = 0.01;

    public NeuralNetwork(int i, int h, int o){
        weights_ih = new Matrix(h, i);
        weights_ho = new Matrix(o, h);

        bias_h = new Matrix(h, 1);
        bias_o = new Matrix(o, 1);
    }

    public List<Double> predict(double[] x){
        input = Matrix.fromArray(x);
        hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.sigmoid();

        return output.toArray();
    }

    public void train(double [] X,double [] Y)
    {
        input = Matrix.fromArray(X);
        hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        output = Matrix.multiply(weights_ho,hidden);
        output.add(bias_o);
        output.sigmoid();

        Matrix target = Matrix.fromArray(Y);

        Matrix error = Matrix.subtract(target, output);
        accuracy = (float) (1 - error.data[0][0]);


        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(l_rate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta =  Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_T = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_T, error);

        Matrix h_gradient = hidden.dsigmoid();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(l_rate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        bias_h.add(h_gradient);

    }

    public void fit(double[][] x, double[][] y, int epochs){
        for(int i = 0; i < epochs; i++){
            int sampleN = (int) (Math.random() * x.length);
            this.train(x[sampleN], y[sampleN]);
        }
    }

    public float getAccuracy(){
        return accuracy;
    }

}
