package thirdLab;

import secondLab.NumberVector;
import secondLab.UsualMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Research {

    private UsualMatrix generate(int size, int conditionality) {
        int start = -4, end = 0;
        double sum = 0;
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                double random_element = (int) (start + (Math.random() * ((end - start) + 1)));
                if (i != j) {
                    sum += random_element;
                }
                temp.add(random_element);
            }
            matrix.add(temp);
        }
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                matrix.get(i).set(i, -sum);
            } else {
                matrix.get(i).set(i, -sum + Math.pow(10, -conditionality));
            }
        }
        return new UsualMatrix(matrix);
    }

    private UsualMatrix generate(int size) {
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                temp.add(1.0 / ((i + 1) + (j + 1) - 1));
            }
            matrix.add(temp);
        }
        return new UsualMatrix(matrix);
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

    public void run(int size, int conditionality) {
        UsualMatrix matrixA;
        if (conditionality != -1) {
            matrixA = generate(size, conditionality);
        } else {
            matrixA = generate(size);
        }
        NumberVector vectorX = new NumberVector(IntStream.range(1, size + 1).mapToDouble(z -> (double) z).boxed().collect(Collectors.toList()));
        checkLoss(size, conditionality, vectorX, solve(matrixA, vectorX, size));
    }


    public void runAll(int maxSize, int maxConditionality) {
        for (int n = 10; n <= maxSize; n *= 10) {
            for (int k = 0; k <= maxConditionality; k++) {
                run(n, k);
            }
        }
    }

    public void runAll(int maxSize) {
        for (int n = 10; n <= maxSize; n *= 10) {
            run(n, -1);
        }
    }

    private void checkLoss(int n, int k, NumberVector expectedSolution, NumberVector currentSolution) {
        double difference = expectedSolution.addVector(currentSolution.mulOnNumber(-1)).norma();
        System.out.println(n + " " + k + " " + difference + " " + difference / expectedSolution.norma());
    }

    public static void main(String[] args) {
        Research research = new Research();
        research.runAll(1000, 10);
    }

}
