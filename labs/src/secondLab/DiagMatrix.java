package secondLab;

import java.util.ArrayList;
import java.util.List;

public class DiagMatrix implements Matrix {

    private final NumberVector diag;

    public DiagMatrix(List<Double> diag) {
        this.diag = new NumberVector(diag);
    }

    @Override
    public NumberVector mulOnVector(NumberVector numberVector) {
        if (numberVector.size() != diag.size()) {
            throw new IllegalArgumentException("Vectors sizes are not equal");
        }
        List<Double> newMatrix = new ArrayList<>();
        for (int i = 0; i < diag.size(); i++) {
            newMatrix.add(diag.get(i) * numberVector.get(i));
        }
        return new NumberVector(newMatrix);
    }

    @Override
    public Matrix mulOnNumber(double number) {
        return new DiagMatrix(diag.mulOnNumber(number).getVector());
    }



    @Override
    public double get(int i, int j) {
        return 0;
    }

    @Override
    public double apply(NumberVector vector) {
        double ans = 0;
        for (int i = 0; i < diag.size(); i++) {
            ans += vector.get(i) * vector.get(i) * diag.get(i);
        }
        return ans;
    }
}
