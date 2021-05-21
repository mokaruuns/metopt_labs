package secondLab;

import java.util.List;
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

    @Override
    public double apply(NumberVector vector) {
        double ans = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                ans += vector.get(i) * vector.get(j) * get(i, j) / 2;
            }
        }
        return ans;
    }

    public List<List<Double>> getListListDouble(){
        return this.matrix.stream().map(NumberVector::getVector).collect(Collectors.toList());
    }

    public void printMatrix(){
        for (NumberVector row: this.matrix) {
            for(double c: row.getVector()) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}
