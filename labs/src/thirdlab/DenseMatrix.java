package thirdlab;

import java.util.ArrayList;
import java.util.List;

public class DenseMatrix {
    private final List<List<Double>> matrix;
    private final List<Double> b;

    private DenseMatrix(List<List<Double>> m, List<Double> b) {
        this.matrix = m;
        this.b = b;
    }

    private DenseMatrix(int n, int m) {
        List<List<Double>> temp_matrix = new ArrayList<>();
        List<Double> temp_b = new ArrayList<>();

        for(int i = 0; i < n; i++){
            temp_matrix.add(new ArrayList<>());
            temp_b.add(Math.random());
        }

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp_matrix.get(i).add(Math.random());
            }
        }

        this.matrix = temp_matrix;
        this.b = temp_b;
    }

    private int findLeaderInColumn() {
        Double maxElement = Double.valueOf(0);
        int numberColumn = 0;

        for(int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                Double curElement = matrix.get(i).get(j);
                if(Math.abs(curElement) > Math.abs(maxElement)){
                    maxElement = curElement;
                    numberColumn = j;
                }
            }
        }

        return numberColumn;
    }

    private int findLeaderInRow() {
        Double maxElement = Double.valueOf(0);
        int numberRow = 0;

        for(int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                Double curElement = matrix.get(i).get(j);
                if(Math.abs(curElement) > Math.abs(maxElement)){
                    maxElement = curElement;
                    numberRow = i;
                }
            }
        }

        return numberRow;
    }

    private void swapRow(int n, int m) {
        List<Double> temp = matrix.get(n);
        matrix.set(n, matrix.get(m));
        matrix.set(m , temp);
    }

    private void swapColumn(int n, int m) {
        for(int i = 0; i < matrix.size(); i++)
        {
            Double temp = matrix.get(i).get(n);
            matrix.get(i).set(n, matrix.get(i).get(m));
            matrix.get(i).set(m, temp);
        }
    }

    private void print() {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DenseMatrix a = new DenseMatrix(3, 3);
    }
}
