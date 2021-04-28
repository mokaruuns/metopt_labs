package secondLab;

public interface Matrix {

    public NumberVector mulOnVector(NumberVector numberVector);

    public Matrix mulOnNumber(double number);

    public double get(int i, int j);

    public double apply(NumberVector vector);

}
