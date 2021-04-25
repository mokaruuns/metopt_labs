import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class UsualMatrix implements Matrix {

    private final List<NumberVector> matrix;

    public UsualMatrix(List<List<Double>> matrix) {
        this.matrix = matrix.stream().map(NumberVector::new).collect(Collectors.toList());
    }

    @Override
    public NumberVector mulOnVector(NumberVector nv) {
        return new NumberVector(matrix.stream().map(numberVector -> numberVector.mulOnVector(nv)).collect(Collectors.toList()));
    }

    @Override
    public Matrix mulOnNumber(double number) {
        return new UsualMatrix(matrix.stream().map(numberVector -> numberVector.mulOnNumber(number)).
                map(NumberVector::getVector).collect(Collectors.toList()));
    }

    @Override
    public double get(int i, int j) {
        return matrix.get(i).get(j);
    }
}
