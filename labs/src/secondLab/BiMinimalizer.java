package secondLab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BiMinimalizer {

    protected int dimensions;
    final private double DELTA = 0.000001;

    protected Matrix a;
    protected NumberVector b;
    protected double c;

    void checkMatrix(List<List<Double>> function, int dimensions) {
        if (function.size() != dimensions || !function.stream().allMatch(list -> list.size() == dimensions)) {
            throw new IllegalArgumentException("Given matrix should be a square matrix with correct dimension");
        }
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (!function.get(i).get(j).equals(function.get(j).get(i))) {
                    throw new IllegalArgumentException("Given matrix should be symmetric, but " +
                            "a[" + i + "][" + j + "] and a[" + j + "][" + i + "] are not equal: " +
                            function.get(i).get(j) + "!=" + function.get(j).get(i));
                }
            }
        }
    }

    void checkDim(List<Double> list) {
        if (list.size() != dimensions) {
            throw new IllegalArgumentException("Argument list has to have correct dimension: " + dimensions + " instead of " + list.size());
        }
    }

    double apply(List<Double> list) {
        checkDim(list);
        double ans = c + a.apply(new NumberVector(list));
        for (int i = 0; i < dimensions; i++) {
            ans += list.get(i) * b.get(i);
        }
        return ans;
    }

    protected double mod(NumberVector l) {
        return Math.sqrt(l.mulOnVector(l));
    }

    protected List<Double> sum(List<Double> l, List<Double> r) {
        checkDim(l);
        checkDim(r);
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < dimensions; i++) {
            ans.add(l.get(i) + r.get(i));
        }
        return ans;
    }

    protected double dist(NumberVector l, NumberVector r) {
        double dist = 0.0;
        for (int i = 0; i < dimensions; i++) {
            dist += Math.pow(l.get(i) - r.get(i), 2);
        }
        return dist;
    }

    NumberVector countGradient(NumberVector list) {
        return a.mulOnVector(list).addVector(b);
    }


    static List<Double> generateDaigMatrix(int conditionality, int dimensions) {
        List<Double> temp = new ArrayList<>();
        temp.add(1.0);
        for (int i = 1; i < dimensions - 1; i++) {
            temp.add((Math.random() * (conditionality - temp.get(i - 1))) + temp.get(i - 1));
        }
        temp.add((double) conditionality);
        return temp;
    }

    BiMinimalizer(List<List<Double>> a, List<Double> b, double c, int dimensions) {
        this.a = new UsualMatrix(a);
        this.b = new NumberVector(b);
        this.c = c;
        this.dimensions = dimensions;
    }

    BiMinimalizer(Matrix a, NumberVector b, double c, int dimensions) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.dimensions = dimensions;
    }

    abstract List<Double> minimalize();

    public static void printMinimalizer(BiMinimalizer biMinimalizer, int dimensions) {
//        System.out.println(biMinimalizer.apply(Collections.nCopies(dimensions, 1.0)));
//        System.out.println(biMinimalizer.countGradient(Collections.nCopies(dimensions, 1.0)));
        List<Double> x = biMinimalizer.minimalize();
//        System.out.println();
//        System.out.println(biMinimalizer.apply(x));
//        for (double x_i : x) {
//            System.out.print(x_i + " ");
//        }
//        System.out.println();
    }

    public static void printDiag() {
        List<Integer> dimensions = List.of(10, 100, 1000, 10000);
        List<Integer> conditionalities = List.of(1, 5, 10, 20, 50, 100, 150, 200, 250, 300, 400, 500, 600, 700, 800, 900, 1000, 1200, 1400, 1600, 1800, 2000);
        for (Integer dimension : dimensions) {
            for (Integer conditionality : conditionalities) {
                System.out.println("d = " + dimension + ", c = " + conditionality);
                for (int i = 1; i < 6; i++) {
                    Matrix a = new DiagMatrix(generateDaigMatrix(conditionality, dimension));
                    NumberVector b = new NumberVector(Collections.nCopies(dimension, 0.0));
                    double c = 0;
                    printAllMinimalizers(a, b, c, dimension);
                }
                System.out.println();
                System.out.println();
            }
        }
    }

    private static void printAllMinimalizers(Matrix a, NumberVector b, double c, int dimension) {
//        printMinimalizer(new GradientDescent(a, b, c, dimension), dimension);
//        printMinimalizer(new SteepestDescent(a, b, c, dimension), dimension);
        printMinimalizer(new FletcherReeves(a, b, c, dimension), dimension);

    }

    private static void printFirst() {
//        64 * x * x + 64 * y * y + 126 * x * y - 10 * x + 30 * y + 13
//        Matrix a = new UsualMatrix(List.of(List.of(128.0, 126.0), List.of(126.0, 128.0)));
//        NumberVector b = new NumberVector(List.of(-10.0, 30.0));
//        double c = 13;
//        150 * x * x + 40 * y * y - 24 * x + 10 * y + 10
        Matrix a = new UsualMatrix(List.of(List.of(300.0, 0.0), List.of(0.0, 80.0)));
        NumberVector b = new NumberVector(List.of(-24.0, 10.0));
        double c = 10;
        //      16*x*x + 20*y*y - 4*x - 8*y + 5
//        Matrix a = new UsualMatrix(List.of(List.of(32.0, 0.0), List.of(0.0, 40.0)));
//        NumberVector b = new NumberVector(List.of(-4.0, -8.0));
//        double c = 5;
        printAllMinimalizers(a, b, c, 2);
    }

    public static void main(String[] args) {
        printFirst();
//        printDiag();
    }
}
