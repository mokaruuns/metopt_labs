import java.util.List;

public interface Matrix {

    public NumberVector mulOnVector(NumberVector numberVector);

    public Matrix mulOnNumber(double number);

    public double get(int i, int j);

}
