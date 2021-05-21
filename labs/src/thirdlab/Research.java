package thirdlab;

import secondLab.Matrix;
import secondLab.NumberVector;
import secondLab.UsualMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Research {

    List<List<Double>> generate(int k, int n) {
        int start = -4, end = 0;
        double sum = 0;
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                double random_element = (int) (start + (Math.random() * ((end - start) + 1)));
                if (i != j) {
                    sum += random_element;
                }
                temp.add(random_element);
            }
            matrix.add(temp);
        }
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                matrix.get(i).set(i, -sum);
            } else {
                matrix.get(i).set(i, -sum + Math.pow(10, -k));
            }
        }
        return matrix;
    }

    public void solve(UsualMatrix matrix, NumberVector numberVector, int n) {
        ProfileMatrix a = new ProfileMatrix(matrix.getListListDouble(), n);
        a.turnTOLU();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a.get(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println(a.solveSLAU(numberVector.getVector()));
    }

    public void run(int k) {
        int n = 3;
        for (int i = 0; i < k; i++) {
            UsualMatrix matrixA = new UsualMatrix(generate(i, n));
            matrixA.printMatrix();
            NumberVector vectorX = new NumberVector(IntStream.range(1, n + 1).mapToDouble(z -> (double) z).boxed().collect(Collectors.toList()));
            vectorX.printNumberVector();
            solve(matrixA, vectorX, n);
        }
    }


    public static void main(String[] args) {
        Research research = new Research();
        research.run(1);
    }

}
