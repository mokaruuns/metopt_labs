package secondLab;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NumberVector {

    private List<Double> vector;

    public NumberVector(List<Double> vector) {
        this.vector = vector;
    }

    public NumberVector mulOnNumber(double a) {
        return new NumberVector(vector.stream().map(i -> i * a).collect(Collectors.toList()));
    }

    public double mulOnVector(NumberVector numberVector) {
        if (numberVector.vector.size() != vector.size()) {
            throw new IllegalArgumentException("Vectors sizes are not equal");
        }
        double res = 0;
        for (int i = 0; i < vector.size(); i++) {
            res += vector.get(i) * numberVector.vector.get(i);
        }
        return res;
    }

    public double norma() {
        return Math.sqrt(mulOnVector(new NumberVector(vector)));
    }

    public NumberVector normalize() {
        return new NumberVector(vector).mulOnNumber(1 / new NumberVector(vector).norma());
    }

    public double get(int i) {
        return vector.get(i);
    }

    public void set(int i, double d) {
        vector.set(i, d);
    }

    public NumberVector addVector(NumberVector nv) {
        List<Double> newVector = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            newVector.add(get(i) + nv.get(i));
        }
        return new NumberVector(newVector);
    }

    public int size() {
        return vector.size();
    }

    public List<Double> getVector() {
        return new ArrayList<>(vector);
    }
    public void printNumberVector() {
        for(double c: this.vector){
            System.out.print(c + " ");
        }
        System.out.println();
    }
}
