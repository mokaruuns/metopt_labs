package thirdlab;

import secondLab.Matrix;
import secondLab.NumberVector;

import java.util.ArrayList;
import java.util.List;

public class DenseMatrix {
    private final List<List<Double>> matrix;
    private final List<Double> b;
    private List<Double> x;

    private DenseMatrix(List<List<Double>> m, List<Double> b) {
        this.matrix = m;
        this.b = b;
    }

    private DenseMatrix(int n, int m) {
        List<List<Double>> temp_matrix = new ArrayList<>();
        List<Double> temp_b = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            temp_matrix.add(new ArrayList<>());
            temp_b.add(Math.random());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp_matrix.get(i).add(Math.random());
            }
        }

        this.matrix = temp_matrix;
        this.b = temp_b;
    }

    private int findLeaderInRowWithColumn(int row, int column) {
        double maxElement = (double) 0;
        int numberRow = 0;

        for (int i = row; i < matrix.size(); i++) {
            Double cur_elem = matrix.get(i).get(column);
            if (Math.abs(cur_elem) > Math.abs(maxElement)) {
                maxElement = cur_elem;
                numberRow = i;
            }
        }

        return numberRow;
    }

    private void swapRow(int n, int m) {
        List<Double> temp = matrix.get(n);
        matrix.set(n, matrix.get(m));
        matrix.set(m, temp);

        Double temp_b = b.get(n);
        b.set(n, b.get(m));
        b.set(m, temp_b);
    }

    private void swapColumn(int n, int m) {
        for (List<Double> doubles : matrix) {
            Double temp = doubles.get(n);
            doubles.set(n, doubles.get(m));
            doubles.set(m, temp);
        }
    }

    private void solve() {
        for (int i = 0; i < matrix.size() - 1; i++) {
            int rowMaxNumber = findLeaderInRowWithColumn(i, i);

            if (matrix.get(rowMaxNumber).get(i) == 0) {
                System.out.println("SLAU don't have solve");
                return;
            }

            if (rowMaxNumber > i) {
                while (rowMaxNumber != i) {
                    swapRow(rowMaxNumber, rowMaxNumber - 1);
                    rowMaxNumber--;
                }
            }

            print();

            for (int i1 = i + 1; i1 < matrix.size(); i1++) {
                Double ratio = matrix.get(i1).get(i) / matrix.get(i).get(i);
                System.out.println(ratio);
                for (int j = i; j < matrix.get(i1).size(); j++) {
                    Double new_elem = matrix.get(i).get(j) * ratio - matrix.get(i1).get(j);
                    System.out.println(new_elem);
                    matrix.get(i1).set(j, new_elem);
                }
                Double new_elem_b = b.get(i) * ratio - b.get(i1) * ratio;
                b.set(i1, new_elem_b);
            }
        }

        for (int i = matrix.size() - 1; i >= 0; i--) {
            x.set(i, (double) 0);
            Double cur_value = (double) 0;
            for (int j = matrix.size() - 1; j > i; j--) {
                cur_value += matrix.get(i).get(j) * x.get(j);
            }
            Double new_value = (b.get(i) - cur_value) / matrix.get(i).get(i);
            x.set(i, new_value);
        }
    }

    private void print() {
        for (List<Double> doubles : matrix) {
            for (Double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<List<Double>> temp = List.of(List.of(10.0, -7.0, 0.0),
                List.of(-3.0, 2.0, 6.0),
                List.of(5.0, -1.0, 5.0));
        List<Double> temp_b = List.of(7.0, 4.0, 6.0);
        DenseMatrix a = new DenseMatrix(temp, temp_b);
        a.print();
        a.solve();
    }
}
