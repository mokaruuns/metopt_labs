package thirdLab;

import secondLab.Matrix;
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

    private UsualMatrix generate(int size, int min, int max) {
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                double random_element = (min + (Math.random() * ((max - min) + 1)));
                temp.add(random_element);
            }
            matrix.add(temp);
        }
        return new UsualMatrix(matrix);
    }

    private NumberVector generateNumberVector(int size, int min, int max) {
        List<Double> vector = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double random_element = (int) (min + (Math.random() * ((max - min) + 1)));
            vector.add(random_element);
        }
        return new NumberVector(vector);
    }

    public NumberVector solve(UsualMatrix matrix, NumberVector numberVector, int n) {
        ProfileMatrix a = new ProfileMatrix(matrix.getListListDouble(), n, false);
        a.turnTOLU();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(a.get(i, j) + " ");
//            }
//            System.out.println();
//        }
        return new NumberVector(a.LUSolve(numberVector.getVector()));
    }

    public void solveAndCompare(UsualMatrix matrix, NumberVector expectedVector, NumberVector vectorB, int n) {
        ProfileMatrix a = new ProfileMatrix(matrix.getListListDouble(), n, true);
        ProfileMatrix b = new ProfileMatrix(matrix.getListListDouble(), n, true);
        a.turnTOLU();
        NumberVector HausseSolution = new NumberVector(b.swapSolve(vectorB.getVector()));
        NumberVector LUSolution = new NumberVector(a.LUSolve(vectorB.getVector()));
        System.out.print(n + " ");
        checkLoss(expectedVector, HausseSolution);
        checkLoss(expectedVector, LUSolution);
        System.out.println();
    }

    public void run(int size, int conditionality) {
        UsualMatrix matrixA;
        if (conditionality != -1) {
            matrixA = generate(size, conditionality);
        } else {
            matrixA = generate(size);
        }
        NumberVector rightSolution = new NumberVector(IntStream.range(1, size + 1).mapToDouble(z -> (double) z).boxed().collect(Collectors.toList()));
        checkLoss(size, conditionality, rightSolution, solve(matrixA, matrixA.mulOnVector(rightSolution), size));
    }

    public void runFour() {
        for (int j = 1; j <= 3; j++) {
            int size = (int) Math.pow(10, j);
            for (int i = 0; i < 5; i++) {
                UsualMatrix matrixA = generate(size, -1000, 1000);
                NumberVector rightSolution = generateNumberVector(size, -1000, 1000);
                NumberVector rightAnswer = matrixA.mulOnVector(rightSolution);
                solveAndCompare(matrixA, rightSolution, rightAnswer, size);
            }
        }
    }


    public void runAll(int maxSize, int maxConditionality) {
        for (int n = 10; n <= maxSize; n *= 10) {
            for (int k = 0; k <= maxConditionality; k += 10) {
                run(n, k);
            }
        }
    }

    public void runAll(int maxSize) {
        for (int n = 1; n <= maxSize; n += 100) {
            run(n, -1);
        }
    }

    private void checkLoss(int n, int k, NumberVector expectedSolution, NumberVector currentSolution) {
        double difference = expectedSolution.addVector(currentSolution.mulOnNumber(-1)).norma();
        System.out.println(n + " " + k + " " + difference + " " + difference / expectedSolution.norma());
    }

    private void checkLoss(NumberVector expectedSolution, NumberVector currentSolution) {
        double difference = expectedSolution.addVector(currentSolution.mulOnNumber(-1)).norma();
        System.out.print(difference + " " + difference / expectedSolution.norma() + " ");
    }

    public static void main(String[] args) {
        Research research = new Research();
//        research.runAll(1000, 100);
//        research.runAll(1001);
//        research.run(100, 4);
        research.runFour();
    }

}
