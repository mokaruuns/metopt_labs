import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    protected double mul(List<Double> l, List<Double> r) {
        checkDim(l);
        checkDim(r);
        double ans = 0;
        for (int i = 0; i < dimensions; i++) {
            ans += l.get(i) * r.get(i);
        }
        return ans;
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

    private double partDiff(List<Double> list, int i) {
        double ans = b.get(i);
        for (int j = 0; j < dimensions; j++) {
            ans += a.get(i, j) * list.get(j);
        }
        return ans;
    }

    static List<Double> generateMatrix(int conditionality, int dimensions) {
        List<Double> temp = new ArrayList<>(Collections.nCopies(dimensions - 1, 1.0));
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
        System.out.println(biMinimalizer.apply(x));
//        for (double x_i : x) {
//            System.out.println(x_i);
//        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Integer> dimensions = List.of(10, 100, 1000, 10000);
        List<Integer> conditionalities = List.of(10, 100, 1000);
        for (Integer dimension : dimensions) {
            for (Integer conditionality : conditionalities) {
                System.out.println("d = " + dimension + ", c = " + conditionality);
                Matrix a = new DiagMatrix(generateMatrix(conditionality, dimension));
                NumberVector b = new NumberVector(Collections.nCopies(dimension, 0.0));
                double c = 0;
                printMinimalizer(new GradientDescent(a, b, c, dimension), dimension);
                printMinimalizer(new SteepestDescent(a, b, c, dimension), dimension);
                printMinimalizer(new FletcherReeves(a, b, c, dimension), dimension);
            }
        }
    }
}
