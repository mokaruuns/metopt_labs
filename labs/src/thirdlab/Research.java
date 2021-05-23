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

    public NumberVector solve(UsualMatrix matrix, NumberVector numberVector, int n) {
        ProfileMatrix a = new ProfileMatrix(matrix.getListListDouble(), n);
        a.turnTOLU();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(a.get(i, j) + " ");
//            }
//            System.out.println();
//        }
        return new NumberVector(a.solveSLAU(numberVector.getVector()));
    }

    public void run(int n, int k) {
//        System.out.println("Размер: " + n + ", число обусловленности: " + k);
        UsualMatrix matrixA = new UsualMatrix(generate(k, n));
//        matrixA.printMatrix();
        NumberVector vectorX = new NumberVector(IntStream.range(1, n + 1).mapToDouble(z -> (double) z).boxed().collect(Collectors.toList()));
//        vectorX.printNumberVector();
        checkLoss(n, k, vectorX, solve(matrixA, vectorX, n));
    }

    private void checkLoss(int n, int k, NumberVector expectedSolution, NumberVector currentSolution) {
        double difference = expectedSolution.addVector(currentSolution.mulOnNumber(-1)).norma();
        System.out.println(n + " " + k + " " + difference + " " + difference / expectedSolution.norma());
    }

    public static void main(String[] args) {
        Research research = new Research();
        for (int c = 1; c <= 3; c++) {
            for (int k = 0; k < 10; k++) {
                research.run((int) Math.pow(10, c), k);
            }
        }
    }

}
