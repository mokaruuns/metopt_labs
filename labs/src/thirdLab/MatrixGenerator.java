package thirdLab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixGenerator {
    public static final Random RANDOM = new Random();

    private Double rand(Double min, Double max){
        max -= min;
        return (Math.random() * max) + min;
    }

    private int randInteger(Double min, Double max){
        max -= min;
        return (int)((Math.random() * max) + min);
    }

    public static List<List<Double>> generateOrdinaryMatrix(final int dimension) {
        List<List<Double>> matrix = new ArrayList<>();
        List<Integer> profile = new ArrayList<>();

        for(int i = 0; i < dimension; i++){
            matrix.add(new ArrayList<>());
            for(int j = 0; j < dimension; j++){
                matrix.get(i).add(0.0);
            }
        }

        for (int i = 0; i < dimension; ++i) {
            profile.add(RANDOM.nextInt(i + 1));
        }

        for (int row = 0; row < dimension; row++) {
            for (int col = profile.get(row); col < row; col++) {
                matrix.get(row).set(col, (double) (-RANDOM.nextInt(4) - 1));
            }
        }

        for (int col = 0; col < dimension; ++col) {
            for (int row = profile.get(col); row < col; ++row) {
                matrix.get(row).set(col, (double) (-RANDOM.nextInt(4) - 1));
            }
        }

        for (int i = 0; i < dimension; ++i) {
            double sum = 0;
            for (int j = 0; j < dimension; ++j) {
                if (j != i) {
                    sum -= matrix.get(i).get(j);
                }
            }
            matrix.get(i).set(i, sum);
            if (i == 0) {
                matrix.get(i).set(i, matrix.get(i).get(i) + 1);
            }
        }

        return matrix;
    }

    public static List<List<Double>> generateDiagonalDominationMatrix(int dimension) {
        List<List<Double>> matrix = new ArrayList<>();
        List<Integer> profile = new ArrayList<>();

        for(int i = 0; i < dimension; i++){
            matrix.add(new ArrayList<>());
            for(int j = 0; j < dimension; j++){
                matrix.get(i).add(0.0);
            }
        }

        for (int i = 0; i < dimension; ++i) {
            profile.add(RANDOM.nextInt(i + 1));
        }

        for (int row = 0; row < dimension; ++row) {
            for (int col = profile.get(row); col < row; ++col) {
                int val = -RANDOM.nextInt(4) - 1;
                matrix.get(row).set(col, (double) val);
                matrix.get(col).set(row, (double) val);
            }
        }

        for (int i = 0; i < dimension; ++i) {
            double sum = 0;
            for (int j = 0; j < dimension; ++j) {
                if (j != i) {
                    sum -= matrix.get(i).get(j);
                }
            }

            matrix.get(i).set(i, sum);
            if (i == 0) {
                matrix.get(i).set(i, matrix.get(i).get(i) + 1);
            }
        }

        return matrix;
    }
}
